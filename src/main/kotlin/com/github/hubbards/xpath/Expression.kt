package com.github.hubbards.xpath

/**
 * An XPath expression, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Expressions).
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
   * A number literal, see
   * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#numbers).
   */
  data class LiteralNumber(val number: Number) : Expression() {
    override val unabbreviated =
        number.toString()
  }

  /**
   * A string literal, see
   * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#strings).
   */
  data class LiteralString(val string: String) : Expression() {
    override val unabbreviated =
        buildString {
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

    // TODO eliminate duplicate code
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
  data class BinaryExpression(val operator: Operator,
                              val left: Expression,
                              val right: Expression) : Expression() {
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

    // TODO eliminate duplicate code
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
  }

  /**
   * A function call expression, see
   * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#section-Function-Calls).
   */
  data class FunctionCall(val name: String,
                          val arguments: List<Expression> = emptyList()) : Expression() {
    constructor(name: String, vararg arguments: Expression) : this(name, arguments.toList())

    override val unabbreviated =
        name + arguments.joinToString(prefix = "(",
                                      postfix = ")",
                                      transform = Expression::unabbreviated)

    // TODO eliminate duplicate code
    override val abbreviated =
        name + arguments.joinToString(prefix = "(",
                                      postfix = ")",
                                      transform = Expression::abbreviated)

    // TODO add builder using invoke convention

    companion object {
      /**
       * Function call which returns the number of nodes in [argument] node-set.
       */
      fun count(argument: Expression): FunctionCall =
          FunctionCall("count", argument)

      /**
       * Function call which returns true if [argument] is false, and false otherwise.
       */
      fun not(argument: Expression): FunctionCall =
          FunctionCall("not", argument)

      /**
       * Function call which returns the local part of the expanded-name of the
       * first node in [argument] node-set.
       */
      fun localName(argument: Expression): FunctionCall =
          FunctionCall("local-name", argument)

      /**
       * Function call which returns the local part of the expanded-name of the
       * context node.
       */
      val localName: FunctionCall =
          FunctionCall("local-name")

      // TODO add missing standard functions
    }
  }

  /**
   * A location path, see
   * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#location-paths).
   */
  sealed class Path : Expression() {
    abstract val steps: List<Step>

    /**
     * An absolute location path.
     */
    data class Absolute(override val steps: List<Step>) : Path() {
      constructor(vararg steps: Step) : this(steps.toList())

      override val unabbreviated =
          steps.joinToString(separator = "/",
                             prefix = "/",
                             transform = Step::unabbreviated)

      // TODO eliminate duplicate code
      override val abbreviated = run {
        val last =
            if (steps.isNotEmpty())
              (if (steps.size > 1) "/" else "") + steps.last().abbreviated
            else
              ""
        steps.dropLast(1).joinToString(separator = "/", prefix = "/", postfix = last) {
          if (it.axis == Axis.DESCENDANT_OR_SELF && it.node == Step.NODE && it.predicates.isEmpty())
            ""
          else
            it.abbreviated
        }
      }
    }

    /**
     * A relative location path.
     */
    data class Relative(override val steps: List<Step>) : Path() {
      constructor(vararg steps: Step) : this(steps.toList())

      override val unabbreviated =
          steps.joinToString(separator = "/",
                             transform = Step::unabbreviated)

      // TODO eliminate duplicate code
      override val abbreviated = run {
        val first =
            if (steps.isNotEmpty())
              steps.first().abbreviated + (if (steps.size > 1) "/" else "")
            else
              ""
        val last =
            if (steps.size > 1)
              (if (steps.size > 2) "/" else "") + steps.last().abbreviated
            else
              ""
        steps.drop(1).dropLast(1).joinToString(separator = "/", prefix = first, postfix = last) {
          if (it.axis == Axis.DESCENDANT_OR_SELF && it.node == Step.NODE && it.predicates.isEmpty())
            ""
          else
            it.abbreviated
        }
      }
    }

    // TODO document
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
      fun self(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.SELF, node), init)

      /**
       * Add child axis step to this path
       */
      fun child(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.CHILD, node), init)

      /**
       * Add parent axis step to this path
       */
      fun parent(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.PARENT, node), init)

      /**
       * Add descendant axis step to this path
       */
      fun descendant(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.DESCENDANT, node), init)

      /**
       * Add ancestor axis step to this path
       */
      fun ancestor(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.ANCESTOR, node), init)

      /**
       * Add descendant-or-self axis step to this path
       */
      fun descendantOrSelf(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.DESCENDANT_OR_SELF, node), init)

      /**
       * Add ancestor-or-self axis step to this path
       */
      fun ancestorOrSelf(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.ANCESTOR_OR_SELF, node), init)

      /**
       * Add following axis step to this path
       */
      fun following(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.FOLLOWING, node), init)

      /**
       * Add preceding axis step to this path
       */
      fun preceding(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.PRECEDING, node), init)

      /**
       * Add following-sibling axis step to this path
       */
      fun followingSibling(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.FOLLOWING_SIBLING, node), init)

      /**
       * Add preceding-sibling axis step to this path
       */
      fun precedingSibling(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.PRECEDING_SIBLING, node), init)

      /**
       * Add attribute axis step to this path
       */
      fun attribute(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.ATTRIBUTE, node), init)

      /**
       * Add namespace axis step to this path
       */
      fun namespace(node: String = Step.NODE, init: Step.Builder.() -> Unit = {}) =
          doInit(Step.Builder(Axis.NAMESPACE, node), init)
    }

    companion object {
      /**
       * An absolute location path.
       */
      fun absolute(init: Builder.() -> Unit): Absolute =
          Builder().apply(init).absolute()

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
