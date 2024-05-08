package com.example;

import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.MapAssert.assertThatMap;

public class ExpressionParserTest {

    @Test
    public void testExpressionParser() throws Exception {
        new ExpressionParser(
                new StringReader("""
                         a=1+5;
                         b=a*3;
                         c=a+b;
                         d=a*b+c;
                         e=-d;
                        """)
        );
        ASTstart expr = ExpressionParser.start();
        ExpressionVisitor v = new ExpressionVisitor();
        assertThatMap((HashMap) expr.jjtAccept(v, null)).containsAnyOf(
                entry("a", 6),
                entry("b", 18),
                entry("c", 24),
                entry("d", 168),
                entry("e", -168)
        );
    }
}
