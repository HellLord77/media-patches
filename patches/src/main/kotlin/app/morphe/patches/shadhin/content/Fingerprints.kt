package app.morphe.patches.shadhin.content

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.morphe.java.lang.Object
import app.morphe.patches.shared.morphe.java.lang.String
import com.android.tools.smali.dexlib2.AccessFlags

object FetchStreamingUrlFingerprint :
    Fingerprint(
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
        returnType = Object.toString(),
        parameters = listOf(String.toString(), String.toString(), String.toString(), "L"),
        custom = { method, _ ->
            method.annotations
                .flatMap { it.elements }
                .any { it.value.toString().endsWith("/streamings/url\"") }
        },
    )
