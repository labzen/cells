package cn.labzen.cells.network.exception

import cn.labzen.cells.core.exception.LabzenRuntimeException

/**
 * HTTP服务器异常
 */
class HttpServerException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}
