package app.morphe.patches.shared.morphe.com.pairip

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.java.lang.Object
import app.morphe.patches.shared.morphe.java.lang.String
import com.android.tools.smali.dexlib2.AccessFlags

object VMRunner {
    override fun toString() = "Lcom/pairip/VMRunner;"

    object ClassConstructorFingerprint :
        Fingerprint(
            definingClass = VMRunner.toString(),
            name = "<clinit>",
            accessFlags = listOf(AccessFlags.STATIC, AccessFlags.CONSTRUCTOR),
            returnType = Type.void,
            parameters = emptyList(),
        )

    object InvokeFingerprint :
        Fingerprint(
            definingClass = VMRunner.toString(),
            name = "invoke",
            accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
            returnType = Object.toString(),
            parameters = listOf(String.toString(), "[$Object"),
        )
}
