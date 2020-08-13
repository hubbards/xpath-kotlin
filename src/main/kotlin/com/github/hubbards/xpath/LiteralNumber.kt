package com.github.hubbards.xpath

/**
 * A [number][specification] literal.
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#numbers
 */
data class LiteralNumber(val number: Number) : Expression() {
  override val unabbreviated =
    number.toString()
}
