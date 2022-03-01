package cn.labzen.cells.network.http.server.handler

import cn.labzen.cells.network.http.server.*
import cn.labzen.cells.network.http.server.bean.HandleMode
import cn.labzen.cells.network.http.server.bean.HandleMode.*
import cn.labzen.cells.network.http.server.bean.MethodDefinition
import cn.labzen.cells.network.http.server.bean.ParameterDefinition
import cn.labzen.cells.network.http.server.bean.ParameterMode
import cn.labzen.cells.network.http.server.bean.ParameterMode.*
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpRequest
import org.checkerframework.checker.nullness.qual.NonNull
import org.jetbrains.annotations.NotNull
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import javax.annotation.Nonnull
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@ChannelHandler.Sharable
class DefaultHttpServerHandler internal constructor() :
  LynxHttpServerHandler, SimpleChannelInboundHandler<FullHttpRequest>() {

  private val logger = LoggerFactory.getLogger(DefaultHttpServerHandler::class.java)
  private var springContext: ApplicationContext? = null
  private lateinit var handlers: List<MethodDefinition>

  override fun setSpringContext(context: ApplicationContext) {
    springContext = context
  }

  override fun scanAndInitialize(packagePath: String) {
    val reflections = Reflections(
      packagePath,
      SubTypesScanner(false),
      TypeAnnotationsScanner()
    )
    val types = reflections.getTypesAnnotatedWith(HttpServerHandler::class.java)
    val instances = instantiate(types)
    parseExecutableMethod(instances)
  }

  private fun instantiate(types: Set<Class<*>>): Map<Class<*>, Any> {
    val result = mutableMapOf<Class<*>, Any>()
    var instance: Any?
    for (type in types) {
      instance = findInstanceInSpringContext(type) ?: tryCreateInstance(type)

      if (instance != null) {
        result[type] = instance
      } else {
        logger.warn("找不着啊~~ 也创建不了啊，您这啥类啊 - [{}]", type)
      }
    }

    return result.toMap()
  }

  private fun findInstanceInSpringContext(cls: Class<*>): Any? =
    springContext?.let {
      try {
        it.getBean(cls)
      } catch (e: Exception) {
        null
      }
    }

  private fun tryCreateInstance(cls: Class<*>): Any? =
    try {
      cls.getConstructor().newInstance()
    } catch (e: Exception) {
      null
    }

  private fun parseExecutableMethod(instances: Map<Class<*>, Any>) {
    // 最终返回 Pair 的无序集合，Pair存放bean实例，和注解了 @All 的方法
    val result = mutableListOf<MethodDefinition>()
    instances.forEach { entry ->
      val methodDefinitions = entry.key.methods.mapNotNull {
        convertToMethodDefinition(it, entry.value)
      }

      result.addAll(methodDefinitions)
    }
    handlers = result.toList()
  }

  private fun convertToMethodDefinition(method: Method, instance: Any): MethodDefinition? {
    val handleMethod = methodsHandleMethod(method) ?: return null

    val paramRequirements = method.parameters.map {
      val paramType = methodsParamType(it)
      val required = it.annotations.any { annotation ->
        annotation is Nonnull
            || annotation is NotNull
            || annotation is NonNull
      }
      ParameterDefinition(it.name, it.type, paramType, required)
    }

    return MethodDefinition(method.name, method, instance, handleMethod, paramRequirements)
  }

  private fun methodsParamType(parameter: Parameter): ParameterMode =
    when (parameter.type) {
      ChannelHandlerContext::class.java -> CONTEXT
      HttpServletRequest::class.java -> REQUEST
      HttpServletResponse::class.java -> RESPONSE
      HttpSession::class.java -> SESSION
      else -> PARAM
    }

  private fun methodsHandleMethod(method: Method): HandleMode? =
    when {
      method.isAnnotationPresent(All::class.java) -> ALL
      method.isAnnotationPresent(Get::class.java) -> GET
      method.isAnnotationPresent(Post::class.java) -> POST
      method.isAnnotationPresent(Put::class.java) -> PUT
      method.isAnnotationPresent(Patch::class.java) -> PATCH
      method.isAnnotationPresent(Delete::class.java) -> DELETE
      method.isAnnotationPresent(Error::class.java) -> ERROR
      else -> null
    }

  override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpRequest?) {
    TODO("Not yet implemented")
  }
}
