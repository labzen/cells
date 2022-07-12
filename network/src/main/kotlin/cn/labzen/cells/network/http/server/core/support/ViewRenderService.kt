package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.network.exception.HttpServerException
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Rendering
import cn.labzen.cells.network.http.server.core.handler.render.ThymeleafRendering
import org.springframework.util.ClassUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object ViewRenderService {

  private lateinit var rendering: Rendering

  fun initialize(configuration: ServerConfiguration) {
    val cl = ViewRenderService::class.java.classLoader
    rendering = when {
      ClassUtils.isPresent("org.thymeleaf.TemplateEngine", cl) -> {
        ThymeleafRendering(configuration)
      }
      else -> throw HttpServerException("未找到任何可用的视图模板引擎框架，建议使用Thymeleaf")
    }
  }

  fun render(templatePath: String, request: HttpServletRequest, response: HttpServletResponse, result: Any? = null) {
    rendering.render(templatePath, request, response, result)
  }
}
