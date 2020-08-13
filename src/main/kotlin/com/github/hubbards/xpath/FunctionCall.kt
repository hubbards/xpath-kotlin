package com.github.hubbards.xpath

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
   * Factory methods for constructing function calls, including calls to some
   * [core library][specification] functions.
   *
   * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#corelib
   */
  companion object Factory {
    /**
     * Construct a function call. This is useful for constructing a function
     * call from Java.
     */
    fun functionCall(name: String, vararg arguments: Expression): FunctionCall =
      FunctionCall(name, arguments.asList())

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
