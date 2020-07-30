package com.github.hubbards.xpath

/**
 * A unary or binary operator. Note that operators are partially ordered by
 * (binary) precedence, from lowest to highest, and [compareTo] defines a linear
 * extension of this order.
 */
internal enum class Operator {
  OR,
  AND,
  EQUAL,
  NOT_EQUAL,
  LESS_THAN_OR_EQUAL_TO,
  LESS_THAN,
  GREATER_THAN_OR_EQUAL_TO,
  GREATER_THAN,
  PLUS,
  MINUS,
  TIMES,
  DIVIDE,
  MODULO,
  UNION;

  override fun toString() =
      when (this) {
        OR -> "or"
        AND -> "and"
        EQUAL -> "="
        NOT_EQUAL -> "!="
        LESS_THAN_OR_EQUAL_TO -> "<="
        LESS_THAN -> "<"
        GREATER_THAN_OR_EQUAL_TO -> ">="
        GREATER_THAN -> ">"
        PLUS -> "+"
        MINUS -> "-"
        TIMES -> "*"
        DIVIDE -> "div"
        MODULO -> "mod"
        UNION -> "|"
      }
}
