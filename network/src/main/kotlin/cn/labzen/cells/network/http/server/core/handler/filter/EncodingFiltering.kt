package cn.labzen.cells.network.http.server.core.handler.filter

import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Filtering
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class EncodingFiltering(configuration: ServerConfiguration) : Filtering(configuration) {

  override fun name(): String =
    "EncodingFiltering"

  override fun order(): Int =
    3

  override fun doInternalFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    println("encoding filter...")
    filterChain.doFilter(request, response)
  }
}
