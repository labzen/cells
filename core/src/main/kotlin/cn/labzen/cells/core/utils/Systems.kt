package cn.labzen.cells.core.utils

import java.util.*

object Systems {

  private val OS_NAME: OS by lazy {
    val name = System.getProperty("os.name").lowercase(Locale.getDefault())

    when {
      name.contains("win") -> OS.WINDOWS
      name.contains("nix") || name.contains("nux") || name.contains("aix") -> OS.LINUX
      name.contains("mac") -> OS.MAC
      name.contains("sunos") -> OS.SOLARIS
      else -> OS.UNKNOWN
    }
  }
  private val OS_ARCH: Int by lazy {
    val arch = System.getProperty("os.arch")
    if (arch.contains("64")) 64 else 32
  }

  @JvmStatic
  fun os() = OS_NAME

  /**
   * 获取的是JVM是多少位系统，返回只有32和64两个值
   */
  @JvmStatic
  fun osArch() = OS_ARCH

  enum class OS {
    WINDOWS, LINUX, MAC, SOLARIS, UNKNOWN
  }
}
