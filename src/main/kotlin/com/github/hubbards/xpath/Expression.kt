package com.github.hubbards.xpath

/**
 * An XPath [expression][specification].
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Expressions
 */
sealed class Expression : Syntax {
  infix fun equal(other: Expression): Expression =
    BinaryExpression(Operator.EQUAL, this, other)

  infix fun notEqual(other: Expression) =
    BinaryExpression(Operator.NOT_EQUAL, this, other)

  infix fun greaterThan(other: Expression) =
    BinaryExpression(Operator.GREATER_THAN, this, other)

  infix fun lessThan(other: Expression) =
    BinaryExpression(Operator.LESS_THAN, this, other)

  infix fun greaterThanOrEqualTo(other: Expression) =
    BinaryExpression(Operator.GREATER_THAN_OR_EQUAL_TO, this, other)

  infix fun lessThanOrEqualTo(other: Expression) =
    BinaryExpression(Operator.LESS_THAN_OR_EQUAL_TO, this, other)

  infix fun and(other: Expression) =
    BinaryExpression(Operator.AND, this, other)

  infix fun or(other: Expression) =
    BinaryExpression(Operator.OR, this, other)

  operator fun unaryMinus(): Expression =
    UnaryMinusExpression(this)

  operator fun plus(other: Expression): Expression =
    BinaryExpression(Operator.PLUS, this, other)

  operator fun minus(other: Expression) =
    BinaryExpression(Operator.MINUS, this, other)

  operator fun times(other: Expression) =
    BinaryExpression(Operator.TIMES, this, other)

  operator fun div(other: Expression) =
    BinaryExpression(Operator.DIVIDE, this, other)

  operator fun rem(other: Expression) =
    BinaryExpression(Operator.MODULO, this, other)

  infix fun union(other: Expression) =
    BinaryExpression(Operator.UNION, this, other)

  /**
   * A [number][specification] literal.
   *
   * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#numbers
   */
  data class LiteralNumber(val number: Number) : Expression() {
    override val unabbreviated =
      number.toString()
  }

  /**
   * A [string][specification] literal.
   *
   * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#strings
   */
  data class LiteralString(val string: String) : Expression() {
    override val unabbreviated = buildString {
      append('\'')
      append(string)
      append('\'')
    }
  }

  /**
   * A unary minus XPath expression.
   */
  data class UnaryMinusExpression(val expression: Expression) : Expression() {
    override val unabbreviated = buildString {
      append(Operator.MINUS)
      append(' ')
      if (expression is BinaryExpression)
        parenthesize(expression.unabbreviated)
      else
        append(expression.unabbreviated)
    }

    override val abbreviated = buildString {
      append(Operator.MINUS)
      append(' ')
      if (expression is BinaryExpression)
        parenthesize(expression.abbreviated)
      else
        append(expression.abbreviated)
    }
  }

  /**
   * A binary XPath expression.
   */
  data class BinaryExpression(
    val operator: Operator,
    val left: Expression,
    val right: Expression
  ) : Expression() {
    override val unabbreviated: String = buildString {
      if (left is BinaryExpression && left.operator < operator)
        parenthesize(left.unabbreviated)
      else
        append(left.unabbreviated)
      append(' ')
      append(operator)
      append(' ')
      if (right is BinaryExpression && right.operator <= operator)
        parenthesize(right.unabbreviated)
      else
        append(right.unabbreviated)
    }

    override val abbreviated: String = buildString {
      if (left is BinaryExpression && left.operator < operator)
        parenthesize(left.abbreviated)
      else
        append(left.abbreviated)
      append(' ')
      append(operator)
      append(' ')
      if (right is BinaryExpression && right.operator <= operator)
        parenthesize(right.abbreviated)
      else
        append(right.abbreviated)
    }

    companion object {
      // TODO pick up here
    }
  }

  /**
   * A [function call][specification] XPath expression.
   *
   * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Function-Calls
   */
  data class FunctionCall(
    val name: String,
    val arguments: List<Expression> = emptyList()
  ) : Expression() {
    override val unabbreviated =
      name + arguments.joinToString(
        prefix = "(",
        postfix = ")",
        transform = Expression::unabbreviated
      )

    override val abbreviated =
      name + arguments.joinToString(
        prefix = "(",
        postfix = ")",
        transform = Expression::abbreviated
      )

    // TODO add builder using invoke convention

    /**
     * Factory methods for constructing [core function library][specification]
     * function calls.
     *
     * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#corelib
     */
    companion object Factory {
      /**
       * Call the [last][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-last
       */
      fun last(): FunctionCall =
        FunctionCall("last")

      /**
       * Call the [position][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-position
       */
      fun position(): FunctionCall =
        FunctionCall("position")

      /**
       * Call the [count][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-count
       */
      fun count(argument: Expression): FunctionCall =
        FunctionCall("count", listOf(argument))

      // TODO add id function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-id

      /**
       * Call the [local-name][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-local-name
       */
      fun localName(argument: Expression?): FunctionCall =
        FunctionCall("local-name", listOfNotNull(argument))

      /**
       * Call the [namespace-uri][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-namespace-uri
       */
      fun namespaceUri(argument: Expression?): FunctionCall =
        FunctionCall("namespace-uri", listOfNotNull(argument))

      /**
       * Call the [name][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-name
       */
      fun name(argument: Expression?): FunctionCall =
        FunctionCall("name", listOfNotNull(argument))

      /**
       * Call the [string][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-string
       */
      fun string(argument: Expression?): FunctionCall =
        FunctionCall("string", listOfNotNull(argument))

      // TODO add concat function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-concat

      // TODO add starts-with function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-starts-with

      // TODO add contains function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-contains

      // TODO add substring-before function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-substring-before

      // TODO add substring-after function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-substring-after

      // TODO add substring function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-substring

      // TODO add string-length function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-string-length

      // TODO add normalize-space function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-normalize-space

      // TODO add translate function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-translate

      /**
       * Call the [boolean][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-boolean
       */
      fun boolean(argument: Expression?): FunctionCall =
        FunctionCall("boolean", listOfNotNull(argument))

      /**
       * Call the [not][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-not
       */
      fun not(argument: Expression): FunctionCall =
        FunctionCall("not", listOf(argument))

      /**
       * Call the [true][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-true
       */
      fun _true(): FunctionCall =
        FunctionCall("true")

      /**
       * Call the [false][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-false
       */
      fun _false(): FunctionCall =
        FunctionCall("false")

      // TODO add lang function, see
      //  https://www.w3.org/TR/1999/REC-xpath-19991116/#function-lang

      /**
       * Call the [number][specification] function.
       *
       * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#function-number
       */
      fun number(argument: Expression?): FunctionCall =
        FunctionCall("number", listOfNotNull(argument))

      // TODO add other number functions
    }
  }

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

    companion object Factory {
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
       * An absolute location path. This is useful for constructing an absolute
       * location path from Java.
       */
      fun absolute(vararg steps: Step): Absolute =
        Absolute(steps.asList())

      /**
       * An absolute location path.
       */
      fun absolute(init: Builder.() -> Unit): Absolute =
        Builder().apply(init).absolute()

      /**
       * A relative location path. This is useful for constructing a relative
       * location path from Java.
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

  companion object {
    private fun StringBuilder.parenthesize(string: String) {
      append('(')
      append(string)
      append(')')
    }
  }
}
