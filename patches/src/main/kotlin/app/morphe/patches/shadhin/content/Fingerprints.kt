package app.morphe.patches.shadhin.content

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object IsPaidGetterFingerprint : Fingerprint(
    definingClass = "Lcom/gm/shadhin/data/remote/api/model/MainContentModel;",
    name = "isPaid",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Z",
    parameters = emptyList(),
)

object FetchStreamingUrlFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = Any::class.java.descriptorString(),
    parameters = listOf(
        String::class.java.descriptorString(),
        String::class.java.descriptorString(),
        String::class.java.descriptorString(),
        "L",
    ),
    custom = { method, _ ->
        method.annotations.flatMap { it.elements }
            .any { it.value.toString().endsWith("/streamings/url\"") }
    }
)