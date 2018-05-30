package xpath

class LiteralString(val string: String) : Expression() {
  private val syntax = "'$string'"

  override fun abbreviated() = syntax

  override fun unabbreviated() = syntax
}
