package cn.labzen.cells.network.http.server.core.view

import cn.labzen.cells.network.http.meta.ContentType
import cn.labzen.cells.network.http.meta.Status
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class RestfulView(
  private val produce: ContentType,
  private val result: Any?
) : View {

  override fun render(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.status = Status.HTTP_OK.value
    resp.contentType = produce.value
    if (produce.value.contains("xml", true)) {
      renderXml(resp)
    } else {
      renderJson(resp)
    }
  }

  private fun renderJson(resp: HttpServletResponse) {
    val output = when (result) {
      null -> "{}"
      is String -> result
      else -> jsonMapper.writeValueAsString(result)
    }

    resp.writer.write((output))
    resp.flushBuffer()
  }

  private fun renderXml(resp: HttpServletResponse) {
    val output = when (result) {
      null -> "{}"
      is String -> result
      else -> xmlMapper.writeValueAsString(result)
    }

    resp.writer.write((output))
    resp.flushBuffer()
  }

  companion object {
    val jsonMapper = jacksonObjectMapper()
    val xmlMapper = XmlMapper().apply {
      this.propertyNamingStrategy = PropertyNamingStrategies.UPPER_CAMEL_CASE
    }
  }

}
