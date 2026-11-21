package app.morphe.patches.shared.morphe.java.net

import java.net.URI

object URI {
    override fun toString(): String = URI::class.java.descriptorString()
}
