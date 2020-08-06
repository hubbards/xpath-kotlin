package com.github.hubbards.xpath

// TODO document
enum class Axis {
  // TODO add missing axes
  SELF,
  CHILD,
  PARENT,
  DESCENDANT,
  ANCESTOR,
  DESCENDANT_OR_SELF,
  ANCESTOR_OR_SELF,
  ATTRIBUTE;

  override fun toString() =
      name.toLowerCase().replace('_', '-')
}
