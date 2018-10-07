package com.github.hubbards.xpath

internal enum class Operator {
  EQUAL,
  NOT_EQUAL,
  GREATER_THAN,
  LESS_THAN,
  GREATER_THAN_OR_EQUAL_TO,
  LESS_THAN_OR_EQUAL_TO,
  AND,
  OR,
  PLUS,
  MINUS,
  TIMES,
  DIVIDE,
  MODULO,
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
        PLUS -> "+"
        MINUS -> "-"
        TIMES -> "*"
        DIVIDE -> "div"
        MODULO -> "mod"
        UNION -> "|"
      }
}
