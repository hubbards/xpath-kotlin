package com.github.hubbards.xpath

/**
 * A location step, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Location-Steps).
 */
data class Step(val axis: Axis = Axis.CHILD,
                val node: String = NODE,
                val predicates: List<Expression> = emptyList()) : Syntax {
  override val unabbreviated =
      buildString {
        append(axis)
        append(':')
        append(':')
        append(node)
        for (predicate in predicates) {
          brackets(predicate.unabbreviated)
        }
      }

  override val abbreviated =
      buildString {
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
          axis == Axis.SELF && node == NODE && predicates.isEmpty() ->
            append('.')
          // abbreviated syntax for parent::node()
          axis == Axis.PARENT && node == NODE && predicates.isEmpty() -> {
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

  // TODO document
  class Builder(val axis: Axis = Axis.CHILD, val node: String = NODE) {
    private val predicates: MutableList<Expression> = mutableListOf()

    /**
     * Add [expression] predicate to this builder
     */
    fun predicate(expression: Expression) =
        predicates.add(expression)

    // TODO document
    fun build(): Step =
        Step(axis = axis,
                                                          node = node,
                                                          predicates = predicates.toList())
  }

  companion object {
    // TODO replace with NodeTest type
    const val NODE: String = "node()"

    private fun StringBuilder.brackets(string: String) {
      append('[')
      append(string)
      append(']')
    }
  }
}
