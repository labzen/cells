package cn.labzen.cells.network.http.server.core.handler.converter

import cn.labzen.cells.core.feature.MultiTry
import cn.labzen.cells.network.http.server.core.handler.Converting
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class StringToLocalDateTimeConverting : Converting<String, LocalDateTime> {

  private val mt = MultiTry.withoutValue<LocalDateTime>()

  override fun convert(source: String): LocalDateTime? =
    mt.attempt {
      LocalDateTime.parse(source, defaultFormatter)
    }.attempt {
      val timestamp = source.toLong()
      LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.systemDefault())
    }.attempt {
      LocalDateTime.parse(source, chineseFormatter)
    }.attempt {
      LocalDateTime.parse(source, alternativeFormatter)
    }.attempt {
      LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }.execute()

  companion object {
    private val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val chineseFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒")
    private val alternativeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
  }
}
