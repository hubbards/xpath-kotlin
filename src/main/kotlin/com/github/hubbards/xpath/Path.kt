package com.github.hubbards.xpath

/**
 * A [location path][specification] XPath expression.
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#location-paths
 */
sealed class Path : Expression() {
  abstract val steps: List<Step>

  /**
   * An absolute location path.
   */
  data class Absolute(override val steps: List<Step>) : Path() {
    override val unabbreviated =
      steps.joinToString(
        separator = "/",
        prefix = "/",
        transform = Step::unabbreviated
      )

    override val abbreviated = buildString {
      append('/')
      // interior steps
      for (step in steps.dropLast(1)) {
        interior(step)
        append('/')
      }
      // last step
      if (steps.isNotEmpty()) {
        append(steps.last().abbreviated)
      }
    }
  }

  /**
   * A relative location path.
   */
  data class Relative(override val steps: List<Step>) : Path() {
    init {
      require(steps.isNotEmpty()) {
        "relative path must contain at least one step"
      }
    }

    override val unabbreviated =
      steps.joinToString(
        separator = "/",
        transform = Step::unabbreviated
      )

    override val abbreviated = buildString {
      // first step
      append(steps.first().abbreviated)
      // interior steps
      for (step in steps.drop(1).dropLast(1)) {
        append('/')
        interior(step)
      }
      // last step
      if (steps.size > 1) {
        append('/')
        append(steps.last().abbreviated)
      }
    }
  }

  /**
   * A location path builder.
   */
  class Builder {
    private val steps: MutableList<Step> = mutableListOf()

    private fun doInit(builder: Step.Builder, init: Step.Builder.() -> Unit) {
      steps += builder.apply(init).build()
    }

    /**
     * Build an absolute location path
     */
    fun absolute(): Absolute =
      Absolute(steps.toList())

    /**
     * Build a relative location path
     */
    fun relative(): Relative =
      Relative(steps.toList())

    /**
     * Add self axis step to this path
     */
    fun self(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.SELF, node), init)

    /**
     * Add self axis step to this path
     */
    fun self(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.SELF, NodeTest.Name(name)), init)

    /**
     * Add child axis step to this path
     */
    fun child(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.CHILD, node), init)

    /**
     * Add child axis step to this path
     */
    fun child(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.CHILD, NodeTest.Name(name)), init)

    /**
     * Add parent axis step to this path
     */
    fun parent(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PARENT, node), init)

    /**
     * Add parent axis step to this path
     */
    fun parent(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PARENT, NodeTest.Name(name)), init)

    /**
     * Add descendant axis step to this path
     */
    fun descendant(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.DESCENDANT, node), init)

    /**
     * Add descendant axis step to this path
     */
    fun descendant(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.DESCENDANT, NodeTest.Name(name)), init)

    /**
     * Add ancestor axis step to this path
     */
    fun ancestor(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ANCESTOR, node), init)

    /**
     * Add ancestor axis step to this path
     */
    fun ancestor(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ANCESTOR, NodeTest.Name(name)), init)

    /**
     * Add descendant-or-self axis step to this path
     */
    fun descendantOrSelf(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.DESCENDANT_OR_SELF, node), init)

    /**
     * Add descendant-or-self axis step to this path
     */
    fun descendantOrSelf(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.DESCENDANT_OR_SELF, NodeTest.Name(name)), init)

    /**
     * Add ancestor-or-self axis step to this path
     */
    fun ancestorOrSelf(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ANCESTOR_OR_SELF, node), init)

    /**
     * Add ancestor-or-self axis step to this path
     */
    fun ancestorOrSelf(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ANCESTOR_OR_SELF, NodeTest.Name(name)), init)

    /**
     * Add following axis step to this path
     */
    fun following(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.FOLLOWING, node), init)

    /**
     * Add following axis step to this path
     */
    fun following(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.FOLLOWING, NodeTest.Name(name)), init)

    /**
     * Add preceding axis step to this path
     */
    fun preceding(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PRECEDING, node), init)

    /**
     * Add preceding axis step to this path
     */
    fun preceding(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PRECEDING, NodeTest.Name(name)), init)

    /**
     * Add following-sibling axis step to this path
     */
    fun followingSibling(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.FOLLOWING_SIBLING, node), init)

    /**
     * Add following-sibling axis step to this path
     */
    fun followingSibling(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.FOLLOWING_SIBLING, NodeTest.Name(name)), init)

    /**
     * Add preceding-sibling axis step to this path
     */
    fun precedingSibling(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PRECEDING_SIBLING, node), init)

    /**
     * Add preceding-sibling axis step to this path
     */
    fun precedingSibling(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.PRECEDING_SIBLING, NodeTest.Name(name)), init)

    /**
     * Add attribute axis step to this path
     */
    fun attribute(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ATTRIBUTE, node), init)

    /**
     * Add attribute axis step to this path
     */
    fun attribute(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.ATTRIBUTE, NodeTest.Name(name)), init)

    /**
     * Add namespace axis step to this path
     */
    fun namespace(
      node: NodeTest = NodeTest.Node,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.NAMESPACE, node), init)

    /**
     * Add namespace axis step to this path
     */
    fun namespace(
      name: String,
      init: Step.Builder.() -> Unit = {}
    ) =
      doInit(Step.Builder(Axis.NAMESPACE, NodeTest.Name(name)), init)
  }

  /**
   * Factory methods for constructing location paths.
   */
  companion object Factory {
    // helper method
    private fun StringBuilder.interior(step: Step) =
      if (
        step.axis == Axis.DESCENDANT_OR_SELF &&
        step.node == NodeTest.Node &&
        step.predicates.isEmpty()
      )
        this
      else
        append(step.abbreviated)

    /**
     * Construct an absolute location path. This is useful for constructing an
     * absolute location path from Java.
     */
    fun absolute(vararg steps: Step): Absolute =
      Absolute(steps.asList())

    /**
     * An absolute location path.
     */
    fun absolute(init: Builder.() -> Unit): Absolute =
      Builder().apply(init).absolute()

    /**
     * Construct a relative location path. This is useful for constructing a
     * relative location path from Java.
     */
    fun relative(vararg steps: Step): Relative =
      Relative(steps.asList())

    /**
     * A relative location path.
     */
    fun relative(init: Builder.() -> Unit): Relative =
      Builder().apply(init).relative()
  }
}
