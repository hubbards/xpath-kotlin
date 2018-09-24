package xpath

class LiteralNumber(number: Number) : Expression() {
  private val syntax = number.toString()

  override fun abbreviated() = syntax

  override fun unabbreviated() = syntax
}
