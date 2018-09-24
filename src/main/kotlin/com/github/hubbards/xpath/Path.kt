package com.github.hubbards.xpath

import com.github.hubbards.xpath.Axis.*

/**
 * A location path, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#location-paths).
 */
sealed class Path : Expression() {
  protected val steps = mutableListOf<Step>()

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
}

/**
 * Builds a relative location path.
 */
class RelativePath(init: Path.() -> Unit) : Path() {
  init {
    apply(init)
  }

  override fun abbreviated(): String {
    val first =
        if (steps.isNotEmpty())
          steps.first().abbreviated() + (if (steps.size > 1) "/" else "")
        else
          ""
    val last =
        if (steps.size > 1)
          (if (steps.size > 2) "/" else "") + steps.last().abbreviated()
        else
          ""

    return steps.drop(1).dropLast(1).joinToString(
        separator = "/",
        prefix = first,
        postfix = last,
        transform = ::helper
    )
  }

  override fun unabbreviated() =
      steps.joinToString(
          separator = "/",
          transform = Step::unabbreviated
      )
}

/**
 * Builds an absolute location path.
 */
class AbsolutePath(init: Path.() -> Unit) : Path() {
  init {
    apply(init)
  }

  override fun abbreviated(): String {
    val last =
        if (steps.isNotEmpty())
          (if (steps.size > 1) "/" else "") + steps.last().abbreviated()
        else
          ""

    return steps.dropLast(1).joinToString(
        separator = "/",
        prefix = "/",
        postfix = last,
        transform = ::helper
    )
  }

  override fun unabbreviated() =
      steps.joinToString(
          separator = "/",
          prefix = "/",
          transform = Step::unabbreviated
      )
}

private fun helper(step: Step) =
    if (step.axis == DESCENDANT_OR_SELF &&
        step.node == NODE_TEST &&
        step.predicates.isEmpty())
      ""
    else
      step.abbreviated()
