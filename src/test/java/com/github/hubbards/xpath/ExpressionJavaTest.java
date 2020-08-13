package com.github.hubbards.xpath;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionJavaTest {
    @Test
    public void literalNumberSyntax() {
        Expression expression = new LiteralNumber(3);
        Assert.assertEquals("3", expression.getUnabbreviated());
        Assert.assertEquals("3", expression.getAbbreviated());
    }

    @Test
    public void literalStringSyntax() {
        Expression expression = new LiteralString("dog");
        Assert.assertEquals("'dog'", expression.getUnabbreviated());
        Assert.assertEquals("'dog'", expression.getAbbreviated());
    }

    @Test
    public void functionCallSyntax() {
        Expression expression = FunctionCall.Factory.localName(
                Path.Factory.absolute(
                        Step.Factory.step(Axis.DESCENDANT, new NodeTest.Name("img")),
                        Step.Factory.step(Axis.PARENT, NodeTest.Node.INSTANCE)
                )
        );
        Assert.assertEquals("local-name(/descendant::img/parent::node())", expression.getUnabbreviated());
        Assert.assertEquals("local-name(/descendant::img/..)", expression.getAbbreviated());
    }

    @Test
    public void binaryExpressionSyntax() {
        Expression expression = new BinaryExpression(
                Operator.GREATER_THAN,
                new LiteralNumber(3),
                FunctionCall.Factory.count(Path.Factory.absolute(
                        Step.Factory.step(Axis.DESCENDANT_OR_SELF, NodeTest.Node.INSTANCE),
                        Step.Factory.step(Axis.CHILD, new NodeTest.Name("ol")),
                        Step.Factory.step(Axis.CHILD, new NodeTest.Name("li"))
                ))
        );
        Assert.assertEquals(
                "3 > count(/descendant-or-self::node()/child::ol/child::li)",
                expression.getUnabbreviated()
        );
        Assert.assertEquals(
                "3 > count(//ol/li)",
                expression.getAbbreviated()
        );
    }
}
