package com.github.hubbards.xpath

/**
 * An axis, see
 * [specification](https://www.w3.org/TR/1999/REC-xpath-19991116/#axes).
 */
enum class Axis {
  SELF,
  CHILD,
  PARENT,
  DESCENDANT,
  ANCESTOR,
  DESCENDANT_OR_SELF,
  ANCESTOR_OR_SELF,
  FOLLOWING,
  PRECEDING,
  FOLLOWING_SIBLING,
  PRECEDING_SIBLING,
  ATTRIBUTE,
  NAMESPACE;

  override fun toString() =
    name.toLowerCase().replace('_', '-')
}
