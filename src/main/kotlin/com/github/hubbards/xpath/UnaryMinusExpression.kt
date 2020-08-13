package com.github.hubbards.xpath

/**
 * A unary minus XPath expression.
 */
data class UnaryMinusExpression(val expression: Expression) : Expression() {
  override val unabbreviated =
    buildString {
      append(Operator.MINUS)
      append(' ')
      if (expression is BinaryExpression) {
        parenthesize(expression.unabbreviated)
      } else {
        append(expression.unabbreviated)
      }
    }

  override val abbreviated =
    buildString {
      append(Operator.MINUS)
      append(' ')
      if (expression is BinaryExpression) {
        parenthesize(expression.abbreviated)
      } else {
        append(expression.abbreviated)
      }
    }
}
