package xpath

interface Syntax {
  fun unabbreviated(): String

  fun abbreviated(): String
}
