package com.github.hubbards.xpath

import com.github.hubbards.xpath.Operator.MINUS

sealed class OperatorExpression : Expression() {
  // TODO: refactor this
  protected abstract fun helper(transform: (Expression) -> String): String

  override fun unabbreviated() =
      helper {
        val s = it.unabbreviated()
        if (it is BinaryExpression) "($s)" else s
      }

  override fun abbreviated() =
      helper {
        val s = it.abbreviated()
        if (it is BinaryExpression) "($s)" else s
      }
}

class UnaryMinusExpression internal constructor(
    val expression: Expression
) : OperatorExpression() {
  override fun helper(transform: (Expression) -> String) =
      "$MINUS ${transform(expression)}"
}

class BinaryExpression internal constructor(
    internal val operator: Operator,
    val left: Expression,
    val right: Expression
) : OperatorExpression() {
  override fun helper(transform: (Expression) -> String) =
      "${transform(left)} $operator ${transform(right)}"
}
