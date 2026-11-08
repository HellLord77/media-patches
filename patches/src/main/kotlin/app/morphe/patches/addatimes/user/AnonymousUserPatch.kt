package app.morphe.patches.addatimes.user

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.addatimes.extension.sharedExtensionPatch
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES
import app.morphe.patches.addatimes.shared.morphe.com.addatimes.addatimes.AddaTimesApp
import app.morphe.util.matchSingle
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction35c
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableMethodReference

private const val EXTENSION_CLASS = "Lapp/morphe/extension/addatimes/patches/AnonymousUserPatch;"

@Suppress("unused")
val anonymousUserPatch =
    bytecodePatch(
        name = "Anonymous user",
        description = "Log in as anonymous user.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_ADDATIMES)

        dependsOn(sharedExtensionPatch)

        execute {
            AddaTimesApp.OnCreateFingerprint.matchSingle().method.apply {
                addInstruction(
                    0,
                    BuilderInstruction35c(
                        Opcode.INVOKE_STATIC,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        ImmutableMethodReference(EXTENSION_CLASS, name, parameters, returnType),
                    ),
                )
            }
        }
    }
