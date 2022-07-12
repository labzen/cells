package cn.labzen.cells.network.http.server.core.handler.filter

import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Filtering
import cn.labzen.cells.network.http.server.core.view.View
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ViewRenderFiltering(configuration: ServerConfiguration) : Filtering(configuration) {

  override fun name(): String =
    "ViewRenderFiltering"

  override fun order(): Int =
    0

  override fun doInternalFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    filterChain.doFilter(request, response)
    val view = request.getAttribute("Rendered::View") as View
    view.render(request, response)
  }
}
