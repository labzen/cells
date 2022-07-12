package cn.labzen.cells.network.http.server.core.support.meta

import cn.labzen.cells.core.bean.StrictQuadruple
import cn.labzen.cells.core.spring.SpringGuider
import cn.labzen.cells.core.utils.Strings
import cn.labzen.cells.network.exception.HttpServerException
import cn.labzen.cells.network.http.meta.ContentType
import cn.labzen.cells.network.http.server.annotation.*
import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import org.springframework.core.annotation.AnnotatedElementUtils
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import cn.labzen.cells.network.http.meta.Method as HttpMethod
import cn.labzen.cells.network.http.server.annotation.Parameter as Param

internal abstract class Multilevel<T : MappablePath> {

  protected var parent: MappablePath? = null
  protected lateinit var subLevels: SetMultimap<String, T>

  protected fun initSubLevels() {
    subLevels = HashMultimap.create(16, 4)
  }
}

internal interface MappablePath {

  val segment: String
  var handleable: Boolean
  var variable: Boolean
  var restful: Boolean?
  var consume: ContentType?
  var produce: ContentType?

  fun parent(): MappablePath?

  companion object {
    val variableRegex = Regex("^\\{\\w+\\}$")
  }
}

internal abstract class MultilevelClass : Multilevel<MappedClass>() {

  protected fun registerInternal(
    pathSegments: List<String>,
    index: Int,
    cls: Class<*>,
    isRestful: Boolean
  ) {
    if (index >= pathSegments.size) {
      return
    }

    val isLast = pathSegments.lastIndex == index

    val segment = pathSegments[index].trim()
    val mappedClasses = subLevels.get(segment)
    val mappedClass = mappedClasses.find {
      if (isLast) it.handleable && it.cls == cls
      else it.isNotHandleable()
    } ?: MappedClass(segment).also {
      if (this is MappablePath) {
        it.parent = this
      }
      mappedClasses.add(it)
    }

    if (isLast) {
      mappedClass.init(cls, isRestful)
      mappedClass.parseMethods()
      return
    }

    mappedClass.initSubLevels()
    mappedClass.registerInternal(pathSegments, index + 1, cls, isRestful)
  }

  protected fun findInternal(pathSegments: List<String>, index: Int): Set<Pair<MappedClass, MappedMethod>> {
    if (index >= pathSegments.size) {
      return emptySet()
    }

    val segment = pathSegments[index]
    val mappedClasses = subLevels.values().filter {
      it.segment == segment || it.variable
    }.toSet()

    return mappedClasses.flatMap {
      if (it.isNotHandleable()) {
        it.findInternal(pathSegments, index + 1)
      } else {
        val path = pathSegments.takeLast(pathSegments.lastIndex - index).joinToString("/")
        it.findMethods(path).map { mm -> Pair(it, mm) }
      }
    }.toSet()
  }
}

internal class MappedClass(override val segment: String) : MappablePath, MultilevelClass() {

  override var handleable: Boolean = false
  override var variable: Boolean = segment.matches(MappablePath.variableRegex)
  override var restful: Boolean? = null
  override var consume: ContentType? = null
  override var produce: ContentType? = null
  lateinit var cls: Class<*>
  lateinit var instance: Any
  private lateinit var mmProcessor: MappedMethodProcessor

  fun init(cls: Class<*>, isRestful: Boolean) {
    this.handleable = true
    this.cls = cls
    this.restful = isRestful

    this.consume = if (cls.isAnnotationPresent(Consume::class.java)) {
      cls.getAnnotation(Consume::class.java).contentType
    } else null
    this.produce = if (cls.isAnnotationPresent(Produce::class.java)) {
      cls.getAnnotation(Produce::class.java).contentType
    } else null
    this.mmProcessor = MappedMethodProcessor(this)
  }

  override fun parent(): MappablePath? =
    this.parent

  fun instanceIfNecessary() {
    if (!this::instance.isInitialized) {
      instance = SpringGuider.getOrCreate(cls)
    }
  }

  fun isNotHandleable() =
    !handleable && !this::cls.isInitialized

  fun parseMethods() {
    val mappedMethods = cls.methods.filter {
      AnnotatedElementUtils.hasAnnotation(it, Path::class.java)
    }

    mappedMethods.forEach(mmProcessor::register)
  }

  fun findMethods(path: String) =
    mmProcessor.find(path)
}

internal abstract class MultilevelMethod : Multilevel<MappedMethod>() {

  protected fun registerInternal(
    pathSegments: List<String>,
    index: Int,
    method: Method,
    httpMethod: HttpMethod,
    isRestful: Boolean
  ) {
    if (index >= pathSegments.size) {
      return
    }

    val isLast = pathSegments.lastIndex == index

    val segment = pathSegments[index].trim()
    val mappedMethods = subLevels.get(segment)
    val mappedMethod = mappedMethods.find {
      if (isLast) it.handleable && it.method == method && it.httpMethod == httpMethod
      else it.isNotHandleable()
    } ?: MappedMethod(segment).also {
      if (this is MappablePath) {
        it.parent = this
      }
      mappedMethods.add(it)
    }

    if (isLast) {
      mappedMethod.init(method, httpMethod, isRestful)

      mappedMethod.parseParameters()
      return
    }

    mappedMethod.initSubLevels()
    mappedMethod.registerInternal(pathSegments, index + 1, method, httpMethod, isRestful)
  }

