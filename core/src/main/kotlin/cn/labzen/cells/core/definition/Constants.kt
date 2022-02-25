@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package cn.labzen.cells.core.definition

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Constants private constructor() {

  companion object {
    // encoding
    const val DEFAULT_CHARSET_NAME = "UTF-8"

    @JvmField
    val DEFAULT_CHARSET: Charset = StandardCharsets.UTF_8

    // projects package path
    const val PACKAGE_ROOT = "cn.labzen"
    const val PACKAGE_CELLS = "$PACKAGE_ROOT.cells"

    // separators and symbols
    const val SEPARATOR_COMMAS = ","
    const val SEPARATOR_DOT = "."
    const val SEPARATOR_UNDERLINE = "_"
    const val SEPARATOR_STRIKE = "-"
    const val SEPARATOR_COLON = ":"
    const val SEPARATOR_SLASH = "/"
    const val SEPARATOR_BACKSLASH = "\\"

    const val SYMBOL_BANG = "!"
    const val SYMBOL_AT = "@"
    const val SYMBOL_POUND = "#"
    const val SYMBOL_DOLLAR = "$"
    const val SYMBOL_PERCENT = "%"
    const val SYMBOL_POWER = "^"
    const val SYMBOL_AND = "&"
    const val SYMBOL_ASTERISK = "*"
    const val SYMBOL_BRACKET_LEFT = "("
    const val SYMBOL_BRACKET_RIGHT = ")"
    const val SYMBOL_MINUS = "-"
    const val SYMBOL_PLUS = "+"
    const val SYMBOL_UNDERLINE = "_"
    const val SYMBOL_SQUARE_BRACKET_LEFT = "["
    const val SYMBOL_SQUARE_BRACKET_RIGHT = "]"
    const val SYMBOL_BRACE_LEFT = "{"
    const val SYMBOL_BRACE_RIGHT = "}"
    const val SYMBOL_EQUAL = "="
    const val SYMBOL_CR = "\\r"
    const val SYMBOL_BR = "\\n"
    const val SYMBOL_TAB = "\\t"

    // date and times
    const val PATTERN_OF_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_OF_DATE_TIME_MILL = "yyyy-MM-dd HH:mm:ss.SSS"
    const val PATTERN_OF_DATE = "yyyy-MM-dd"
    const val PATTERN_OF_DATE_WEEK = "yyyy-MM-dd E"
    const val PATTERN_OF_TIME = "HH:mm:ss"
    const val PATTERN_OF_TIME_MILL = "HH:mm:ss.SSS"

    const val PATTERN_CN_OF_DATE_TIME = "yyyy年MM月dd日 HH时mm分ss秒"
    const val PATTERN_CN_OF_DATE_TIME_MILL = "yyyy年MM月dd日 HH时mm分ss秒.SSS毫秒"
    const val PATTERN_CN_OF_DATE = "yyyy年MM月dd日"
    const val PATTERN_CN_OF_TIME = "HH时mm分ss秒"
    const val PATTERN_CN_OF_TIME_MILL = "HH时mm分ss秒.SSS毫秒"

    // system properties
    @JvmField
    val SYSTEM_JAVA_VERSION: String = System.getProperty("java.version")

    @JvmField
    val SYSTEM_JAVA_VENDOR: String = System.getProperty("java.vendor")

    @JvmField
    val SYSTEM_JAVA_HOME: String = System.getProperty("java.home")

    @JvmField
    val SYSTEM_JAVA_CLASSPATH: String = System.getProperty("java.class.path")

    @JvmField
    val SYSTEM_OS_NAME: String = System.getProperty("os.name")

    @JvmField
    val SYSTEM_OS_ARCH: String = System.getProperty("os.arch")

    @JvmField
    val SYSTEM_OS_VERSION: String = System.getProperty("os.version")

    @JvmField
    val SYSTEM_FILE_SEPARATOR: String = System.getProperty("file.separator")

    @JvmField
    val SYSTEM_PATH_SEPARATOR: String = System.getProperty("path.separator")

    @JvmField
    val SYSTEM_LINE_SEPARATOR: String = System.getProperty("line.separator")

    @JvmField
    val SYSTEM_USER_NAME: String = System.getProperty("user.name")

    @JvmField
    val SYSTEM_USER_HOME: String = System.getProperty("user.home")

    @JvmField
    val SYSTEM_USER_DIR: String = System.getProperty("user.dir")
  }
}
