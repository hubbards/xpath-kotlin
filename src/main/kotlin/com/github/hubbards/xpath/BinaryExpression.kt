package com.github.hubbards.xpath

/**
 * A binary XPath expression.
 */
data class BinaryExpression(
  val operator: Operator,
  val left: Expression,
  val right: Expression
) : Expression() {
  // helper method
  private fun layout(linearize: (Syntax) -> String) = buildString {
    if (left is BinaryExpression && left.operator < operator)
      parenthesize(linearize(left))
    else
      append(linearize(left))
    append(' ')
    append(operator)
    append(' ')
    if (right is BinaryExpression && right.operator <= operator)
      parenthesize(linearize(right))
    else
      append(linearize(right))
  }

  override val unabbreviated = layout(Syntax::unabbreviated)

  override val abbreviated = layout(Syntax::abbreviated)
}
