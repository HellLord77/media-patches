package app.morphe.patches.shadhin

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants.COMPATIBILITY_SHADHIN
import app.morphe.patches.shared.getRegisterName
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction

@Suppress("unused")
val streamPatch = bytecodePatch(
    name = "Stream paid content",
    description = "Stream podcasts and audiobooks for free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SHADHIN)

    dependsOn(freeContentPatch)

    execute {
        FetchStreamingUrlFingerprint.originalMethod.let { retrofitMethod ->
            val parameterIndex = retrofitMethod.parameters.indexOfFirst { parameter ->
                parameter.annotations.flatMap { it.elements }
                    .any { it.value.toString() == "\"ContentType\"" }
            }

            Fingerprint(filters = listOf(methodCall(retrofitMethod))).matchAll().forEach { match ->
                val invokeInterface = match.instructionMatches[0]
                val contentTypeRegister = invokeInterface.getInstruction<FiveRegisterInstruction>()
                    .let { listOf(it.registerD, it.registerE, it.registerF) }[parameterIndex]
                val registerName = match.originalMethod.getRegisterName(contentTypeRegister)

                match.method.addInstruction(
                    invokeInterface.index,
                    "const-string $registerName, \"S\"",
                )
            }
        }
    }
}