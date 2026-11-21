package app.morphe.patches.shared.morphe.kotlin.coroutines

import kotlin.coroutines.CoroutineContext

object CoroutineContext {
    override fun toString(): String = CoroutineContext::class.java.descriptorString()

    object Element {
        override fun toString(): String = CoroutineContext.Element::class.java.descriptorString()
    }
}
