package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.core.spring.SpringGuider
import cn.labzen.cells.network.http.server.annotation.Path
import cn.labzen.cells.network.http.server.annotation.Restful
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.support.meta.MappedClassProcessor
import org.springframework.core.type.filter.AnnotationTypeFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class MappingsResolver(private val configuration: ServerConfiguration) {

  // 所有路径映射的元信息保存根
  private val mcProcessor = MappedClassProcessor()

  init {
    val mappingClasses = SpringGuider.scan(configuration.mappingPackage) {
      it.addIncludeFilter(AnnotationTypeFilter(Path::class.java))
      it.addIncludeFilter(AnnotationTypeFilter(Restful::class.java))
    }

    mappingClasses.forEach {
      mcProcessor.register(it)
    }
  }

  fun resolve(req: HttpServletRequest, resp: HttpServletResponse): MappingAdapter {
    val found = mcProcessor.find(req.requestURI)
    return MappingAdapter(configuration, found)
  }

}
