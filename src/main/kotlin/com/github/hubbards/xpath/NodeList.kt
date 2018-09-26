package com.github.hubbards.xpath

import org.w3c.dom.Node
import org.w3c.dom.NodeList

operator fun NodeList.get(i: Int) =
    item(i)

operator fun NodeList.iterator() =
    object : Iterator<Node> {
      var index = 0

      override fun hasNext() =
          index < length

      override fun next() =
          item(index++)
    }
