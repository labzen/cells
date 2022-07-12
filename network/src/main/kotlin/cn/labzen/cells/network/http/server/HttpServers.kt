package cn.labzen.cells.network.http.server

import cn.labzen.cells.network.http.server.undertow.UndertowServer

object HttpServers {

  @JvmStatic
  fun createUndertowServer(configuration: ServerConfiguration): Server {
    return UndertowServer().apply {
      init(configuration)
    }
  }
}
