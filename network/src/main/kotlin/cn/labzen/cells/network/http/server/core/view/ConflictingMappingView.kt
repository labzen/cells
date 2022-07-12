package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.Status
import cn.labzen.cells.network.http.server.core.support.meta.MappedClass
import cn.labzen.cells.network.http.server.core.support.meta.MappedMethod
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class ConflictingMappingView(private val mappings: List<Pair<MappedClass, MappedMethod>>) : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.status = Status.HTTP_CONFLICT.value
    val hits = mappings.map {
      val classDesc = it.first.cls.name
      val method = it.second.method
      val methodDesc = method.parameterTypes.joinToString(
        ", ",
        "${method.name}(",
        ")",
        transform = Class<*>::getSimpleName
      )

      "<li>Class: $classDesc, Method: $methodDesc</li>"
    }

    resp.writer.write(
      """
      <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
      <html>

      <head>
      	<title>409 More Mapping</title>
      </head>

      <body>
      	<h1>More Mapping</h1>
      	<p>
          The requested URL ${req.requestURI} was matched multi 'Controller' on this server.

          <ul>
            ${hits.joinToString("\n")}
          <ul>
        </p>
      </body>

      </html>
    """.trimIndent()
    )
    resp.flushBuffer()
  }
}
