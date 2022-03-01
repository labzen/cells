package cn.labzen.cells.network.http.server.service

import cn.labzen.cells.network.http.server.handler.DefaultHttpServerHandler
import cn.labzen.cells.network.http.server.handler.LynxHttpServerHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.unix.PreferredDirectByteBufAllocator
import io.netty.handler.codec.http.HttpContentCompressor
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpRequestDecoder
import io.netty.handler.codec.http.HttpResponseEncoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.stream.ChunkedWriteHandler
import org.springframework.context.ApplicationContext
import java.nio.charset.StandardCharsets

abstract class HttpServerAdaptor : HttpServer, Runnable {

  private var springContext: ApplicationContext? = null
  private lateinit var packagePath: String
  private lateinit var boss: EventLoopGroup
  private lateinit var worker: EventLoopGroup
  private lateinit var server: ServerBootstrap
  private lateinit var mainHandler: ChannelHandler

  private val channelStringEncoder = StringEncoder(StandardCharsets.UTF_8)

  internal fun setPackagePath(path: String) {
    packagePath = path
  }

  internal fun setSpringContext(context: ApplicationContext?) {
    springContext = context
  }

  internal fun initialize(
    workThreadNumber: Int,
    globalOptions: ((ServerBootstrap) -> Unit)?
  ) {
    boss = NioEventLoopGroup(1)
    worker = NioEventLoopGroup(workThreadNumber)
    server = ServerBootstrap()

    instantiateMainHandler()

    server.apply {
      group(boss, worker)
      channel(NioServerSocketChannel::class.java)
      childHandler(channelInitializer())

      option(ChannelOption.SO_BACKLOG, 128)
      option(ChannelOption.ALLOCATOR, PreferredDirectByteBufAllocator.DEFAULT)

      childOption(ChannelOption.TCP_NODELAY, true)
      childOption(ChannelOption.SO_KEEPALIVE, true)
      childOption(ChannelOption.SO_RCVBUF, 128 * 1024)
      childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
      childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark(1024 * 1024 / 2, 1024 * 1024))

      if (globalOptions != null) {
        globalOptions(this)
      }
    }
  }

  private fun instantiateMainHandler() {
    val instance: LynxHttpServerHandler = DefaultHttpServerHandler()

    springContext?.apply {
      instance.setSpringContext(this)
    }
    instance.scanAndInitialize(packagePath)

    mainHandler = instance as ChannelHandler
  }

  private fun channelInitializer(): ChannelInitializer<SocketChannel> =
    object : ChannelInitializer<SocketChannel>() {
      @Throws(Exception::class)
      override fun initChannel(socketChannel: SocketChannel) {
        socketChannel.pipeline()
          .addLast(
            HttpRequestDecoder(),
            HttpObjectAggregator(65535),
            mainHandler,
            HttpContentCompressor(),
            HttpResponseEncoder(),
            channelStringEncoder,
            ChunkedWriteHandler()
          )
      }
    }

  override fun run() {
    TODO("Not yet implemented")
  }
}

