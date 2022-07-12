package cn.labzen.cells.network.http.ua.bean

import cn.labzen.cells.network.http.ua.UNKNOWN

data class Device @JvmOverloads constructor(
  val type: String, val name: String = UNKNOWN
) {
  var brand: String = UNKNOWN
  var cpu: String = UNKNOWN
  var bits: Int = 0
}
