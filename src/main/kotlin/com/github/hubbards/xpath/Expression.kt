package com.github.hubbards.xpath

import com.github.hubbards.xpath.Operator.*

/**
 * An XPath expression, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Expressions).
 */
abstract class Expression : Syntax {
  infix fun equal(e: Expression) =
      BinaryExpression(EQUAL, this, e)

  infix fun notEqual(e: Expression) =
      BinaryExpression(NOT_EQUAL, this, e)

  infix fun greaterThan(e: Expression) =
      BinaryExpression(GREATER_THAN, this, e)

  infix fun lessThan(e: Expression) =
      BinaryExpression(LESS_THAN, this, e)

  infix fun greaterThanOrEqualTo(e: Expression) =
      BinaryExpression(GREATER_THAN_OR_EQUAL_TO, this, e)

  infix fun lessThanOrEqualTo(e: Expression) =
      BinaryExpression(LESS_THAN_OR_EQUAL_TO, this, e)

  infix fun and(e: Expression) =
      BinaryExpression(AND, this, e)

  infix fun or(e: Expression) =
      BinaryExpression(OR, this, e)

  operator fun unaryMinus() =
      UnaryExpression(this)

  operator fun plus(e: Expression) =
      BinaryExpression(PLUS, this, e)

  operator fun minus(e: Expression) =
      BinaryExpression(MINUS, this, e)

  operator fun times(e: Expression) =
      BinaryExpression(TIMES, this, e)

  operator fun div(e: Expression) =
      BinaryExpression(DIVIDE, this, e)

  operator fun mod(e: Expression) =
      BinaryExpression(MODULO, this, e)

  operator fun rem(e: Expression) =
      BinaryExpression(MODULO, this, e)

  infix fun union(e: Expression) =
      BinaryExpression(UNION, this, e)

  override fun toString() =
      abbreviated()
}
