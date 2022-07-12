package cn.labzen.cells.network.http.ua.bean

import cn.labzen.cells.network.http.ua.AgentAnalyzer
import cn.labzen.cells.network.http.ua.DeviceAnalyzer
import cn.labzen.cells.network.http.ua.LayoutEngineAnalyzer
import cn.labzen.cells.network.http.ua.OperatingSystemAnalyzer
import com.google.common.cache.CacheBuilder
import java.util.concurrent.TimeUnit

data class UserAgent internal constructor(
  val original: String,
  val device: Device,
  val operatingSystem: OperatingSystem,
  val layoutEngine: LayoutEngine,
  val agent: Agent
) {

  companion object {
    /**
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
