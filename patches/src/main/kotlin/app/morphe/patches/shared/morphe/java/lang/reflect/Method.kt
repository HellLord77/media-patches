package app.morphe.patches.shared.morphe.java.lang.reflect

import java.lang.reflect.Method

object Method {
    override fun toString(): String = Method::class.java.descriptorString()
}
