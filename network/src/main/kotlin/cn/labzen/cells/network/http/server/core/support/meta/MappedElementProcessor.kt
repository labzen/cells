package cn.labzen.cells.network.http.server.core.support.meta

import java.lang.reflect.AnnotatedElement

interface MappedElementProcessor {

  fun register(element: AnnotatedElement)
}
