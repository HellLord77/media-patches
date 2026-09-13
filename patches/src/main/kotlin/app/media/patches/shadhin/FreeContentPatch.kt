package app.media.patches.shadhin

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction

val freeContentPatch = bytecodePatch {
    execute {
        IsPaidGetterFingerprint.let {
            val getBoolean = it.instructionMatches[0]
            val getBooleanDestRegister =
                getBoolean.getInstruction<TwoRegisterInstruction>().registerA

            it.method.replaceInstruction(
                getBoolean.index,
                "const/4 v$getBooleanDestRegister, 0x0",
            )
        }
    }
}