package com.github.hubbards.xpath

class BinaryExpression internal constructor(
    val operator: Operator,
    val left: Expression,
    val right: Expression
) : Expression() {
  private fun helper(transform: Expression.() -> String) =
      "${left.transform()} $operator ${right.transform()}"

  override fun abbreviated() =
      helper(Expression::abbreviated)

  override fun unabbreviated() =
      helper(Expression::unabbreviated)
}