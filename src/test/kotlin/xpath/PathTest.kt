package xpath

import kotlin.test.assertEquals
import org.junit.Test

class PathTest {
  @Test
  fun childSyntax() {
    val p = RelativePath { child("div"); child("para") }
    assertEquals(
        expected = "child::div/child::para",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = "div/para",
        actual = p.abbreviated()
    )
  }

  @Test
  fun attributeSyntax() {
    val p = RelativePath {
      child("para") {
        val l = RelativePath { attribute("type") }
        val r = LiteralString("warning")
        predicates += l equal r
      }
    }
    assertEquals(
        expected = "child::para[attribute::type = 'warning']",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = "para[@type = 'warning']",
        actual = p.abbreviated()
    )
  }

  @Test
  fun absolutePathDescendantOrSelfSyntax() {
    val p = AbsolutePath { descendantOrSelf(); child("para") }
    assertEquals(
        expected = "/descendant-or-self::node()/child::para",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = "//para",
        actual = p.abbreviated()
    )
  }

  @Test
  fun relativePathDescendantOrSelfSyntax() {
    val p = RelativePath { child("div"); descendantOrSelf(); child("para") }
    assertEquals(
        expected = "child::div/descendant-or-self::node()/child::para",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = "div//para",
        actual = p.abbreviated()
    )
  }

  @Test
  fun selfSyntax() {
    val p = RelativePath { self(); descendantOrSelf(); child("para") }
    assertEquals(
        expected = "self::node()/descendant-or-self::node()/child::para",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = ".//para",
        actual = p.abbreviated()
    )
  }

  @Test
  fun parentSyntax() {
    val p = RelativePath { parent(); child("title") }
    assertEquals(
        expected = "parent::node()/child::title",
        actual = p.unabbreviated()
    )
    assertEquals(
        expected = "../title",
        actual = p.abbreviated()
    )
  }
}
