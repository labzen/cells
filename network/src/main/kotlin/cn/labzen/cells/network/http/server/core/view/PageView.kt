package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.ContentType
import cn.labzen.cells.network.http.server.core.support.ViewRenderService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class PageView(
  private val produce: ContentType,
  private val templatePath: String?,
  private val result: Any?
) : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.contentType = produce.value

    val template = templatePath ?: if (result is String) result else null
    if (template == null) {
      NoViewTemplateView().render(req, resp)
      return
    }

    try {
      // 注解中没有指定模板，代表使用了返回值指定模板，也就没有返回的模型
      if (templatePath == null) {
        ViewRenderService.render(template, req, resp)
      } else {
        ViewRenderService.render(template, req, resp, result)
      }
    } catch (e: Exception) {
      UnknownViewTemplateView(template).render(req, resp)
      return
    }
  }
}
