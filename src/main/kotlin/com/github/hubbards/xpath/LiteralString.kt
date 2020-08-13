package com.github.hubbards.xpath

/**
 * A [string][specification] literal.
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#strings
 */
data class LiteralString(val string: String) : Expression() {
  override val unabbreviated =
    buildString {
      append('\'')
      append(string)
      append('\'')
    }
}
