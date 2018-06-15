package xpath.extension

import org.xml.sax.InputSource
import xpath.Expression
import javax.xml.namespace.QName
import javax.xml.xpath.XPath

fun XPath.compile(expression: Expression) =
    this.compile(expression.toString())

fun XPath.evaluate(expression: Expression, source: InputSource) =
    this.evaluate(expression.toString(), source)

fun XPath.evaluate(expression: Expression,
                   source: InputSource,
                   returnType: QName) =
    this.evaluate(expression.toString(), source, returnType)

fun XPath.evaluate(expression: Expression, item: Any) =
    this.evaluate(expression.toString(), item)

fun XPath.evaluate(expression: Expression, item: Any, returnType: QName) =
    this.evaluate(expression.toString(), item, returnType)
