package cn.labzen.cells.network.http.ua.matcher

import cn.labzen.cells.network.http.ua.bean.Agent

class AgentMatcher(
  name: String,
  pattern: String,
  val type: Agent.Type
) : Matcher(name, pattern)
