@file:Suppress("unused")

package cn.labzen.cells.core.kotlin

import cn.labzen.meta.exception.LabzenException
import cn.labzen.meta.exception.LabzenRuntimeException
import java.util.function.Supplier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation


/**
 * { IDIOMS } -- 表达式为true时，执行代码块
 */
inline fun <T, R> T.runIf(condition: Boolean, block: T.() -> R): R? = if (condition) block() else null

/**
 * { IDIOMS } -- 为true时，执行代码块
 */
inline fun <R> Boolean.runIf(block: () -> R): R? = if (this) block() else null

/**
 * { IDIOMS } -- 表达式为false时，执行代码块
 */
inline fun <T, R> T.runUnless(condition: Boolean, block: T.() -> R): R? = if (!condition) block() else null

/**
 * { IDIOMS } -- 为false时，执行代码块
 */
inline fun <R> Boolean.runUnless(block: () -> R): R? = if (!this) block() else null

/**
 * { IDIOMS } -- 表达式为true时，执行代码块
 */
inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
  if (condition) block()
  return this
}

/**
 * { IDIOMS } -- 为true时，执行代码块
 */
inline fun Boolean.applyIf(block: () -> Unit): Boolean {
  if (this) block()
  return this
}

/**
 * { IDIOMS } -- 表达式为false时，执行代码块
 */
inline fun <T> T.applyUnless(condition: Boolean, block: T.() -> Unit): T {
  if (!condition) block()
  return this
}

/**
 * { IDIOMS } -- 为false时，执行代码块
 */
inline fun Boolean.applyUnless(block: () -> Unit): Boolean {
  if (!this) block()
  return this
}

/**
 * { IDIOMS } -- 表达式为true时，执行代码块
 */
inline fun <T> T.makeIf(condition: Boolean, block: T.() -> T): T =
  if (condition) block() else this

/**
 * { IDIOMS } -- 表达式为false时，执行代码块
 */
inline fun <T> T.makeUnless(condition: Boolean, block: T.() -> T): T =
  if (!condition) block() else this

/**
 * { IDIOMS } -- 表达式为true时，抛出异常
 */
inline fun <T, E : LabzenException> T.throwIf(condition: Boolean, block: T.() -> E) {
  if (condition) throw block()
}

/**
 * { IDIOMS } -- 为true时，抛出异常
 */
inline fun <E : LabzenException> Boolean.throwIf(block: () -> E) {
  if (this) throw block()
}

/**
 * { IDIOMS } -- 表达式为false时，抛出异常
 */
inline fun <T, E : LabzenException> T.throwUnless(condition: Boolean, block: T.() -> E) {
  if (!condition) throw block()
}

/**
 * { IDIOMS } -- 为false时，抛出异常
 */
inline fun <E : LabzenException> Boolean.throwUnless(block: () -> E) {
  if (!this) throw block()
}

/**
 * { IDIOMS } -- 表达式为true时，抛出异常
 */
inline fun <T, E : LabzenRuntimeException> T.throwRuntimeIf(condition: Boolean, block: T.() -> E) {
  if (condition) throw block()
}

/**
 * { IDIOMS } -- 为true时，抛出异常
 */
inline fun <E : LabzenRuntimeException> Boolean.throwRuntimeIf(block: () -> E) {
  if (this) throw block()
}

/**
 * { IDIOMS } -- 表达式为false时，抛出异常
 */
inline fun <T, E : LabzenRuntimeException> T.throwRuntimeUnless(condition: Boolean, block: T.() -> E) {
  if (!condition) throw block()
}

/**
 * { IDIOMS } -- 为false时，抛出异常
 */
inline fun <E : LabzenRuntimeException> Boolean.throwRuntimeUnless(block: () -> E) {
  if (!this) throw block()
}

/**
 * { IDIOMS } -- 如果枚举[T]包含给出name的项，返回true
 */
inline fun <reified T : Enum<T>> enumContains(name: String, ignoreCase: Boolean = false): Boolean =
  enumValues<T>().any { it.name.equals(name, ignoreCase) }

/**
 * { IDIOMS } -- 返回具有指定name的枚举项，如果没有找到此类项，则返回null
 */
inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String, ignoreCase: Boolean = false): T? =
  enumValues<T>().find { it.name.equals(name, ignoreCase) }

// ==================================================================

/**
 * { IDIOMS } - 只能被赋值一次的属性
 */
@Suppress("UNCHECKED_CAST")
class InitOnceProperty<T> : ReadWriteProperty<Any, T> {

  private var value: Any = EMPTY

  override fun getValue(thisRef: Any, property: KProperty<*>): T = get()

  fun get(): T =
    if (this.value == EMPTY) throw IllegalStateException("The Value hasn't been initialized")
    else value as T

  fun getOrNull(): T? =
    if (this.value == EMPTY) null
    else this.value as T

  fun getOrElse(other: T): T =
    if (this.value == EMPTY) other
    else this.value as T

  fun getOrElse(other: Supplier<T>): T =
    if (this.value == EMPTY) other.get()
    else this.value as T

  override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = set(value)

  fun set(value: T) {
    if (this.value != EMPTY) {
      throw IllegalStateException("The Value has been initialized")
    }
    this.value = value as Any
  }

  fun setIfAbsent(value: T) {
    if (this.value == EMPTY) {
      this.value = value as Any
    }
  }

  fun initialized() =
    this.value != EMPTY

  companion object {
    private object EMPTY
  }
}

/**
 * { IDIOMS } -- 创建只能被赋值一次的属性
 */
inline fun <reified T> initOnce(): InitOnceProperty<T> = InitOnceProperty()

// ==================================================================

/**
 * { IDIOMS } -- 注解类是否存在注解
 */
inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation(): Boolean =
  findAnnotation<T>() != null
