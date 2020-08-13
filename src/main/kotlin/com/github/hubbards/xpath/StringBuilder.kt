package com.github.hubbards.xpath

internal fun StringBuilder.parenthesize(string: String) {
  append('(')
  append(string)
  append(')')
}

internal fun StringBuilder.brackets(string: String) {
  append('[')
  append(string)
  append(']')
}
