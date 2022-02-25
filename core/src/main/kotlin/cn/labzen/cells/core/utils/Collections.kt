package cn.labzen.cells.core.utils

import java.util.Collections

object Collections {

  /**
   * 集合是NULL或0元素
   */
  @JvmStatic
  fun isNullOrEmpty(collection: Iterable<*>?) =
    collection?.toList()?.isEmpty() ?: true

  /**
   * 剔除集合中的null元素，如果参数为空，返回空集合
   * @param collection Iterable<E>? 集合
   * @return List<E> 剔除null后的集合，可能是空集合，但不会为null
   */
  @JvmStatic
  fun <E : Any> removeNull(collection: Iterable<E?>?): List<E> =
    collection?.filterNotNull() ?: Collections.emptyList()

  /**
   * 剔除集合中的null值与空内容字符串，如果参数为空，返回空集合
   * @param collection Iterable<String>? 集合
   * @return List<E> 剔除后的集合，可能是空集合，但不会为null
   */
  @JvmStatic
  fun removeBlank(collection: Iterable<String?>?): List<String> =
    collection?.filterNotNull()?.filterNot { it.isBlank() } ?: Collections.emptyList()
}
