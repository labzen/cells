package cn.labzen.cells.network.http.server.core.support.meta

import cn.labzen.cells.network.http.server.annotation.Path
import cn.labzen.cells.network.http.server.annotation.Restful
import java.lang.reflect.AnnotatedElement

internal class MappedClassProcessor : MultilevelClass(), MappedElementProcessor {

  init {
    initSubLevels()
  }

  override fun register(element: AnnotatedElement) {
    val isRestful = element.isAnnotationPresent(Restful::class.java)
    val isPathPresent = element.isAnnotationPresent(Path::class.java)

    val paths = if (isPathPresent) {
      element.getAnnotation(Path::class.java).value
    } else {
      element.getAnnotation(Restful::class.java).value
    }.toMutableList()
    if (paths.isEmpty()) {
      paths.add("")
    }

    paths.forEach {
      val segments = it.trim(' ', '/').split('/')
      registerInternal(segments, 0, element as Class<*>, isRestful)
    }
  }

  fun find(path: String): Set<Pair<MappedClass, MappedMethod>> {
    val segments = path.trim(' ', '/').split('/')
    return findInternal(segments, 0)
  }
}
