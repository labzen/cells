package cn.labzen.cells.network.http.server.bean

import java.lang.reflect.Method

data class MethodDefinition(
  val name: String,
  val method: Method,
  val instance: Any,
  val mode: HandleMode,
  val params: List<ParameterDefinition>
)
