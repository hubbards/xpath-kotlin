package com.github.hubbards.xpath

class BinaryExpression internal constructor(
    val operator: Operator,
    val left: Expression,
    val right: Expression
) : OperatorExpression() {
  override fun helper(transform: Expression.() -> String) =
      "${left.transform()} $operator ${right.transform()}"
}
