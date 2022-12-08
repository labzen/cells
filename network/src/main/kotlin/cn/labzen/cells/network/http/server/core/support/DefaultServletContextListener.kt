package cn.labzen.cells.network.http.server.core.support

import cn.labzen.cells.core.definition.Constants
import cn.labzen.cells.network.http.server.ServerConfiguration
import cn.labzen.cells.network.http.server.core.handler.Filtering
import cn.labzen.spring.helper.Springs
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

class DefaultServletContextListener(
  private val configuration: ServerConfiguration,
) : ServletContextListener {

  override fun contextInitialized(sce: ServletContextEvent) {
    val servletContext = sce.servletContext
    servletContext.requestCharacterEncoding = Constants.DEFAULT_CHARSET_NAME
    servletContext.responseCharacterEncoding = Constants.DEFAULT_CHARSET_NAME

    configuration.context = servletContext
    val dispatcher = Springs.getOrCreate(Dispatcher::class.java)
    val dynamicServlet = servletContext.addServlet("dispatcher", dispatcher)
    dynamicServlet.addMapping(configuration.contextPath)

    val filters = initFiltering()
    filters.forEach {
      val dynamicFilter = servletContext.addFilter(it.name(), it)
      dynamicFilter.addMappingForUrlPatterns(it.dispatcherTypes(), true, it.path())
    }

    ViewRenderService.initialize(configuration)
  }

  override fun contextDestroyed(sce: ServletContextEvent?) {
    super.contextDestroyed(sce)
  }

  private fun initFiltering(): List<Filtering> {
    val filters =
      Springs.scanClassesByAnnotation("cn.labzen.cells.network.http.server.core.handler.filter", Filtering::class.java)
    return filters.map {
      val filtering = it.getConstructor(ServerConfiguration::class.java).newInstance(configuration) as Filtering
      Springs.register(filtering.name(), filtering)
      filtering
    }.sortedBy {
      it.order()
    }
  }
}
