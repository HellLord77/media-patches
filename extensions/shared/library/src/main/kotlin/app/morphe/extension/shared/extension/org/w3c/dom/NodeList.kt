package app.morphe.extension.shared.extension.org.w3c.dom

import org.w3c.dom.Node
import org.w3c.dom.NodeList

operator fun NodeList.iterator(): Iterator<Node> =
    object : Iterator<Node> {
        private var index = 0

        override fun next(): Node {
            if (!hasNext()) throw NoSuchElementException()
            return item(index++)!!
        }

        override fun hasNext() = index < length
    }

fun NodeList.iterable() = Iterable { iterator() }
