package cn.labzen.cells.network.exception

import cn.labzen.cells.core.exception.LabzenRuntimeException

/**
 * TCP客户端异常
 */
class TcpClientException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}
