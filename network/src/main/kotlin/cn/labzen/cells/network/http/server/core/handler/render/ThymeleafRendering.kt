package cn.labzen.cells.network.http.server.core.handler.render

import cn.labzen.cells.core.utils.Strings
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Rendering
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.WebContext
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ThymeleafRendering(configuration: ServerConfiguration) : Rendering {

  private val engine = TemplateEngine().apply {
    val tp = Strings.insureEndsWith(configuration.templatePath, "/")
    val resolver = createResolver(configuration.context, tp)
    this.setTemplateResolver(resolver)
  }

  private fun createResolver(context: ServletContext, path: String) =
    ServletContextTemplateResolver(context).apply {
      templateMode = TemplateMode.HTML
      prefix = path
      suffix = ".html"
      characterEncoding = "UTF-8"
      cacheTTLMs = 3600000L
    }

  override fun render(templatePath: String, request: HttpServletRequest, response: HttpServletResponse, result: Any?) {
    val ctx = WebContext(request, response, request.servletContext, request.locale)
    if (result is Map<*, *>) {
      result.forEach { k, v ->
        ctx.setVariable(k?.toString(), v)
      }
    } else {
      ctx.setVariable("result", result)
    }

    engine.process(templatePath, ctx, response.writer)
  }
}
