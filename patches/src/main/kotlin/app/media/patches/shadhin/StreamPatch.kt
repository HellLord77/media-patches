package app.media.patches.shadhin

import app.media.patches.shared.Constants.COMPATIBILITY_SHADHIN
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction

@Suppress("unused")
val streamPatch = bytecodePatch(
    name = "Stream Paid Content",
    description = "Stream podcasts and audiobooks for free.",
    default = true
) {
    compatibleWith(COMPATIBILITY_SHADHIN)

    dependsOn(freeContentPatch)

    execute {
        FourArgumentMethodFingerprint.matchAll().filter { match ->
            match.originalMethod.annotations.flatMap { it.elements }
                .any { it.value.toString().endsWith("/streamings/url\"") }
        }.map { it.originalMethod }.forEach { retrofitMethod ->
            val parameterIndex = retrofitMethod.parameters.indexOfFirst { parameter ->
                parameter.annotations.flatMap { it.elements }
                    .any { it.value.toString() == "\"ContentType\"" }
            }

            Fingerprint(filters = listOf(methodCall(retrofitMethod))).matchAll()
                .forEach { methodInvoker ->
                    val invokeInterface = methodInvoker.instructionMatches[0]
                    val contentTypeReg = invokeInterface.getInstruction<FiveRegisterInstruction>()
                        .let { listOf(it.registerD, it.registerE, it.registerF) }[parameterIndex]

                    methodInvoker.method.addInstruction(
                        invokeInterface.index,
                        "const-string v$contentTypeReg, \"S\"",
                    )
                }
        }
    }
}