package xpath

// TODO add missing operators
enum class Operator {
  EQUAL,
  NOT_EQUAL,
  GREATER_THAN,
  LESS_THAN,
  GREATER_THAN_OR_EQUAL_TO,
  LESS_THAN_OR_EQUAL_TO,
  AND,
  OR,
  UNION;

  override fun toString() =
      when (this) {
        EQUAL -> "="
        NOT_EQUAL -> "!="
        GREATER_THAN -> ">"
        LESS_THAN -> "<"
        GREATER_THAN_OR_EQUAL_TO -> ">="
        LESS_THAN_OR_EQUAL_TO -> "<="
        AND -> "and"
        OR -> "or"
        UNION -> "|"
      }
}
