package app.media.patches.shadhin

import app.media.patches.shared.Constants.COMPATIBILITY_SHADHIN
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction

@Suppress("unused")
val streamPatch = bytecodePatch(
    name = "Stream Free Content",
    description = "Stream podcasts and audiobooks for free.",
    default = true
) {
    compatibleWith(COMPATIBILITY_SHADHIN)

    dependsOn(freeContentPatch)

    execute {
        AbstractFingerprint.matchAll().forEach { match ->
            match.originalMethod.let {
                if (!it.annotations.any { annotation ->
                        annotation.elements.any { element ->
                            element.value.toString().trim('"').endsWith("/streamings/url")
                        }
                    }) {
                    return@forEach
                }

                val parameterIndex = it.parameters.indexOfFirst { parameter ->
                    parameter.annotations.any { annotation ->
                        annotation.elements.any { element ->
                            element.value.toString().trim('"') == "ContentType"
                        }
                    }
                }

                Fingerprint(filters = listOf(methodCall(it))).let { fingerprint ->
                    val invokeInterface = fingerprint.instructionMatches[0]
                    val registerIndex = invokeInterface.getInstruction<FiveRegisterInstruction>()
                        .let { instruction ->
                            listOf(
                                instruction.registerD,
                                instruction.registerE,
                                instruction.registerF,
                            )
                        }[parameterIndex]

                    fingerprint.method.addInstruction(
                        invokeInterface.index,
                        """const-string v$registerIndex, "S"""",
                    )
                }
            }
        }
    }
}