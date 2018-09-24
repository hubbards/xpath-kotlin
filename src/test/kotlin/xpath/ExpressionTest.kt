package xpath

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
    val e = localName(absolutePath { descendant("img"); parent("node()") })
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
    val l = LiteralNumber(3)
    val r = absolutePath {
      descendantOrSelf()
      child("ol")
      child("li")
    }
    val e = l greaterThan count(r)
    assertEquals(
        expected = "3 > count(/descendant-or-self::node()/child::ol/child::li)",
        actual = e.unabbreviated()
    )
    assertEquals(
        expected = "3 > count(//ol/li)",
        actual = e.abbreviated()
    )
  }
}
