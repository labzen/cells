package cn.labzen.cells.network.http.server.core.handler.filter

import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Filtering
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FileUploadFiltering(configuration: ServerConfiguration) : Filtering(configuration) {

  override fun name(): String =
    "FileUploadFiltering"

  override fun order(): Int =
    2

  override fun doInternalFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    println("file upload..")
    filterChain.doFilter(request, response)
    println("file upload  done")
  }
}
