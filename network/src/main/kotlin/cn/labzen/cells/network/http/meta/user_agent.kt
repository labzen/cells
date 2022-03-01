package cn.labzen.cells.network.http.meta

import com.google.common.cache.CacheBuilder
import cn.labzen.cells.network.http.meta.OperatingSystem.Type.*
import java.util.concurrent.TimeUnit

const val UNKNOWN = "Unknown"

val UNKNOWN_DEVICE = Device(UNKNOWN)
val UNKNOWN_OPERATING_SYSTEM = OperatingSystem(UNKNOWN)
val UNKNOWN_LAYOUT_ENGINE = LayoutEngine(UNKNOWN)
val UNKNOWN_AGENT = Agent(UNKNOWN)


data class Device @JvmOverloads constructor(
  val type: String, val name: String = UNKNOWN
) {
  var brand: String = UNKNOWN
  var cpu: String = UNKNOWN
  var bits: Int = 0
}

data class OperatingSystem @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN

  enum class Type {
    DESKTOP, PHONE, TABLET, WATCH, GAMES, ROBOT, PROGRAM
  }
}

data class LayoutEngine @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN
  var build: String = UNKNOWN

  enum class Type {
    BROWSER, PROGRAM
  }
}

data class Agent @JvmOverloads constructor(
  val type: String,
  val name: String = UNKNOWN,
  val version: String = UNKNOWN
) {
  var namedVersion: String = UNKNOWN

  enum class Type {
    BROWSER, WEBVIEW, APP, EMAIL, PROGRAM, SYSTEM
  }
}

data class UserAgent internal constructor(
  val original: String,
  val device: Device,
  val operatingSystem: OperatingSystem,
  val layoutEngine: LayoutEngine,
  val agent: Agent
) {

  companion object {
    /*
     * 缓存策略需要优化
     */
    private val cache = CacheBuilder.newBuilder()
      .expireAfterAccess(30, TimeUnit.SECONDS)
      .maximumSize(512)
      .build<Int, UserAgent>()

    @JvmStatic
    fun parse(value: String): UserAgent {
      val key = value.hashCode()
      return cache.get(key) {
        val device = DeviceAnalyzer.analyze(value)
        val operatingSystem = OperatingSystemAnalyzer.analyze(value)
        val layoutEngine = LayoutEngineAnalyzer.analyze(value)
        val agent = AgentAnalyzer.analyze(value)

        UserAgent(value, device, operatingSystem, layoutEngine, agent)
      }
    }
  }
}

internal open class Matcher(val name: String, pattern: String) {

  private val regex = Regex(pattern, RegexOption.IGNORE_CASE)

  fun match(value: String): Boolean =
    regex.containsMatchIn(value)
}

internal object DeviceAnalyzer {

  private val matchers = listOf<Matcher>()

  // todo
  fun analyze(value: String): Device =
    UNKNOWN_DEVICE
}

internal object OperatingSystemAnalyzer {

  internal class OperatingSystemMatcher(
    name: String,
    pattern: String,
    val type: OperatingSystem.Type
  ) : Matcher(name, pattern)

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

internal object LayoutEngineAnalyzer {

  internal class LayoutEngineMatcher(
    name: String,
    pattern: String,
    val type: LayoutEngine.Type
  ) : Matcher(name, pattern)

  private val matchers = listOf(
    LayoutEngineMatcher("Trident", "trident", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Webkit", "webkit", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Chrome", "chrome", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Opera", "opera", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Presto", "presto", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Gecko", "gecko", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("KHTML", "khtml", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("Konqeror", "konqueror", LayoutEngine.Type.BROWSER),
    LayoutEngineMatcher("MIDP", "MIDP", LayoutEngine.Type.BROWSER)
  )

  fun analyze(value: String): LayoutEngine =
    matchers.firstOrNull { it.match(value) }?.let {
      LayoutEngine(it.type.toString(), it.name)
    } ?: UNKNOWN_LAYOUT_ENGINE
}

internal object AgentAnalyzer {

  internal class AgentMatcher(
    name: String,
    pattern: String,
    val type: Agent.Type
  ) : Matcher(name, pattern)

  private val matchers = listOf(
    AgentMatcher("MSEdge", "Edge", Agent.Type.BROWSER),
    AgentMatcher("Chrome", "chrome", Agent.Type.BROWSER),
    AgentMatcher("Firefox", "firefox", Agent.Type.BROWSER),
    AgentMatcher("IEMobile", "iemobile", Agent.Type.BROWSER),
    AgentMatcher("Safari", "safari", Agent.Type.BROWSER),
    AgentMatcher("Opera", "opera", Agent.Type.BROWSER),
    AgentMatcher("Konqueror", "konqueror", Agent.Type.BROWSER),
    AgentMatcher("PS3", "playstation 3", Agent.Type.SYSTEM),
    AgentMatcher("PSP", "playstation portable", Agent.Type.SYSTEM),
    AgentMatcher("Lotus", "lotus.notes", Agent.Type.EMAIL),
    AgentMatcher("Thunderbird", "thunderbird", Agent.Type.EMAIL),
    AgentMatcher("Netscape", "netscape", Agent.Type.BROWSER),
    AgentMatcher("Seamonkey", "seamonkey", Agent.Type.BROWSER),
    AgentMatcher("Outlook", "microsoft.outlook", Agent.Type.EMAIL),
    AgentMatcher("MSIE", "msie", Agent.Type.BROWSER),
    AgentMatcher("MSIE11", "rv:11", Agent.Type.BROWSER),
    AgentMatcher("Yammer Desktop", "AdobeAir", Agent.Type.APP),
    AgentMatcher("Yammer Mobile", "Yammer[\\s]+([\\d\\w\\.\\-]+)", Agent.Type.APP),
    AgentMatcher("Apache HTTP Client", "Apache\\\\-HttpClient", Agent.Type.PROGRAM),
    AgentMatcher("BlackBerry", "BlackBerry", Agent.Type.SYSTEM),
  )

  fun analyze(value: String): Agent =
    matchers.firstOrNull { it.match(value) }?.let {
      Agent(it.type.toString(), it.name)
    } ?: UNKNOWN_AGENT
}
