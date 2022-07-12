package cn.labzen.cells.network.http.ua

import cn.labzen.cells.network.http.ua.bean.Device
import cn.labzen.cells.network.http.ua.matcher.Matcher

object DeviceAnalyzer {

  private val matchers = listOf<Matcher>()

  // todo
  fun analyze(value: String): Device =
    UNKNOWN_DEVICE
}
