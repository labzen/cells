package cn.labzen.cells.network.http.server

import cn.labzen.cells.core.definition.Constants
import cn.labzen.cells.network.http.server.service.HttpServer
import cn.labzen.cells.network.http.server.service.HttpServerAdaptor
import cn.labzen.cells.network.http.server.service.StandardHttpServer
import io.netty.bootstrap.ServerBootstrap
import org.springframework.context.ApplicationContext
import java.net.InetSocketAddress

// todo http模块整体重构
class HttpServerBuilder private constructor() {

  private var packagePath: String = Constants.PACKAGE_ROOT
  private var springContext: ApplicationContext? = null
  private lateinit var address: InetSocketAddress
  private var path = "/"
  private var workThreadNumber = 128
  private var customOptionsFunc: ((ServerBootstrap) -> Unit)? = null

  fun bind(ip: String = "0.0.0.0", port: Int): HttpServerBuilder =
    bind(InetSocketAddress(ip, port))

  fun bind(address: InetSocketAddress): HttpServerBuilder {
    this.address = address
    return this
  }

  fun path(path: String): HttpServerBuilder {
    this.path = path
    return this
  }

  fun workThread(number: Int): HttpServerBuilder {
    workThreadNumber = number
    return this
  }

  fun customOptionsWhenBootstrap(block: (ServerBootstrap) -> Unit): HttpServerBuilder {
    customOptionsFunc = block
    return this
  }

  /**
   * 默认扫描 cn.labzen 包下的所有类
   */
  fun scanPackage(path: String) {
    packagePath = path
  }

  fun withSpring(context: ApplicationContext): HttpServerBuilder {
    springContext = context
    return this
  }

  fun build(): HttpServer {
    check()
    val service: HttpServerAdaptor = StandardHttpServer()
    service.setPackagePath(packagePath)
    service.setSpringContext(springContext)
    service.initialize(workThreadNumber, customOptionsFunc)
    return service
  }

  private fun check() {
    if (!this::address.isInitialized) {
      bind(port = 80)
    }

    assert(workThreadNumber >= 1)
  }

  companion object {
    @JvmStatic
    fun create(): HttpServerBuilder = HttpServerBuilder()
  }
}
