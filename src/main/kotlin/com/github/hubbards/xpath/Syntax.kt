package com.github.hubbards.xpath

/**
 * Represents a term in a syntactic category of the XPath grammar.
 */
interface Syntax {
  /**
   * Return a representation of this term using unabbreviated syntax.
   */
  fun unabbreviated(): String

  /**
   * Return a representation of this term using abbreviated syntax.
   */
  fun abbreviated(): String
}
