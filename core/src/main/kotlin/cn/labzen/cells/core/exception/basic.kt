package cn.labzen.cells.core.exception

import cn.labzen.cells.core.utils.Strings

abstract class LabzenException : Exception {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(Strings.format(message, arguments.asList()))

  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(message, cause)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) :
      super(Strings.format(message, arguments.asList()), cause)
}

abstract class LabzenRuntimeException : RuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(Strings.format(message, arguments.asList()))

  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(message, cause)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) :
      super(Strings.format(message, arguments.asList()), cause)
}
