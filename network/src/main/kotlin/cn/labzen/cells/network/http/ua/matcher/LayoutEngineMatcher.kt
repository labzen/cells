package cn.labzen.cells.network.http.ua.matcher

import cn.labzen.cells.network.http.ua.bean.LayoutEngine

class LayoutEngineMatcher(
  name: String,
  pattern: String,
  val type: LayoutEngine.Type
) : Matcher(name, pattern)
