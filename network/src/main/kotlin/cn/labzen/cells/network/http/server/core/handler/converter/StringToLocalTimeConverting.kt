package cn.labzen.cells.network.http.server.core.handler.converter

import cn.labzen.cells.core.feature.MultiTry
import cn.labzen.cells.network.http.server.core.handler.Converting
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class StringToLocalTimeConverting : Converting<String, LocalTime> {

  private val mt = MultiTry.withoutValue<LocalTime>()

  override fun convert(source: String): LocalTime? =
    mt.attempt {
      LocalTime.parse(source, defaultFormatter)
    }.attempt {
      LocalTime.parse(source, chineseFormatter)
    }.attempt {
      LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME)
    }.execute()

  companion object {
    private val defaultFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val chineseFormatter = DateTimeFormatter.ofPattern("HH时mm分ss秒")
  }
}
