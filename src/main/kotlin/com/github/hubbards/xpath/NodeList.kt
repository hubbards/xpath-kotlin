package com.github.hubbards.xpath

import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * Implement the get convention for accessing [Node] items in this [NodeList].
 */
operator fun NodeList.get(i: Int): Node =
  item(i)

/**
 * Implement the iterator convention for accessing [Node] items in this
 * [NodeList].
 */
operator fun NodeList.iterator(): Iterator<Node> =
  object : Iterator<Node> {
    var index = 0

    override fun hasNext() =
      index < length

    override fun next() =
      item(index++)
  }
