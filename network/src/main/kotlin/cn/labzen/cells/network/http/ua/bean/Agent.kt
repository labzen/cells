package cn.labzen.cells.network.http.ua.bean

import cn.labzen.cells.network.http.ua.UNKNOWN

data class Agent @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN

  enum class Type {
    BROWSER, WEBVIEW, APP, EMAIL, PROGRAM, SYSTEM
  }
}
