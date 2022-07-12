package cn.labzen.cells.network.http.server.core.handler.converter

import cn.labzen.cells.core.feature.MultiTry
import cn.labzen.cells.network.http.server.core.handler.Converting
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class StringToDateConverting : Converting<String, Date> {

  private val mt = MultiTry.withoutValue<Date>()

  override fun convert(source: String): Date? =
    mt.attempt {
      defaultFormatter.parse(source)
    }.attempt {
      val timestamp = source.toLong()
      Date.from(Instant.ofEpochMilli(timestamp))
    }.attempt {
      chineseFormatter.parse(source)
    }.attempt {
      alternativeFormatter.parse(source)
    }.execute()


  companion object {
    private val defaultFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val chineseFormatter = SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private val alternativeFormatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
  }
}
