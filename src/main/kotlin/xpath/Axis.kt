package xpath

enum class Axis {
  SELF,
  CHILD,
  PARENT,
  DESCENDANT,
  ANCESTOR,
  DESCENDANT_OR_SELF,
  ANCESTOR_OR_SELF,
  ATTRIBUTE;

  override fun toString() = this.name.toLowerCase().replace('_', '-')
}
