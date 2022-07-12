package cn.labzen.cells.network.http.server.core.view

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface View {

  fun render(req: HttpServletRequest, resp: HttpServletResponse)
}
