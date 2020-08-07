package com.github.hubbards.xpath;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionJavaTest {
    @Test
    public void literalNumberSyntax() {
        Expression expression = new Expression.LiteralNumber(3);
        Assert.assertEquals("3", expression.getUnabbreviated());
        Assert.assertEquals("3", expression.getAbbreviated());
    }

    @Test
    public void literalStringSyntax() {
        Expression expression = new Expression.LiteralString("dog");
        Assert.assertEquals("'dog'", expression.getUnabbreviated());
        Assert.assertEquals("'dog'", expression.getAbbreviated());
    }

    @Test
    public void functionCallSyntax() {
        Expression expression = Expression.FunctionCall.Companion.localName(
                new Expression.Path.Absolute(
                        new Step(Axis.DESCENDANT, "img"),
                        new Step(Axis.PARENT, Step.NODE)
                )
        );
        Assert.assertEquals("local-name(/descendant::img/parent::node())", expression.getUnabbreviated());
        Assert.assertEquals("local-name(/descendant::img/..)", expression.getAbbreviated());
    }

    @Test
    public void binaryExpressionSyntax() {
        Expression expression = new Expression.BinaryExpression(
                Operator.GREATER_THAN,
                new Expression.LiteralNumber(3),
                Expression.FunctionCall.Companion.count(new Expression.Path.Absolute(
                        new Step(Axis.DESCENDANT_OR_SELF, Step.NODE),
                        new Step(Axis.CHILD, "ol"),
                        new Step(Axis.CHILD, "li")
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
