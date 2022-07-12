package cn.labzen.cells.network.http.server.core.support.meta

import cn.labzen.cells.core.utils.Collections
import cn.labzen.cells.network.http.server.annotation.*
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method
import cn.labzen.cells.network.http.meta.Method as HttpMethod

internal class MappedMethodProcessor(mc: MappablePath) : MultilevelMethod(), MappedElementProcessor {

  init {
    this.parent = mc
    initSubLevels()
  }

  override fun register(element: AnnotatedElement) {
    val annotations = element.annotations.filter { a ->
      a.annotationClass.annotations.any { sa ->
        sa.annotationClass ==
            Path::class
      }
    }

    val isRestful = parent!!.restful ?: false || element.isAnnotationPresent(Restful::class.java)
    val withoutResultAnnotationSize = annotations.filterNot { it is Restful }.size

    for (annotation in annotations) {
      // annotations.forEach { annotation ->
      val (httpMethod, paths) = when (annotation) {
        is Restful -> {
          if (withoutResultAnnotationSize == 0) {
            Pair(HttpMethod.GET, element.getAnnotation(Restful::class.java).value)
          } else {
            continue
          }
        }
        is Get -> Pair(HttpMethod.GET, element.getAnnotation(Get::class.java).value)
        is Post -> Pair(HttpMethod.POST, element.getAnnotation(Post::class.java).value)
        is Put -> Pair(HttpMethod.PUT, element.getAnnotation(Put::class.java).value)
        is Patch -> Pair(HttpMethod.PATCH, element.getAnnotation(Patch::class.java).value)
        is Delete -> Pair(HttpMethod.DELETE, element.getAnnotation(Delete::class.java).value)
        is Options -> Pair(HttpMethod.OPTIONS, element.getAnnotation(Options::class.java).value)
        else -> continue
      }

      Collections.onValueAtLeast(paths.toList(), "").forEach {
        val segments = it.trim(' ', '/').split('/')
        registerInternal(segments, 0, element as Method, httpMethod, isRestful)
      }
    }
  }

  fun find(path: String): Set<MappedMethod> {
    val segments = path.trim(' ', '/').split('/')
    return findInternal(segments, 0)
  }
}
