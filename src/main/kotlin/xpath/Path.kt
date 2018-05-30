package xpath

import xpath.Axis.*

/**
 * A location path, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#location-paths).
 *
 * TODO make subclass for absolute path
 */
class Path(val isAbsolute: Boolean) : Expression() {
  val isRelative = !isAbsolute

  private val steps = mutableListOf<Step>()

  private fun doInit(step: Step, init: Step.() -> Unit) {
    step.init()
    steps.add(step)
  }

  fun self(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(SELF, node), init)

  fun child(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(CHILD, node), init)

  fun parent(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(PARENT, node), init)

  fun descendant(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(DESCENDANT, node), init)

  fun ancestor(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(ANCESTOR, node), init)

  fun descendantOrSelf(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(DESCENDANT_OR_SELF, node), init)

  fun ancestorOrSelf(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(ANCESTOR_OR_SELF, node), init)

  fun attribute(node: String = NODE_TEST, init: Step.() -> Unit = {}) =
      doInit(Step(ATTRIBUTE, node), init)

  // abbreviated syntax for /descendant-or-self::node()/
  override fun abbreviated(): String {
    val prefix =
        if (isAbsolute)
          "/"
        else if (isRelative && steps.isNotEmpty())
          steps.first().abbreviated() + (if (steps.size > 1) "/" else "")
        else
          ""
    val postfix =
        if (isAbsolute && steps.isNotEmpty())
          (if (steps.size > 1) "/" else "") + steps.last().abbreviated()
        else if (isRelative && steps.size > 1)
          (if (steps.size > 2) "/" else "") + steps.last().abbreviated()
        else
          ""
    return steps
        .drop(if (isAbsolute) 0 else 1)
        .dropLast(1)
        .joinToString(
            separator = "/",
            prefix = prefix,
            postfix = postfix
        ) {
          if (it.axis == DESCENDANT_OR_SELF &&
              it.node == NODE_TEST &&
              it.predicates.isEmpty())
            ""
          else
            it.abbreviated()
        }
  }

  override fun unabbreviated() =
      steps.joinToString(
          separator = "/",
          prefix = if (isAbsolute) "/" else "",
          transform = Step::unabbreviated
      )
}

/**
 * Builds a relative location path.
 */
fun relativePath(init: Path.() -> Unit) = Path(false).apply(init)

/**
 * Builds an absolute location path.
 */
fun absolutePath(init: Path.() -> Unit) = Path(true).apply(init)
