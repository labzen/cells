package cn.labzen.cells.network.http.ua

import cn.labzen.cells.network.http.ua.bean.LayoutEngine
import cn.labzen.cells.network.http.ua.matcher.LayoutEngineMatcher

object LayoutEngineAnalyzer {

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
