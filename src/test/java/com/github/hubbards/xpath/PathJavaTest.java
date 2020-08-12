package com.github.hubbards.xpath;

import org.junit.Assert;
import org.junit.Test;

public class PathJavaTest {
    @Test
    public void predicateSyntax() {
        Expression expression = Expression.Path.Factory.relative(Step.Factory.step(
                Axis.CHILD,
                new NodeTest.Name("para"),
                new Expression.BinaryExpression(
                        Operator.EQUAL,
                        Expression.Path.Factory.relative(Step.Factory.step(Axis.ATTRIBUTE, new NodeTest.Name("type"))),
                        new Expression.LiteralString("warning")
                ),
                new Expression.LiteralNumber(5)
        ));
        Assert.assertEquals("child::para[attribute::type = 'warning'][5]", expression.getUnabbreviated());
        Assert.assertEquals("para[@type = 'warning'][5]", expression.getAbbreviated());
    }
}
