package cn.labzen.cells.network.http.server.annotation

import cn.labzen.cells.network.http.meta.ContentType

/**
 * 声明一个（组）URL，如未单独声明 http method 的注解，默认为 Get；URL如果不指定，路径为上级目录
 *
 * 以 "/" 开头的URL，为绝对路径，否则为相对路径
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(
  AnnotationTarget.ANNOTATION_CLASS,
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION
)
annotation class Path(val value: Array<String> = [])

/**
 * 声明类下的所有路径、或声明方法路径，为REST
 *
 * 当声明在方法上时：
 * 1. 只有[Restful]注解：可以设置[value]值，方法为 GET
 * 2. 当带有其他的一个或多个[Path]注解时，例如 Post, Get 等，则[value]值将被忽略
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Restful(val value: Array<String> = [])

/**
 * 声明一个 GET 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Get(val value: Array<String> = [])

/**
 * 声明一个 POST 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Post(val value: Array<String> = [])

/**
 * 声明一个 PUT 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Put(val value: Array<String> = [])

/**
 * 声明一个 DELETE 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Delete(val value: Array<String> = [])

/**
 * 声明一个 PATCH 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Patch(val value: Array<String> = [])

/**
 * 声明一个 OPTIONS 方法的URL
 */
@Path
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Options(val value: Array<String> = [])

// -------------------------------------------------------------------------------------------------

/**
 * 声明一个参数接收request的parameter，默认parameter key为参数名，如果参数名、类型等均相同，可不需要此注解，会自动注入相应的值
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Parameter(val value: String = "", val defaultValue: String = "", val converter: String = "")

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Session(val value: String = "", val defaultValue: String = "", val converter: String = "")

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Cookie(val value: String = "", val defaultValue: String = "", val converter: String = "")

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Header(val value: String = "", val defaultValue: String = "", val converter: String = "")

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Variable(val value: String = "", val converter: String = "")

// -------------------------------------------------------------------------------------------------

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Consume(val contentType: ContentType = ContentType.ALL)

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Produce(val contentType: ContentType = ContentType.JSON)

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Body

/**
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Part

// -------------------------------------------------------------------------------------------------

/**
 * 声明响应的页面模板（当不为Restful时），如果不设置：
 * 1. 返回值如果是String，则认为该返回值为指向模板位置
 * 2. 返回值如果不是String，则异常
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Template(val value: String)

@Retention(AnnotationRetention.RUNTIME)
@Target
annotation class Failure(val statusCode: Int = -1)
