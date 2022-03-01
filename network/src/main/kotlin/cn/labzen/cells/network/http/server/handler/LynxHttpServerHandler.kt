package cn.labzen.cells.network.http.server.handler

import org.springframework.context.ApplicationContext

interface LynxHttpServerHandler {

  fun scanAndInitialize(packagePath: String)

  fun setSpringContext(context: ApplicationContext)
}
