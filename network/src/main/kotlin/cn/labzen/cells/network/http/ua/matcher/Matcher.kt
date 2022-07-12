package cn.labzen.cells.network.http.ua.matcher

abstract class Matcher(val name: String, pattern: String) {

  private val regex = Regex(pattern, RegexOption.IGNORE_CASE)

  fun match(value: String): Boolean =
    regex.containsMatchIn(value)
}
