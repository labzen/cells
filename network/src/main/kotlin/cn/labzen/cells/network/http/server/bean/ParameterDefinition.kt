package cn.labzen.cells.network.http.server.bean

data class ParameterDefinition(
  val name: String,
  val clazz: Class<*>,
  val mode: ParameterMode,
  val require: Boolean
)
