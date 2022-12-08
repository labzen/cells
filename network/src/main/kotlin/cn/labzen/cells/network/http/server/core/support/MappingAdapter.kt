package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.network.exception.HttpServerException
import cn.labzen.cells.network.http.meta.ContentType
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.support.meta.*
import cn.labzen.cells.network.http.server.core.view.*
import cn.labzen.logger.kotlin.logger
import io.undertow.util.Headers
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import cn.labzen.cells.network.http.meta.Method as HttpMethod

internal class MappingAdapter(
  private val configuration: ServerConfiguration,
  private val found: Set<Pair<MappedClass, MappedMethod>>
) {

  private val logger = LoggerFactory.getLogger(MappingAdapter::class.java)
  private val variableParameters = mutableMapOf<String, String>()

  fun handle(req: HttpServletRequest, resp: HttpServletResponse): View {
    val picked = found.filter {
      pick(it, req, resp)
    }
    if (picked.isEmpty()) {
      return notFoundView
    }
    if (picked.size > 1) {
      return ConflictingMappingView(picked)
    }

    val hit = picked[0]
    val mc = hit.first
    val mm = hit.second
    mc.instanceIfNecessary()

    val parameters = collectParameters(req, resp, mm)
    return try {
      val result: Any? = if (parameters.isEmpty()) {
        mm.method.invoke(mc.instance)
      } else {
        mm.method.invoke(mc.instance, *parameters)
      }

      val isRestful = mc.restful ?: false || mm.restful ?: false
      val produce = mm.produce
        ?: mc.produce
        ?: if (isRestful)
          configuration.defaultRestfulProduce
        else configuration.defaultPageProduce

      if (isRestful) {
        RestfulView(produce, result)
      } else {
        PageView(produce, mm.template, result)
      }
    } catch (e: Exception) {
      InternalExceptionView(e)
    }
  }

  private fun pick(
    mapped: Pair<MappedClass, MappedMethod>,
    req: HttpServletRequest,
    resp: HttpServletResponse
  ): Boolean {
    val mc = mapped.first
    val mm = mapped.second

    return try {
      assertHttpMethod(req, mm.httpMethod)
      assertHttpConsume(req, mc, mm)
      assertHttpProduce(req, mc, mm)
      true
    } catch (e: HttpServerException) {
      logger.debug("${e.message} :: ${mc.cls.name}#${mm.method.name}()")
      false
    }
  }

  private fun assertHttpMethod(req: HttpServletRequest, httpMethod: HttpMethod?) {
    if (!req.method.equals(httpMethod.toString(), true)) {
      throw HttpServerException("不支持的 request method: ${req.method}")
    }
  }

  private fun assertHttpConsume(req: HttpServletRequest, mc: MappedClass, mm: MappedMethod) {
    val consume = mm.consume ?: mc.consume ?: configuration.defaultConsume
    if (consume == ContentType.ALL) {
      return
    }

    val requestContentType = req.getHeader(Headers.CONTENT_TYPE_STRING) ?: return
    if (!requestContentType.contains(consume.value)) {
      throw HttpServerException("不支持的 request content-type: $requestContentType")
    }
  }

  private fun assertHttpProduce(req: HttpServletRequest, mc: MappedClass, mm: MappedMethod) {
    val isRestful = mc.restful ?: false || mm.restful ?: false
    val produce = mm.produce
      ?: mc.produce
      ?: if (isRestful)
        configuration.defaultRestfulProduce
      else configuration.defaultPageProduce

    val requestAccept = req.getHeader(Headers.ACCEPT_STRING)
    if ("*/*" == requestAccept) {
      return
    }

    val ps = produce.value.split(";")
    if (!ps.any { requestAccept.contains(it) }) {
      throw HttpServerException("不支持的 request accept: $requestAccept")
    }
  }

  private fun checkVariables(mapped: MappablePath?, pathSegments: List<String>, index: Int) {
    if (mapped == null || index < 0) {
      return
    }

    val variableValue = pathSegments[index]
    if (mapped.variable) {
      val key = mapped.segment.removeSurrounding("{", "}")
      variableParameters[key] = variableValue
    }

    checkVariables(mapped.parent(), pathSegments, index - 1)
  }

  private fun collectParameters(req: HttpServletRequest, resp: HttpServletResponse, mm: MappedMethod): Array<Any?> {
    val segments = req.requestURI.trim(' ', '/').split('/')
    checkVariables(mm, segments, segments.lastIndex)

    val args = Array(mm.parameters.size) {
      val param = mm.parameters[it]
      resolveParameter(req, resp, param)
    }
    return args
  }

  private fun resolveParameter(
    req: HttpServletRequest,
    resp: HttpServletResponse,
    mappedParameter: MappedParameter
  ): Any? =
    // todo 默认值参数未使用
    when (mappedParameter.from) {
      ParameterFrom.PARAMETER -> convertParameterValue(req.getParameter(mappedParameter.paramName), mappedParameter)
      ParameterFrom.SESSION -> convertParameterValue(
        req.session.getAttribute(mappedParameter.paramName),
        mappedParameter
      )
      ParameterFrom.COOKIE -> convertParameterValue(
        req.cookies.find { it.name == mappedParameter.paramName }?.value,
        mappedParameter
      )
      ParameterFrom.HEADER -> convertParameterValue(req.getHeader(mappedParameter.paramName), mappedParameter)
      ParameterFrom.PATH -> convertParameterValue(variableParameters[mappedParameter.paramName], mappedParameter)
      ParameterFrom.OTHER -> systemParameterValue(req, resp, mappedParameter)
    }

  private fun convertParameterValue(value: Any?, mappedParameter: MappedParameter): Any? {
    value ?: return null

    if (value.javaClass == mappedParameter.type) {
      return value
    }
    return null
  }

  private fun convertParameterValue(value: String?, mappedParameter: MappedParameter): Any? {
    value ?: return null

    if (ConvertService.support(String::class.java, mappedParameter.type)) {
      return ConvertService.convert(value, mappedParameter.type)
    }
    return null
  }

  private fun systemParameterValue(
    req: HttpServletRequest,
    resp: HttpServletResponse,
    mappedParameter: MappedParameter
  ): Any? =
    when (mappedParameter.type) {
      HttpServletRequest::class.java -> req
      HttpServletResponse::class.java -> resp
      HttpSession::class.java -> req.session
      else -> null
    }

  companion object {
    private val notFoundView = NotFoundView()
  }
}
