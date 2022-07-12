package cn.labzen.cells.network.http.ua.bean

import cn.labzen.cells.network.http.ua.UNKNOWN

data class LayoutEngine @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN
  var build: String = UNKNOWN

  enum class Type {
    BROWSER, PROGRAM
  }
}
