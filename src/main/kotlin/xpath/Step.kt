package xpath

import xpath.Axis.*

const val NODE_TEST = "node()"

/**
 * A location step, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Location-Steps).
 */
class Step(val axis: Axis, val node: String = NODE_TEST) : Syntax {
  val predicates = mutableListOf<Syntax>()

  override fun abbreviated() =
      when {
      // abbreviated syntax for child::
        axis == CHILD -> node
      // abbreviated syntax for attribute::
        axis == ATTRIBUTE -> "@$node"
      // abbreviated syntax for self::node()
        axis == SELF && node == NODE_TEST && predicates.isEmpty() -> "."
      // abbreviated syntax for parent::node()
        axis == PARENT && node == NODE_TEST && predicates.isEmpty() -> ".."
      // unabbreviated syntax
        else -> "$axis::$node"
      } + predicates.joinToString("") { "[${it.abbreviated()}]" }

  override fun unabbreviated() =
      "$axis::$node" + predicates.joinToString("") { "[${it.unabbreviated()}]" }

  override fun toString() = abbreviated()
}
