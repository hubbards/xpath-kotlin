package com.github.hubbards.xpath

/**
 * A number literal, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#numbers).
 */
class LiteralNumber(number: Number) : Expression() {
  private val syntax = number.toString()

  override fun abbreviated() = syntax

  override fun unabbreviated() = syntax
}
