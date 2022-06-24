package cn.labzen.cells.core.spring

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.Ordered

class LabzenSpringGuiderInitializer : ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

  override fun getOrder(): Int = 1

  override fun initialize(applicationContext: ConfigurableApplicationContext) {
    SpringGuider.initApplicationContext(applicationContext)
  }
}
