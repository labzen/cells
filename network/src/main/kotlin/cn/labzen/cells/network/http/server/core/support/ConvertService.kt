package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.network.http.meta.Method
import cn.labzen.cells.network.http.server.core.handler.converter.StringToDateConverting
import cn.labzen.cells.network.http.server.core.handler.converter.StringToLocalDateConverting
import cn.labzen.cells.network.http.server.core.handler.converter.StringToLocalDateTimeConverting
import cn.labzen.cells.network.http.server.core.handler.converter.StringToLocalTimeConverting
import org.springframework.core.convert.support.DefaultConversionService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

object ConvertService {

  private val service = DefaultConversionService().also {
    it.addConverter(StringToDateConverting())
    it.addConverter(StringToLocalDateTimeConverting())
    it.addConverter(StringToLocalDateConverting())
    it.addConverter(StringToLocalTimeConverting())
  }

  fun support(source: Class<String>, target: Class<*>): Boolean =
    service.canConvert(source, target)

  fun convert(source: String, target: Class<*>) =
    try {
      service.convert(source, target)
    } catch (e: Exception) {
      null
    }
}
