package cn.labzen.cells.network.http.ua

import cn.labzen.cells.network.http.ua.bean.Agent
import cn.labzen.cells.network.http.ua.matcher.AgentMatcher

object AgentAnalyzer {

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
