package cn.labzen.cells.core.definition

/**
 * 频率，频繁程度（主观评定）
 */
@Suppress("unused")
enum class Frequencies {

  /*
    todo  加入新的枚举，表达概率性    probabilistic

    definitely  100%
    probably    70%
    maybe       50%
    perhaps     30%
    possibly    10%
    never       0%
  */

  /**
   * 极端的频繁
   */
  EXTREMELY,

  /**
   * 经常的
   */
  OFTEN,

  /**
   * 定期的，有规律的
   */
  REGULAR,

  /**
   * 偶然的
   */
  CASUAL,

  /**
   * 几乎不
   */
  SCARCELY,

  /**
   * 不确定的
   */
  UNCLEAR
}
