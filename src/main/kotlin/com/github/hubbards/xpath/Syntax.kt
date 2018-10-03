package com.github.hubbards.xpath

/**
 * Represents a term in a syntactic category of the XPath grammar.
 */
interface Syntax {
  fun unabbreviated(): String

  fun abbreviated(): String
}