  protected fun findInternal(pathSegments: List<String>, index: Int): Set<MappedMethod> {
    if (index >= pathSegments.size) {
      return emptySet()
    }

    val segment = pathSegments[index]
    val mappedMethods = subLevels.values().filter {
      it.segment == segment || it.variable
    }.toSet()

    return mappedMethods.flatMap {
      if (it.isNotHandleable()) {
        it.findInternal(pathSegments, index + 1)
      } else {
        setOf(it)
      }
    }.toSet()
  }
}

internal class MappedMethod(override val segment: String) : MappablePath, MultilevelMethod() {

  override var handleable: Boolean = false
  override var variable: Boolean = segment.matches(MappablePath.variableRegex)
  override var restful: Boolean? = null
  override var consume: ContentType? = null
  override var produce: ContentType? = null
  lateinit var method: Method
  var httpMethod: HttpMethod? = null
  var template: String? = null
  lateinit var parameters: MutableList<MappedParameter>

  fun init(method: Method, httpMethod: HttpMethod, isRestful: Boolean) {
    this.handleable = true
    this.method = method
    this.httpMethod = httpMethod
    this.restful = isRestful

    this.consume = if (method.isAnnotationPresent(Consume::class.java)) {
      method.getAnnotation(Consume::class.java).contentType
    } else null
    this.produce = if (method.isAnnotationPresent(Produce::class.java)) {
      this.method.getAnnotation(Produce::class.java).contentType
    } else null
    if (!isRestful) {
      if (method.isAnnotationPresent(Template::class.java)) {
        template = method.getAnnotation(Template::class.java).value
      } else if (method.returnType != String::class.java) {
        throw HttpServerException("未指定渲染模板 :: ${method.declaringClass.name}#${method.name}")
      }
    }
    parameters = mutableListOf()
  }

  override fun parent(): MappablePath? =
    this.parent

  fun isNotHandleable() =
    !handleable && !this::method.isInitialized

  fun parseParameters() {
    val rawParameters = method.parameters
    rawParameters.map(MappedParameter::parseParameter).forEach(parameters::add)
  }

  companion object {
    val systemParameterTypes = listOf<Class<*>>(
      HttpServletRequest::class.java,
      HttpServletResponse::class.java,
      HttpSession::class.java
    )
  }
}

internal class MappedParameter(
  val paramName: String,
  val type: Class<*>,
  val defaultValue: String,
  val converter: String,
  val from: ParameterFrom,
  val multipart: Boolean,
  val body: Boolean
) {

  companion object {
    fun parseParameter(parameter: Parameter): MappedParameter {
      val parameterName = parameter.name
      val (httpParameterName, from, converter, defaultValue) = when {
        parameter.isAnnotationPresent(Variable::class.java) -> {
          parameter.getAnnotation(Variable::class.java).let {
            val pn = Strings.blankToNull(it.value) ?: parameterName
            StrictQuadruple(pn, ParameterFrom.PATH, it.converter, "")
          }
        }

        parameter.isAnnotationPresent(Session::class.java) -> {
          parameter.getAnnotation(Session::class.java).let {
            val pn = Strings.blankToNull(it.value) ?: parameterName
            StrictQuadruple(pn, ParameterFrom.SESSION, it.converter, it.defaultValue)
          }
        }

        parameter.isAnnotationPresent(Cookie::class.java) -> {
          parameter.getAnnotation(Cookie::class.java).let {
            val pn = Strings.blankToNull(it.value) ?: parameterName
            StrictQuadruple(pn, ParameterFrom.COOKIE, it.converter, it.defaultValue)
          }
        }

        parameter.isAnnotationPresent(Header::class.java) -> {
          parameter.getAnnotation(Header::class.java).let {
            val pn = Strings.blankToNull(it.value) ?: parameterName
            StrictQuadruple(pn, ParameterFrom.HEADER, it.converter, it.defaultValue)
          }
        }

        parameter.isAnnotationPresent(Param::class.java) -> {
          parameter.getAnnotation(Param::class.java).let {
            val pn = Strings.blankToNull(it.value) ?: parameterName
            StrictQuadruple(pn, ParameterFrom.PARAMETER, it.converter, it.defaultValue)
          }
        }

        else -> {
          if (MappedMethod.systemParameterTypes.contains(parameter.type)) {
            StrictQuadruple(parameterName, ParameterFrom.OTHER, "", "")
          } else {
            StrictQuadruple(parameterName, ParameterFrom.PARAMETER, "", "")
          }
        }
      }

      val isMultipart = parameter.isAnnotationPresent(Part::class.java)
      val isBody = parameter.isAnnotationPresent(Body::class.java)

      return MappedParameter(httpParameterName, parameter.type, defaultValue, converter, from, isMultipart, isBody)
    }
  }
}

internal enum class ParameterFrom {

  PARAMETER, SESSION, COOKIE, HEADER, PATH, OTHER
}
