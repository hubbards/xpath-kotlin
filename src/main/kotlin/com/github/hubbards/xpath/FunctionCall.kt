package com.github.hubbards.xpath

/**
 * A function call expression, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Function-Calls).
 *
 * TODO add missing standard functions
 * TODO use invoke convention
 */
class FunctionCall(val name: String,
                   vararg val arguments: Expression) : Expression() {
  private fun helper(transform: (Expression) -> String) =
      name + arguments.joinToString(
          separator = ",",
          prefix = "(",
          postfix = ")",
          transform = transform
      )

  override fun abbreviated() =
      helper(Expression::abbreviated)

  override fun unabbreviated() =
      helper(Expression::unabbreviated)
}

/**
 * Returns the number of nodes in [argument] node-set.
 */
fun count(argument: Expression) =
    FunctionCall("count", argument)

/**
 * Returns true if [argument] is false, and false otherwise.
 */
fun not(argument: Expression) = FunctionCall("not", argument)

/**
 * Returns the local part of the expanded-name of the first node in [argument]
 * node-set.
 */
fun localName(argument: Expression) =
    FunctionCall("local-name", argument)

/**
 * Returns the local part of the expanded-name of the context node.
 */
val localName = FunctionCall("local-name")
