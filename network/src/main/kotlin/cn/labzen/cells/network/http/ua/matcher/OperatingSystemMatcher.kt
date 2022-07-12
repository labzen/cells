package cn.labzen.cells.network.http.ua.matcher

import cn.labzen.cells.network.http.ua.bean.OperatingSystem

class OperatingSystemMatcher(
  name: String,
  pattern: String,
  val type: OperatingSystem.Type
) : Matcher(name, pattern)
