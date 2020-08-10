package com.github.hubbards.xpath

import org.xml.sax.InputSource
import javax.xml.namespace.QName
import javax.xml.xpath.XPath

/**
 * Compile [expression] for later evaluation.
 *
 * @see javax.xml.xpath.XPath.compile
 */
fun XPath.compile(expression: Expression) =
  this.compile(expression.unabbreviated)

/**
 * Evaluate [expression] in context of [source].
 *
 * @see javax.xml.xpath.XPath.evaluate
 */
fun XPath.evaluate(expression: Expression, source: InputSource) =
  this.evaluate(expression.unabbreviated, source)

/**
 * Evaluate [expression] in context of [source] with [returnType].
 *
 * @see javax.xml.xpath.XPath.evaluate
 */
fun XPath.evaluate(
  expression: Expression,
  source: InputSource,
  returnType: QName
) =
  this.evaluate(expression.unabbreviated, source, returnType)

/**
 * Evaluate [expression] in context of [item].
 *
 * @see javax.xml.xpath.XPath.evaluate
 */
fun XPath.evaluate(expression: Expression, item: Any) =
  this.evaluate(expression.unabbreviated, item)

/**
 * Evaluate [expression] in context of [item] with [returnType].
 *
 * @see javax.xml.xpath.XPath.evaluate
 */
fun XPath.evaluate(expression: Expression, item: Any, returnType: QName) =
  this.evaluate(expression.unabbreviated, item, returnType)
