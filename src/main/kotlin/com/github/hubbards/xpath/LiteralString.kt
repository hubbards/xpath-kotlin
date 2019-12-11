package com.github.hubbards.xpath

/**
 * A string literal, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#strings).
 */
class LiteralString(string: String) : Expression() {
  private val syntax = "'$string'"

  override fun abbreviated() = syntax

  override fun unabbreviated() = syntax
}
