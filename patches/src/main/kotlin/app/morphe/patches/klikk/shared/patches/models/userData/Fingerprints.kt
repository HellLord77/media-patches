package app.morphe.patches.klikk.shared.patches.models.userData

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object GetIdFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/models/UserData;",
    name = "getId",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = String::class.java.descriptorString(),
    parameters = emptyList(),
)
