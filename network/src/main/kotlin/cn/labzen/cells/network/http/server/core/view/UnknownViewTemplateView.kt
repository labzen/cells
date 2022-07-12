package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.Status
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class UnknownViewTemplateView(private val template: String) : View {

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
      	<p>The page view template file '$template' cannot be parsed.</p>
      </body>

      </html>
    """.trimIndent()
    )
    resp.flushBuffer()
  }
}
