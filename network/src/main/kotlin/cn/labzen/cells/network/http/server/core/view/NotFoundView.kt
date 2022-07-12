package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.Status
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class NotFoundView : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.status = Status.HTTP_NOT_FOUND.value
    resp.writer.write(
      """
      <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
      <html>

      <head>
      	<title>404 Not Found</title>
      </head>

      <body>
      	<h1>Not Found</h1>
      	<p>The requested URL ${req.requestURI} was not found on this server.</p>
      </body>

      </html>
    """.trimIndent()
    )
    resp.flushBuffer()
  }
}
