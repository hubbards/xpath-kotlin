package com.github.hubbards.xpath

import com.github.hubbards.xpath.Operator.MINUS

class UnaryMinusExpression internal constructor(
    val expression: Expression
) : Expression() {
  // TODO refactor using StringBuild buildString pattern
  private fun helper(string: String) =
      buildString {
        append(MINUS)
        append(' ')

        if (expression is BinaryExpression) {
          append('(')
          append(string)
          append(')')
        } else {
          append(string)
        }
      }

  override fun unabbreviated() =
      helper(expression.unabbreviated())

  override fun abbreviated() =
      helper(expression.abbreviated())
}
