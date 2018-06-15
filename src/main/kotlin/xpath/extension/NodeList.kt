package xpath.extension

import org.w3c.dom.Node
import org.w3c.dom.NodeList

operator fun NodeList.get(i: Int) = this.item(i)

operator fun NodeList.iterator() =
    object : Iterator<Node> {
      var index = 0

      override fun hasNext() = index < length

      override fun next() = item(index++)
    }
