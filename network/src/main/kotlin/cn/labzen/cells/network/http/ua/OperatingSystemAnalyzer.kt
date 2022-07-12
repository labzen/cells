package cn.labzen.cells.network.http.ua

import cn.labzen.cells.network.http.ua.bean.OperatingSystem
import cn.labzen.cells.network.http.ua.bean.OperatingSystem.Type.*
import cn.labzen.cells.network.http.ua.matcher.OperatingSystemMatcher

object OperatingSystemAnalyzer {

  private val matchers = listOf(
    OperatingSystemMatcher("Windows 10 or Windows Server 2016", "windows nt 10\\.0", DESKTOP),
    OperatingSystemMatcher("Windows 8.1 or Winsows Server 2012R2", "windows nt 6\\.3", DESKTOP),
    OperatingSystemMatcher("Windows 8 or Winsows Server 2012", "windows nt 6\\.2", DESKTOP),
    OperatingSystemMatcher("Windows Vista", "windows nt 6\\.0", DESKTOP),
    OperatingSystemMatcher("Windows 7 or Windows Server 2008R2", "windows nt 6\\.1", DESKTOP),
    OperatingSystemMatcher("Windows 2003", "windows nt 5\\.2", DESKTOP),
    OperatingSystemMatcher("Windows XP", "windows nt 5\\.1", DESKTOP),
    OperatingSystemMatcher("Windows 2000", "windows nt 5\\.0", DESKTOP),
    OperatingSystemMatcher("Windows Phone", "windows (ce|phone|mobile)( os)?", PHONE),
    OperatingSystemMatcher("Windows", "windows", DESKTOP),
    OperatingSystemMatcher("OSX", "os x (\\d+)[._](\\d+)", DESKTOP),
    OperatingSystemMatcher("Android", "Android", PHONE),
    OperatingSystemMatcher("Linux", "linux", DESKTOP),
    OperatingSystemMatcher("Wii", "wii", GAMES),
    OperatingSystemMatcher("PS3", "playstation 3", GAMES),
    OperatingSystemMatcher("PSP", "playstation portable", GAMES),
    OperatingSystemMatcher("iPad", "\\(iPad.*os (\\d+)[._](\\d+)", TABLET),
    OperatingSystemMatcher("iPhone", "\\(iPhone.*os (\\d+)[._](\\d+)", PHONE),
    OperatingSystemMatcher("YPod", "iPod touch[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", TABLET),
    OperatingSystemMatcher("YPad", "iPad[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", TABLET),
    OperatingSystemMatcher("YPhone", "iPhone[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", PHONE),
    OperatingSystemMatcher("Symbian", "symbian(os)?", DESKTOP),
    OperatingSystemMatcher("Darwin", "Darwin\\/([\\d\\w\\.\\-]+)", DESKTOP),
    OperatingSystemMatcher("Adobe Air", "AdobeAir\\/([\\d\\w\\.\\-]+)", DESKTOP),
    OperatingSystemMatcher("Java", "Java[\\s]+([\\d\\w\\.\\-]+)", PROGRAM)
  )

  fun analyze(value: String): OperatingSystem =
    matchers.firstOrNull { it.match(value) }?.let {
      OperatingSystem(it.type.toString(), name = it.name)
    } ?: UNKNOWN_OPERATING_SYSTEM
}
