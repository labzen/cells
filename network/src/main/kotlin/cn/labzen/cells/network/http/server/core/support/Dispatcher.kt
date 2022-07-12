package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.core.utils.Strings
import cn.labzen.cells.network.http.meta.Header
import cn.labzen.cells.network.http.meta.Method
import com.google.common.base.Enums
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

class Dispatcher : HttpServlet() {

  override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
    val method = Enums.getIfPresent(Method::class.java, req.method)

    if (!method.isPresent || method.get() == Method.PATCH) {
      doService(req, resp)
    } else {
      super.service(req, resp)
    }
  }

  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    doService(req, resp)
  }

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    doService(req, resp)
  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    doService(req, resp)
  }

  override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    doService(req, resp)
  }

  override fun doOptions(req: HttpServletRequest, resp: HttpServletResponse) {
    if (req.getHeader(Header.ORIGIN.value) != null
      && req.getHeader(Header.ACCESS_CONTROL_REQUEST_METHOD.value) != null
    ) {
      doService(req, resp)
      if (resp.containsHeader("Allow")) {
        return
      }
    }

    // from spring: Use response wrapper in order to always add PATCH to the allowed methods
    super.doOptions(req, object : HttpServletResponseWrapper(resp) {
      override fun setHeader(name: String, value: String?) {
        val v = if ("Allow" == name) {
          if (Strings.isNotEmpty(value)) "$value, ${Method.PATCH.name}" else Method.PATCH.name
        } else value
        super.setHeader(name, v)
      }
    })
  }

  private fun doService(req: HttpServletRequest, resp: HttpServletResponse) {
    val adapter = req.getAttribute("Mapped::Adapter::Resolved") as MappingAdapter
    val view = adapter.handle(req, resp)
    req.setAttribute("Rendered::View", view)
  }
}
