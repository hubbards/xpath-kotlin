package com.github.hubbards.xpath

import com.github.hubbards.xpath.Operator.MINUS

sealed class OperatorExpression : Expression() {
  protected abstract fun layout(transform: (Expression) -> String): String

  override fun unabbreviated() =
      layout(helper(Expression::unabbreviated))

  override fun abbreviated() =
      layout(helper(Expression::abbreviated))
}

private fun helper(transform: (Expression) -> String): (Expression) -> String =
    { expression ->
      val s = transform(expression)
      if (expression is BinaryExpression) "($s)" else s
    }

class UnaryMinusExpression internal constructor(
    val expression: Expression
) : OperatorExpression() {
  override fun layout(transform: (Expression) -> String) =
      "$MINUS ${transform(expression)}"
}

class BinaryExpression internal constructor(
    internal val operator: Operator,
    val left: Expression,
    val right: Expression
) : OperatorExpression() {
  override fun layout(transform: (Expression) -> String) =
      "${transform(left)} $operator ${transform(right)}"
}
