package cn.labzen.cells.network.http.server.core.handler.filter

import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Filtering
import cn.labzen.cells.network.http.server.core.support.MappingsResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MappingResolveFiltering(configuration: ServerConfiguration) : Filtering(configuration) {

  private val mappingsResolver = MappingsResolver(configuration)

  override fun name(): String =
    "MappingResolveFiltering"

  override fun order(): Int =
    1

  override fun doInternalFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val resolved = mappingsResolver.resolve(request, response)
    request.setAttribute("Mapped::Adapter::Resolved", resolved)
    filterChain.doFilter(request, response)
  }
}
