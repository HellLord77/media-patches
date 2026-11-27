package app.morphe.patches.shared.patches.dex

import app.morphe.patches.shared.Type
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableFieldReference

data class MutableFieldReference(
    var definingClass: String,
    var name: String,
    var type: String,
) {
    fun toImmutable() = ImmutableFieldReference(definingClass, name, type)

    fun methodNameSuffix(moduleName: String?) = "${name.replaceFirstChar(Char::uppercase)}${moduleName?.let { "$$it" } ?: ""}"
}

internal fun registerCount(type: String) =
    when (type.take(1)) {
        Type.long, Type.double -> 2
        else -> 1
    }
