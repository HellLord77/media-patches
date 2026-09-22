package app.morphe.patches.bongobd.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.bongobd.extension.sharedExtensionPatch
import app.morphe.patches.bongobd.shared.Constants.COMPATIBILITY_BONGO
import app.morphe.patches.shared.getRegisterName
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction


@Suppress("unused")
val freeContentPatch = bytecodePatch(
    name = "Free content",
    description = "Use alternative api to get content details.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_BONGO)

    dependsOn(sharedExtensionPatch)

    execute {
        GetContentDetailsFingerprint.let { match ->
            Fingerprint(filters = listOf(methodCall(match.originalMethod))).matchAll()
                .filterNot { it.originalClassDef.startsWith("Lapp/morphe/extension") }.forEach {
                    val invokeInterface = it.instructionMatches[0]
                    val instruction = invokeInterface.getInstruction<FiveRegisterInstruction>()
                    val registerCName = it.originalMethod.getRegisterName(instruction.registerC)
                    val registerDName = it.originalMethod.getRegisterName(instruction.registerD)
                    val registerEName = it.originalMethod.getRegisterName(instruction.registerE)

                    it.method.replaceInstruction(
                        invokeInterface.index,
                        "invoke-static {$registerCName, $registerDName, $registerEName}, Lapp/morphe/extension/bongo/patches/FreeContentPatch;->getContentDetails(Lcom/bongo/bongobd/view/network/ApiServiceSaas;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                    )
                }
        }
    }
}