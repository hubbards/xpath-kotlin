package com.github.hubbards.xpath

// TODO: add missing axes
internal enum class Axis {
  SELF,
  CHILD,
  PARENT,
  DESCENDANT,
  ANCESTOR,
  DESCENDANT_OR_SELF,
  ANCESTOR_OR_SELF,
  ATTRIBUTE;

  override fun toString() =
      this.name.toLowerCase().replace('_', '-')
}
