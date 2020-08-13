package com.github.hubbards.xpath;

import org.junit.Assert;
import org.junit.Test;

public class PathJavaTest {
    @Test
    public void predicateSyntax() {
        Expression expression = Path.Factory.relative(Step.Factory.step(
                Axis.CHILD,
                new NodeTest.Name("para"),
                new BinaryExpression(
                        Operator.EQUAL,
                        Path.Factory.relative(Step.Factory.step(Axis.ATTRIBUTE, new NodeTest.Name("type"))),
                        new LiteralString("warning")
                ),
                new LiteralNumber(5)
        ));
        Assert.assertEquals("child::para[attribute::type = 'warning'][5]", expression.getUnabbreviated());
        Assert.assertEquals("para[@type = 'warning'][5]", expression.getAbbreviated());
    }
}
