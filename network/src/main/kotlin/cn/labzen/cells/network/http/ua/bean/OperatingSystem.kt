package cn.labzen.cells.network.http.ua.bean

import cn.labzen.cells.network.http.ua.UNKNOWN

data class OperatingSystem @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN

  enum class Type {
    DESKTOP, PHONE, TABLET, WATCH, GAMES, ROBOT, PROGRAM
  }
}
