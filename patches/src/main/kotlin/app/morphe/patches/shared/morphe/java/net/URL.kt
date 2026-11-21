package app.morphe.patches.shared.morphe.java.net

import java.net.URL

object URL {
    override fun toString(): String = URL::class.java.descriptorString()
}
