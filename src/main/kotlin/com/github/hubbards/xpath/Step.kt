package com.github.hubbards.xpath

import com.github.hubbards.xpath.Axis.*

internal const val NODE_TEST = "node()"

/**
 * A location step, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Location-Steps).
 */
class Step internal constructor(
    internal val axis: Axis = CHILD,
    val node: String = NODE_TEST
) : Syntax {
  private val predicates = mutableListOf<Expression>()

  internal val hasNoPredicates: Boolean
      get() = predicates.isEmpty()

  /**
   * Add [expression] predicate to this step.
   */
  fun predicate(expression: Expression) {
    predicates += expression
  }

  override fun abbreviated() =
      when {
        // abbreviated syntax for child::
        axis == CHILD -> node
        // abbreviated syntax for attribute::
        axis == ATTRIBUTE -> "@$node"
        // abbreviated syntax for self::node()
        axis == SELF && node == NODE_TEST && hasNoPredicates -> "."
        // abbreviated syntax for parent::node()
        axis == PARENT && node == NODE_TEST && hasNoPredicates -> ".."
        // unabbreviated syntax
        else -> "$axis::$node"
      } + predicates.joinToString("") { "[${it.abbreviated()}]" }

  override fun unabbreviated() =
      "$axis::$node" + predicates.joinToString("") { "[${it.unabbreviated()}]" }

  override fun toString() =
      abbreviated()
}
