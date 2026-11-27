package app.morphe.patches.shared.morphe.com.pairip.application

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.com.android.content.Context
import com.android.tools.smali.dexlib2.AccessFlags

object Application {
    override fun toString() = "Lcom/pairip/application/Application;"

    object AttachBaseContextFingerprint :
        Fingerprint(
            definingClass = Application.toString(),
            name = "attachBaseContext",
            accessFlags = listOf(AccessFlags.PROTECTED),
            returnType = Type.void,
            parameters = listOf(Context.toString()),
        )
}
