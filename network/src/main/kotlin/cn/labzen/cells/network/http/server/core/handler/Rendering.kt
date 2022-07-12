package cn.labzen.cells.network.http.server.core.handler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface Rendering {

  fun render(templatePath: String, request: HttpServletRequest, response: HttpServletResponse, result: Any?)
}
