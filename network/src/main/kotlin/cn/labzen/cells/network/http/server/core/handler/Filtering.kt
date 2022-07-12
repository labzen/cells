package cn.labzen.cells.network.http.server.core.handler

import cn.labzen.cells.network.http.server.ServerConfiguration
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 过滤器
 */
abstract class Filtering(protected val configuration: ServerConfiguration) : Filter {

  private lateinit var config: FilterConfig

  override fun init(filterConfig: FilterConfig) {
    config = filterConfig
  }

  override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
    if (request !is HttpServletRequest || response !is HttpServletResponse) {
      throw ServletException("Just supports HTTP requests")
    }

    val filteredKey = "${name()}::Filtered"
    val isAlreadyFiltered = request.getAttribute(filteredKey) != null
    val isErrorRequest = request.getAttribute("") != null

    if (isAlreadyFiltered || isErrorRequest) {
      filterChain.doFilter(request, response)
    } else {
      request.setAttribute(filteredKey, true)
      try {
        doInternalFilter(request, response, filterChain)
      } finally {
        request.removeAttribute(filteredKey)
      }
    }
  }

  override fun destroy() {
    // do nothing
  }

  abstract fun name(): String

  open fun order(): Int =
    0

  open fun dispatcherTypes(): EnumSet<DispatcherType> =
    EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE)

  open fun path(): String =
    "/*"

  abstract fun doInternalFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain)
}
