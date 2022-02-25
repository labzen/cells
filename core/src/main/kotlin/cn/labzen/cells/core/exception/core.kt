@file:Suppress("unused")

package cn.labzen.cells.core.exception

/**
 * 内部错误
 */
class InternalException : LabzenException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}

/**
 * 参数非法异常
 */
class ArgumentsException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}

/**
 * 功能未实现异常
 */
class UnimplementedException : LabzenException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}

// =====================================================================================================================

/**
 * 字符串处理异常
 */
class StringException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}

/**
 * 字符串处理异常
 */
class RandomException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}

/**
 * 动态链接库加载异常
 */
class DynamicLinkLibraryLoadException : LabzenRuntimeException {

  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any?) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any?) : super(cause, message, *arguments)
}
