package app.morphe.patches.shared.morphe.java.lang.reflect

import java.lang.reflect.Field

object Field {
    override fun toString(): String = Field::class.java.descriptorString()
}
