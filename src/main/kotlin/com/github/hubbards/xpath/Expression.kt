package com.github.hubbards.xpath

/**
 * An XPath [expression][specification].
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Expressions
 */
abstract class Expression : Syntax {
  infix fun equal(other: Expression): Expression =
    BinaryExpression(Operator.EQUAL, this, other)

  infix fun notEqual(other: Expression) =
    BinaryExpression(Operator.NOT_EQUAL, this, other)

  infix fun greaterThan(other: Expression) =
    BinaryExpression(Operator.GREATER_THAN, this, other)

  infix fun lessThan(other: Expression) =
    BinaryExpression(Operator.LESS_THAN, this, other)

  infix fun greaterThanOrEqualTo(other: Expression) =
    BinaryExpression(Operator.GREATER_THAN_OR_EQUAL_TO, this, other)

  infix fun lessThanOrEqualTo(other: Expression) =
    BinaryExpression(Operator.LESS_THAN_OR_EQUAL_TO, this, other)

  infix fun and(other: Expression) =
    BinaryExpression(Operator.AND, this, other)

  infix fun or(other: Expression) =
    BinaryExpression(Operator.OR, this, other)

  operator fun unaryMinus(): Expression =
    UnaryMinusExpression(this)

  operator fun plus(other: Expression): Expression =
    BinaryExpression(Operator.PLUS, this, other)

  operator fun minus(other: Expression) =
    BinaryExpression(Operator.MINUS, this, other)

  operator fun times(other: Expression) =
    BinaryExpression(Operator.TIMES, this, other)

  operator fun div(other: Expression) =
    BinaryExpression(Operator.DIVIDE, this, other)

  operator fun rem(other: Expression) =
    BinaryExpression(Operator.MODULO, this, other)

  infix fun union(other: Expression) =
    BinaryExpression(Operator.UNION, this, other)
}
