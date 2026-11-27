package app.morphe.patches.all.pairip.strip

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.com.pairip.StartupLauncher
import app.morphe.patches.shared.morphe.com.pairip.VMRunner
import app.morphe.patches.shared.morphe.java.lang.Object
import app.morphe.patches.shared.morphe.java.lang.String
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal object InvokeMethodCallFingerprint :
    Fingerprint(
        definingClass = StartupLauncher.toString(),
        name = "launch",
        accessFlags =
            listOf(AccessFlags.PUBLIC, AccessFlags.STATIC, AccessFlags.DECLARED_SYNCHRONIZED),
        returnType = Type.void,
        parameters = emptyList(),
        filters =
            listOf(
                methodCall(
                    definingClass = VMRunner.toString(),
                    name = "invoke",
                    parameters = listOf(String.toString(), "[$Object"),
                    returnType = Object.toString(),
                    opcode = Opcode.INVOKE_STATIC,
                ),
            ),
    )
