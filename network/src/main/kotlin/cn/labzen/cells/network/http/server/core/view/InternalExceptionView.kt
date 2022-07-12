package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.Status
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class InternalExceptionView(private val exception: Exception) : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.status = Status.HTTP_INTERNAL_ERROR.value
    val message = StringWriter().use { sw ->
      PrintWriter(sw).use { pw ->
        exception.printStackTrace(pw)
        sw.toString()
      }
    }

    resp.writer.write(
      """
      <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
      <html>

      <head>
      	<title>500 Internal Server Error</title>
      </head>

      <body>
      	<h1>Internal Server Error</h1>
      	<p>An error occurred in the requested URL ${req.requestURI}.</p>
          <pre style="background-color: gainsboro;border-style: ridge;border-color: silver;border-width: thin;padding-left: 20px;">

$message
          </pre>
      </body>

      </html>
    """.trimIndent()
    )
    resp.flushBuffer()
  }
}
