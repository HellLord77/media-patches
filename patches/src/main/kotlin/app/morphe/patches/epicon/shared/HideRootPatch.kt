package app.morphe.patches.epicon.shared

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.util.matchAllMethodIndicesForEach
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction10x

@Suppress("unused")
val hideRootPatch =
    bytecodePatch(
        name = "Hide root",
        description = "Hides device root status.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON)

        execute {
            XposedBridgeFingerprint.matchAllMethodIndicesForEach {
                addInstruction(it, BuilderInstruction10x(Opcode.RETURN_VOID))
            }
        }
    }
