package com.github.hubbards.xpath

/**
 * An XPath [expression][specification].
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Expressions
 */
abstract class Expression : Syntax {
  /**
   * TODO document
   */
  infix fun equal(other: Expression): Expression =
    BinaryExpression(Operator.EQUAL, this, other)

  /**
   * TODO document
   */
  infix fun notEqual(other: Expression): Expression =
    BinaryExpression(Operator.NOT_EQUAL, this, other)

  /**
   * TODO document
   */
  infix fun greaterThan(other: Expression): Expression =
    BinaryExpression(Operator.GREATER_THAN, this, other)

  /**
   * TODO document
   */
  infix fun lessThan(other: Expression): Expression =
    BinaryExpression(Operator.LESS_THAN, this, other)

  /**
   * TODO document
   */
  infix fun greaterThanOrEqualTo(other: Expression): Expression =
    BinaryExpression(Operator.GREATER_THAN_OR_EQUAL_TO, this, other)

  /**
   * TODO document
   */
  infix fun lessThanOrEqualTo(other: Expression): Expression =
    BinaryExpression(Operator.LESS_THAN_OR_EQUAL_TO, this, other)

  /**
   * TODO document
   */
  infix fun and(other: Expression): Expression =
    BinaryExpression(Operator.AND, this, other)

  /**
   * TODO document
   */
  infix fun or(other: Expression): Expression =
    BinaryExpression(Operator.OR, this, other)

  /**
   * TODO document
   */
  operator fun unaryMinus(): Expression =
    UnaryMinusExpression(this)

  /**
   * TODO document
   */
  operator fun plus(other: Expression): Expression =
    BinaryExpression(Operator.PLUS, this, other)

  /**
   * TODO document
   */
  operator fun minus(other: Expression): Expression =
    BinaryExpression(Operator.MINUS, this, other)

  /**
   * TODO document
   */
  operator fun times(other: Expression): Expression =
    BinaryExpression(Operator.TIMES, this, other)

  /**
   * TODO document
   */
  operator fun div(other: Expression): Expression =
    BinaryExpression(Operator.DIVIDE, this, other)

  /**
   * TODO document
   */
  operator fun rem(other: Expression): Expression =
    BinaryExpression(Operator.MODULO, this, other)

  /**
   * TODO document
   */
  infix fun union(other: Expression): Expression =
    BinaryExpression(Operator.UNION, this, other)
}
