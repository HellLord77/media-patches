package app.morphe.patches.shared.morphe.com.pairip

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.Type
import com.android.tools.smali.dexlib2.AccessFlags

object StartupLauncher {
    override fun toString() = "Lcom/pairip/StartupLauncher;"

    object LaunchFingerprint :
        Fingerprint(
            definingClass = StartupLauncher.toString(),
            name = "launch",
            accessFlags =
                listOf(AccessFlags.PUBLIC, AccessFlags.STATIC, AccessFlags.DECLARED_SYNCHRONIZED),
            returnType = Type.void,
            parameters = emptyList(),
        )
}
