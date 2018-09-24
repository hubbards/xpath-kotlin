package xpath.extension

import org.junit.Before
import org.junit.Test
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import xpath.LiteralString
import xpath.absolutePath
import xpath.relativePath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import kotlin.test.assertEquals

class XPathTest {
  private val path = "./src/test/resources/movies.xml"
  private val xpath = XPathFactory.newInstance().newXPath()

  @Before
  fun resetXPath() {
    xpath.reset()
  }

  @Test
  fun evaluateInContextOfInputSource() {
    val e = absolutePath {
      child("StarMovieData")
      child("Star")
      child("Name")
    }
    val s = InputSource(path)
    val r = xpath.evaluate(e, s, XPathConstants.NODESET) as NodeList
    assertEquals(2, r.length)
    assertEquals("Carrie Fisher", r[0].textContent)
    assertEquals("Mark Hamill", r[1].textContent)
  }

  @Test
  fun evaluateInContextOfItem() {
    val e1 = absolutePath {
      child("StarMovieData")
      child("Star") {
        val l = relativePath { attribute("starID") }
        val r = LiteralString("cf")
        predicates += l equal r
      }
    }
    val s1 = InputSource(path)
    val s2 = xpath.evaluate(e1, s1, XPathConstants.NODE) as Node
    val e2 = relativePath {
      child("Address")
      child("City")
    }
    val r = xpath.evaluate(e2, s2, XPathConstants.NODESET) as NodeList
    assertEquals(2, r.length)
    assertEquals("Hollywood", r[0].textContent)
    assertEquals("Malibu", r[1].textContent)
  }
}
