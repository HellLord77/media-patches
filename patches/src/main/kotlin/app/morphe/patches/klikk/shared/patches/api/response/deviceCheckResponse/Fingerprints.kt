package app.morphe.patches.klikk.shared.patches.api.response.deviceCheckResponse

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object GetResultFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/api/response/DeviceCheckResponse;",
    name = "getResult",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = Boolean::class.java.descriptorString(),
    parameters = emptyList(),
)