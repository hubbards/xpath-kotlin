package com.github.hubbards.xpath

/**
 * A term in a syntactic category of the XPath grammar.
 */
interface Syntax {
  /**
   * Linear representation of this term using unabbreviated syntax.
   */
  val unabbreviated: String

  /**
   * Linear representation of this term using abbreviated syntax.
   */
  @JvmDefault
  val abbreviated: String
    get() =
      unabbreviated
}
