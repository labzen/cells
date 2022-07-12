package cn.labzen.cells.network.http.server.core.handler.converter

import cn.labzen.cells.core.feature.MultiTry
import cn.labzen.cells.network.http.server.core.handler.Converting
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StringToLocalDateConverting : Converting<String, LocalDate> {

  private val mt = MultiTry.withoutValue<LocalDate>()

  override fun convert(source: String): LocalDate? =
    mt.attempt {
      LocalDate.parse(source, defaultFormatter)
    }.attempt {
      LocalDate.parse(source, chineseFormatter)
    }.attempt {
      LocalDate.parse(source, alternativeFormatter)
    }.attempt {
      LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE)
    }.execute()

  companion object {
    private val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val chineseFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    private val alternativeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
  }
}
