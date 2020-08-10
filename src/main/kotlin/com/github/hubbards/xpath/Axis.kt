package com.github.hubbards.xpath

// TODO document
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
