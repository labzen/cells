package cn.labzen.cells.network.http.server

@Target(AnnotationTarget.ANNOTATION_CLASS)
internal annotation class LynxHttpServerAnnotation

/**
 * 标识一个类可处理API Server服务
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.TYPE)
annotation class HttpServerHandler(val path: String, val disabled: Boolean = false)

/**
 * API接口，Get Method
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Get(val value: String)

/**
 * API接口，Post Method
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Post(val value: String)

/**
 * API接口，Put Method
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Put(val value: String)

/**
 * API接口，Patch Method
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Patch(val value: String)

/**
 * API接口，Delete Method
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Delete(val value: String)

/**
 * API接口出错时的处理
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class Error

/**
 * 接收所有适配的API接口方法
 */
@LynxHttpServerAnnotation
@Target(AnnotationTarget.FUNCTION)
annotation class All
