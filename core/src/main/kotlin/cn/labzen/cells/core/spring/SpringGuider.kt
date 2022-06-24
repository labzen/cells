package cn.labzen.cells.core.spring

import org.springframework.beans.BeansException
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.AbstractBeanDefinition
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.core.type.filter.AssignableTypeFilter
import org.springframework.util.ClassUtils
import java.util.*
import java.util.function.Consumer

object SpringGuider {

  private lateinit var ac: ConfigurableApplicationContext
  private lateinit var bf: ConfigurableListableBeanFactory
  private lateinit var env: ConfigurableEnvironment

  internal fun initApplicationContext(applicationContext: ConfigurableApplicationContext) {
    ac = applicationContext
    bf = ac.beanFactory
    env = ac.environment
  }

  // ===================================================================================================================

  fun <T : Any> bean(type: Class<T>): Optional<T> {
    return Optional.ofNullable(
      try {
        bf.getBean(type)
      } catch (e: BeansException) {
        null
      }
    )
  }

  fun <T : Any> beans(type: Class<T>): Map<String, T> =
    try {
      ac.getBeansOfType(type)
    } catch (e: BeansException) {
      emptyMap()
    }

  fun beanNames(type: Class<Any>): List<String> =
    ac.getBeanNamesForType(type).toList()

  fun isSingleton(type: Class<Any>): Optional<Boolean> =
    Optional.ofNullable(
      beanNames(type).let {
        when (it.size) {
          0 -> null
          1 -> isSingleton(it[0]).orElse(null)
          else -> false
        }
      }
    )

  fun isSingleton(name: String): Optional<Boolean> =
    Optional.ofNullable(
      try {
        bf.isSingleton(name)
      } catch (e: NoSuchBeanDefinitionException) {
        null
      }
    )

  fun <T : Any> register(type: Class<T>): T {
    val autowireMode = resolveAutowireMode(type)

    @Suppress("UNCHECKED_CAST")
    val bean: T = bf.createBean(type, autowireMode, true) as T
    bf.registerSingleton(type.simpleName, bean)
    return bean
  }

  @JvmOverloads
  fun register(bean: Any, name: String? = null) {
    val type = bean.javaClass
    val autowireMode = resolveAutowireMode(type)
    val beanName = name ?: type.simpleName

    bf.autowireBeanProperties(bean, autowireMode, true)
    bf.registerSingleton(beanName, bean)
  }

  fun <T : Any> getOrCreate(type: Class<T>): T =
    bean(type).orElseGet { register(type) }

  private fun resolveAutowireMode(type: Class<*>) =
    if (type.constructors.any { it.parameterCount == 0 }) {
      AbstractBeanDefinition.AUTOWIRE_BY_TYPE
    } else {
      AbstractBeanDefinition.AUTOWIRE_BY_TYPE
    }

  // ===================================================================================================================

  fun activatedProfiles(): List<String> =
    env.activeProfiles.toList()

  fun isProfileActivated(name: String): Boolean =
    env.activeProfiles.contains(name)

  fun environment(name: String, defaultValue: String? = null) =
    defaultValue?.let {
      env.getProperty(name, it)
    } ?: env.getProperty(name)

  // ===================================================================================================================

  fun scan(pkg: String, type: Class<out Any>, vararg annotations: Class<out Annotation>): Set<Class<*>> =
    scan(pkg) { provider ->
      provider.addIncludeFilter(AssignableTypeFilter(type))
      annotations.forEach { ann ->
        provider.addIncludeFilter(AnnotationTypeFilter(ann))
      }
    }

  fun scan(pkg: String, consumer: Consumer<ClassPathScanningCandidateComponentProvider>): Set<Class<*>> {
    val provider = ClassPathScanningCandidateComponentProvider(false)
    provider.environment = env
    provider.resourceLoader = ac

    consumer.accept(provider)

    return provider.findCandidateComponents(pkg).map {
      ClassUtils.forName(it.beanClassName!!, ac.classLoader)
    }.toSet()
  }

}
