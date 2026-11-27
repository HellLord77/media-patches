package app.morphe.patches.shared.morphe.java.lang

import java.lang.Void
import kotlin.String

object Void {
    override fun toString(): String = Void::class.java.descriptorString()
}
