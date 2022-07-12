package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.Status
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class NoViewTemplateView : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.status = Status.HTTP_UNAVAILABLE.value
    resp.writer.write(
      """
      <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
      <html>

      <head>
      	<title>503 Service Unavailable</title>
      </head>

      <body>
      	<h1>Service Unavailable</h1>
      	<p>No page view template was found for the requested URL ${req.requestURI}.</p>
      </body>

      </html>
    """.trimIndent()
    )
    resp.flushBuffer()
  }
}
