package app.morphe.patches.bongobdandroidtv

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants.COMPATIBILITY_BONGOANDROIDTV
import app.morphe.patches.shared.getRegisterName
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction


@Suppress("unused")
val freeContentPatch = bytecodePatch(
    name = "Free paid content",
    description = "Make paid shows and movies free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_BONGOANDROIDTV)

    extendWith("extensions/bongo.mpe")

    execute {
        GetVideoDetailsDataFingerprint.let { match ->
            Fingerprint(filters = listOf(methodCall(match.originalMethod))).matchAll()
                .filterNot { it.originalClassDef.startsWith("Lapp/morphe/extension") }.forEach {
                    val invokeInterface = it.instructionMatches[0]
                    val instruction = invokeInterface.getInstruction<FiveRegisterInstruction>()
                    val registerCName = it.originalMethod.getRegisterName(instruction.registerC)
                    val registerDName = it.originalMethod.getRegisterName(instruction.registerD)

                    it.method.replaceInstruction(
                        invokeInterface.index,
                        "invoke-static {$registerCName, $registerDName}, Lapp/morphe/extension/bongo/patches/FreeContentPatch;->getVideoDetailsData(Lsaas/ott/smarttv/ui/details/data/DetailsEndPoint;Ljava/lang/String;)Lretrofit2/Call;",
                    )
                }
        }
    }
}