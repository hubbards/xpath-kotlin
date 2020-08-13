package com.github.hubbards.xpath

/**
 * A [location step][specification].
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Location-Steps
 */
data class Step(
  val axis: Axis = Axis.CHILD,
  val node: NodeTest = NodeTest.Node,
  val predicates: List<Expression> = emptyList()
) : Syntax {
  override val unabbreviated = buildString {
    append(axis)
    append(':')
    append(':')
    append(node)
    for (predicate in predicates) {
      brackets(predicate.unabbreviated)
    }
  }

  override val abbreviated = buildString {
    when {
      // abbreviated syntax for child::
      axis == Axis.CHILD ->
        append(node)
      // abbreviated syntax for attribute::
      axis == Axis.ATTRIBUTE -> {
        append('@')
        append(node)
      }
      // abbreviated syntax for self::node()
      axis == Axis.SELF && node == NodeTest.Node && predicates.isEmpty() ->
        append('.')
      // abbreviated syntax for parent::node()
      axis == Axis.PARENT && node == NodeTest.Node && predicates.isEmpty() -> {
        append('.')
        append('.')
      }
      // unabbreviated syntax
      else -> {
        append(axis)
        append(':')
        append(':')
        append(node)
      }
    }
    for (predicate in predicates) {
      brackets(predicate.abbreviated)
    }
  }

  /**
   * A location step builder.
   */
  class Builder(val axis: Axis, val node: NodeTest) {
    private val predicates: MutableList<Expression> = mutableListOf()

    /**
     * Add [expression] predicate to this builder
     */
    fun predicate(expression: Expression) =
      predicates.add(expression)

    /**
     * Build a location step
     */
    fun build(): Step =
      Step(axis = axis, node = node, predicates = predicates.toList())
  }

  /**
   * Factory methods for constructing location steps.
   */
  companion object Factory {
    /**
     * Construct a location step. This is useful for constructing a location
     * step from Java.
     */
    fun step(axis: Axis, node: NodeTest, vararg predicates: Expression): Step =
      Step(axis, node, predicates.asList())
  }
}
