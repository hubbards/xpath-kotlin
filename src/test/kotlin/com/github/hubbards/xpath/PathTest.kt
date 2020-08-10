package com.github.hubbards.xpath

import com.github.hubbards.xpath.Expression.*
import com.github.hubbards.xpath.Expression.Path.Companion.absolute
import com.github.hubbards.xpath.Expression.Path.Companion.relative
import org.junit.Test
import kotlin.test.assertEquals

class PathTest {
  @Test
  fun childSyntax() {
    val p = relative { child("div"); child("para") }
    assertEquals(
      expected = "child::div/child::para",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "div/para",
      actual = p.abbreviated
    )
  }

  @Test
  fun attributeSyntax() {
    val p = relative {
      child("para") {
        val l = relative { attribute("type") }
        val r = LiteralString("warning")
        predicate(l equal r)
      }
    }
    assertEquals(
      expected = "child::para[attribute::type = 'warning']",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "para[@type = 'warning']",
      actual = p.abbreviated
    )
  }

  @Test
  fun absolutePathDescendantOrSelfSyntax() {
    val p = absolute { descendantOrSelf(); child("para") }
    assertEquals(
      expected = "/descendant-or-self::node()/child::para",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "//para",
      actual = p.abbreviated
    )
  }

  @Test
  fun relativePathDescendantOrSelfSyntax() {
    val p = relative { child("div"); descendantOrSelf(); child("para") }
    assertEquals(
      expected = "child::div/descendant-or-self::node()/child::para",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "div//para",
      actual = p.abbreviated
    )
  }

  @Test
  fun selfSyntax() {
    val p = relative { self(); descendantOrSelf(); child("para") }
    assertEquals(
      expected = "self::node()/descendant-or-self::node()/child::para",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = ".//para",
      actual = p.abbreviated
    )
  }

  @Test
  fun parentSyntax() {
    val p = relative { parent(); child("title") }
    assertEquals(
      expected = "parent::node()/child::title",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "../title",
      actual = p.abbreviated
    )
  }

  @Test
  fun predicateSyntax() {
    val p = relative {
      child("para") {
        predicate(relative { attribute("type") } equal LiteralString("warning"))
        predicate(LiteralNumber(5))
      }
    }
    assertEquals(
      expected = "child::para[attribute::type = 'warning'][5]",
      actual = p.unabbreviated
    )
    assertEquals(
      expected = "para[@type = 'warning'][5]",
      actual = p.abbreviated
    )
  }
}
