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
        Expression.Path.Builder builder = new Expression.Path.Builder();
        builder.descendant("img");
        builder.parent();
        Expression expression = Expression.FunctionCall.Companion.localName(builder.absolute());
        Assert.assertEquals("local-name(/descendant::img/parent::node())", expression.getUnabbreviated());
        Assert.assertEquals("local-name(/descendant::img/..)", expression.getAbbreviated());
    }

    @Test
    public void binaryExpressionSyntax() {
        Expression.Path.Builder builder = new Expression.Path.Builder();
        builder.descendantOrSelf();
        builder.child("ol");
        builder.child("li");
        Expression right = Expression.FunctionCall.Companion.count(builder.absolute());
        Expression left = new Expression.LiteralNumber(3);
        Expression expression = new Expression.BinaryExpression(Operator.GREATER_THAN, left, right);
        Assert.assertEquals(
                "3 > count(/descendant-or-self::node()/child::ol/child::li)",
                expression.getUnabbreviated()
        );
        Assert.assertEquals("3 > count(//ol/li)", expression.getAbbreviated());
    }
}
