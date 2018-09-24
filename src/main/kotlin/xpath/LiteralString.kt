package xpath

class LiteralString(string: String) : Expression() {
  private val syntax = "'$string'"

  override fun abbreviated() = syntax

  override fun unabbreviated() = syntax
}
