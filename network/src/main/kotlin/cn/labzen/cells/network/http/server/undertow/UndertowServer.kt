package cn.labzen.cells.network.http.server.undertow

import cn.labzen.cells.network.http.server.Server
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.support.DefaultServletContextListener
import io.undertow.Handlers
import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.handlers.encoding.ContentEncodingRepository
import io.undertow.server.handlers.encoding.EncodingHandler
import io.undertow.server.handlers.encoding.GzipEncodingProvider
import io.undertow.server.handlers.resource.ClassPathResourceManager
import io.undertow.server.handlers.resource.FileResourceManager
import io.undertow.server.handlers.resource.ResourceHandler
import io.undertow.servlet.Servlets
import io.undertow.servlet.api.DeploymentInfo
import io.undertow.servlet.api.ListenerInfo
import io.undertow.servlet.util.ImmediateInstanceFactory
import io.undertow.util.URLUtils
import org.slf4j.LoggerFactory
import java.nio.file.Paths
import java.util.*

class UndertowServer internal constructor() : Server {

  private val logger = LoggerFactory.getLogger(UndertowServer::class.java)
  private lateinit var server: Undertow

  private val builder = Undertow.builder()
  private lateinit var deployment: DeploymentInfo

  override fun init(configuration: ServerConfiguration) {
    deployment = Servlets.deployment()
      .setDeploymentName("labzen-undertow")
      .setClassLoader(UndertowServer::class.java.classLoader)
      .setEagerFilterInit(true)
      .setSecurityDisabled(true)
      .setContextPath(configuration.contextPath)
      .setDefaultSessionTimeout(configuration.sessionTimeout)

    val scl = DefaultServletContextListener(configuration)
    val factory = ImmediateInstanceFactory<EventListener>(scl)
    val listener = ListenerInfo(scl::class.java, factory)
    deployment.addListener(listener)
    deployment.resourceManager = ClassPathResourceManager(UndertowServer::class.java.classLoader, "")

    val manager = Servlets.defaultContainer().addDeployment(deployment)
    manager.deploy()

    var httpHandler = manager.start()
    httpHandler = pathHandler(httpHandler, configuration)
    httpHandler = gzipHandler(httpHandler, configuration)
    // val resourceManager = resourceManager(configuration)
    httpHandler = resourceHandler(httpHandler, configuration)

    builder.addHttpListener(configuration.port, configuration.host).setHandler(httpHandler)

    server = builder.build()
  }

  private fun resourceHandler(httpHandler: HttpHandler, configuration: ServerConfiguration): HttpHandler {
    val resourceManager = CompositeResourceManager()

    configuration.resourcesPath.forEach {
      val rm = if (URLUtils.isAbsoluteUrl(it)) {
        val file = Paths.get(it).toFile()
        if (file.exists()) {
          FileResourceManager(file)
        } else {
          logger.warn("不存在的资源地址 - $it")
          null
        }
      } else {
        ClassPathResourceManager(this::class.java.classLoader, it)
      }

      if (rm != null) {
        resourceManager.managers.add(rm)
      }
    }

    return ResourceHandler(resourceManager, httpHandler).apply {
    }
  }

  private fun pathHandler(httpHandler: HttpHandler, configuration: ServerConfiguration): HttpHandler =
    if ("/" == configuration.contextPath) {
      Handlers.path(httpHandler)
    } else {
      Handlers.path(Handlers.redirect(configuration.contextPath)).addPrefixPath(configuration.contextPath, httpHandler)
    }

  private fun gzipHandler(httpHandler: HttpHandler, configuration: ServerConfiguration): HttpHandler {
    val repository = ContentEncodingRepository()
    val provider = GzipEncodingProvider()
    repository.addEncodingHandler("gzip", provider, 100)
    return EncodingHandler(httpHandler, repository)
  }

  override fun start() {
    if (!this::server.isInitialized) {
      throw IllegalStateException("the undertow server is not initialized correctly")
    }

    server.start()
  }

  override fun stop() {
    if (!this::server.isInitialized) {
      throw IllegalStateException("the undertow server is not start yet")
    }

    server.stop()
  }

  override fun isNotRunning(): Boolean {
    if (!this::server.isInitialized) {
      return false
    }

    return server.worker?.isShutdown != false || server.worker?.isTerminated != false
  }

}
