package com.github.hubbards.xpath

/**
 * A [node test][specification].
 *
 * [specification]: https://www.w3.org/TR/1999/REC-xpath-19991116/#node-tests
 */
sealed class NodeTest {
  // TODO document
  data class Name(val name: String) : NodeTest() {
    override fun toString() = name
  }

  // TODO document
  object Comment : NodeTest() {
    override fun toString() = "comment()"
  }

  // TODO document
  object Text : NodeTest() {
    override fun toString() = "text()"
  }

  // TODO document
  object Node : NodeTest() {
    override fun toString() = "node()"
  }

  // TODO document
  data class ProcessingInstruction(val argument: LiteralString?) : NodeTest() {
    override fun toString() = buildString {
      append("processing-instruction")
      append('(')
      if (argument != null) {
        append(argument.unabbreviated)
      }
      append(')')
    }
  }

  // TODO add missing node tests
}
