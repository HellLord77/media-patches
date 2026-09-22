package app.morphe.patches.shadhin.content

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.getRegisterName
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction

val freeContentPatch = bytecodePatch {
    execute {
        IsPaidGetterFingerprint.let {
            val getBoolean = it.instructionMatches[0]
            val destinationRegister = getBoolean.getInstruction<TwoRegisterInstruction>().registerA
            val registerName = it.originalMethod.getRegisterName(destinationRegister)

            it.method.replaceInstruction(
                getBoolean.index,
                "const/4 $registerName, 0x0",
            )
        }
    }
}