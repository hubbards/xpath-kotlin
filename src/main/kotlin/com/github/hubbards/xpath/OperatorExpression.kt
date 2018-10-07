package com.github.hubbards.xpath

abstract class OperatorExpression : Expression() {
  protected abstract fun helper(transform: Expression.() -> String): String

  override fun unabbreviated() =
      helper(Expression::unabbreviated)

  override fun abbreviated() =
      helper(Expression::abbreviated)
}
