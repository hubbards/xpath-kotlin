package com.github.hubbards.xpath

import kotlin.test.assertEquals
import org.junit.Test

class ExpressionTest {
  @Test
  fun literalNumberSyntax() {
    val e = LiteralNumber(3)
    assertEquals(expected = "3", actual = e.unabbreviated())
    assertEquals(expected = "3", actual = e.abbreviated())
  }

  @Test
  fun literalStringSyntax() {
    val e = LiteralString("dog")
    assertEquals(expected = "'dog'", actual = e.unabbreviated())
    assertEquals(expected = "'dog'", actual = e.abbreviated())
  }

  @Test
  fun functionCallSyntax() {
    val e = localName(AbsolutePath { descendant("img"); parent("node()") })
    assertEquals(
        expected = "local-name(/descendant::img/parent::node())",
        actual = e.unabbreviated()
    )
    assertEquals(
        expected = "local-name(/descendant::img/..)",
        actual = e.abbreviated()
    )
  }

  @Test
  fun binaryExpressionSyntax() {
    val e = run {
      val l = LiteralNumber(3)
      val r = AbsolutePath {
        descendantOrSelf()
        child("ol")
        child("li")
      }
      l greaterThan count(r)
    }
    assertEquals(
        expected = "3 > count(/descendant-or-self::node()/child::ol/child::li)",
        actual = e.unabbreviated()
    )
    assertEquals(
        expected = "3 > count(//ol/li)",
        actual = e.abbreviated()
    )
  }

  @Test
  fun rightNestedBinaryExpressionSyntax() {
    val e = run {
      val one = LiteralNumber(1)
      val two = LiteralNumber(2)
      val three = LiteralNumber(3)
      three * (two - one)
    }
    assertEquals(expected = "3 * (2 - 1)", actual = e.unabbreviated())
    assertEquals(expected = e.unabbreviated(), actual = e.abbreviated())
  }

  @Test
  fun leftNestedBinaryExpressionSyntax() {
    val e = run {
      val one = LiteralNumber(1)
      val two = LiteralNumber(2)
      val three = LiteralNumber(3)
      (two - one) * three
    }
    assertEquals(expected = "(2 - 1) * 3", actual = e.unabbreviated())
    assertEquals(expected = e.unabbreviated(), actual = e.abbreviated())
  }
}
