package com.github.hubbards.xpath

import com.github.hubbards.xpath.Operator.MINUS

class UnaryExpression internal constructor(
    val expression: Expression
) : OperatorExpression() {
  // TODO add parentheses to nested binary expressions
  override fun helper(transform: Expression.() -> String) =
      "$MINUS ${expression.transform()}"
}
