package cn.labzen.cells.network.http.server

import cn.labzen.cells.network.http.meta.ContentType
import javax.servlet.ServletContext

data class ServerConfiguration internal constructor(
  val port: Int,
  val host: String,
  val contextPath: String,
  val mappingPackage: String,
  val sessionTimeout: Int,
  val defaultConsume: ContentType,
  val defaultPageProduce: ContentType,
  val defaultRestfulProduce: ContentType,
  val templatePath: String,
  val resourcesPath: List<String>
) {
  lateinit var context: ServletContext

  internal constructor(builder: Builder) : this(
    builder.port,
    builder.host,
    builder.contextPath,
    builder.mappingPackage,
    builder.sessionTimeout,
    builder.defaultConsume,
    builder.defaultPageProduce,
    builder.defaultRestfulProduce,
    builder.templatePath,
    builder.resourcesPath
  )

  companion object {

    @JvmStatic
    fun create(port: Int, mappingPackage: String) =
      Builder(port, mappingPackage)

    @JvmStatic
    inline fun build(port: Int, mappingPackage: String, block: Builder.() -> Unit) =
      Builder(port, mappingPackage).apply(block).build()
  }

  class Builder(val port: Int, val mappingPackage: String) {

    var host: String = "0.0.0.0"
    var contextPath: String = "/"
    var sessionTimeout: Int = 3
    var defaultConsume: ContentType = ContentType.ALL
    var defaultPageProduce: ContentType = ContentType.TXT_HTML_UTF8
    var defaultRestfulProduce: ContentType = ContentType.JSON_UTF8
    var templatePath: String = "template"
    var resourcesPath = mutableListOf("static")

    fun addResourcePath(path: String): Builder {
      resourcesPath.add(path)
      return this
    }

    fun build() = ServerConfiguration(this)
  }

  override fun hashCode(): Int {
    var result = port
    result = 31 * result + host.hashCode()
    result = 31 * result + contextPath.hashCode()
    result = 31 * result + mappingPackage.hashCode()
    result = 31 * result + sessionTimeout
    return result
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ServerConfiguration

    if (port != other.port) return false
    if (host != other.host) return false
    if (contextPath != other.contextPath) return false
    if (mappingPackage != other.mappingPackage) return false
    if (sessionTimeout != other.sessionTimeout) return false
    if (defaultConsume != other.defaultConsume) return false
    if (defaultPageProduce != other.defaultPageProduce) return false
    if (defaultRestfulProduce != other.defaultRestfulProduce) return false
    if (resourcesPath != other.resourcesPath) return false
    if (templatePath != other.templatePath) return false
    if (context != other.context) return false

    return true
  }

}
