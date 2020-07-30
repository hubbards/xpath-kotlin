package com.github.hubbards.xpath

class BinaryExpression internal constructor(
    internal val operator: Operator,
    val left: Expression,
    val right: Expression
) : Expression() {
  private fun helper(transform: (Expression) -> String) =
      buildString {
        val l = transform(left)
        // binary operators are left associative
        // op >= op' implies x op y op' z == (x op y) op' z
        if (left is BinaryExpression && left.operator < operator) {
          append('(')
          append(l)
          append(')')
        } else {
          append(l)
        }

        append(' ')
        append(operator)
        append(' ')

        val r = transform(right)
        if (right is BinaryExpression) {
          append('(')
          append(r)
          append(')')
        } else {
          append(r)
        }
      }

  override fun unabbreviated() =
      helper(Expression::unabbreviated)

  override fun abbreviated() =
      helper(Expression::abbreviated)
}
