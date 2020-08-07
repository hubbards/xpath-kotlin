package com.github.hubbards.xpath;

import org.junit.Assert;
import org.junit.Test;

public class PathJavaTest {
    @Test
    public void predicateSyntax() {
        Expression expression = new Expression.Path.Relative(new Step(
                Axis.CHILD,
                "para",
                new Expression.BinaryExpression(
                        Operator.EQUAL,
                        new Expression.Path.Relative(new Step(Axis.ATTRIBUTE, "type")),
                        new Expression.LiteralString("warning")
                ),
                new Expression.LiteralNumber(5)
        ));
        Assert.assertEquals("child::para[attribute::type = 'warning'][5]", expression.getUnabbreviated());
        Assert.assertEquals("para[@type = 'warning'][5]", expression.getAbbreviated());
    }
}
