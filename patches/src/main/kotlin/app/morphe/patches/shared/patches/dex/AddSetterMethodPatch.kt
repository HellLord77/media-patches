package app.morphe.patches.shared.patches.dex

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod.Companion.toMutable
import app.morphe.patches.shared.Type
import app.morphe.util.getReference
import app.morphe.util.matchSingle
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodImplementation
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodParameter
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction10x
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction11x
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction22c

private fun iputOpcode(type: String) =
    when (type.take(1)) {
        Type.int, Type.float -> Opcode.IPUT
        Type.long, Type.double -> Opcode.IPUT_WIDE
        "L", "[" -> Opcode.IPUT_OBJECT
        Type.boolean -> Opcode.IPUT_BOOLEAN
        Type.byte -> Opcode.IPUT_BYTE
        Type.char -> Opcode.IPUT_CHAR
        Type.short -> Opcode.IPUT_SHORT
        else -> throw PatchException("Unsupported type: $type")
    }

fun addSetterMethodPatch(
    fieldReference: MutableFieldReference,
    methodName: String? = null,
    moduleName: String? = null,
    isFluent: Boolean = false,
) = bytecodePatch {
    finalize {
        val classDef = mutableClassDefBy(fieldReference.definingClass)
        val method =
            ImmutableMethod(
                classDef.toString(),
                methodName ?: "set${fieldReference.methodNameSuffix(moduleName)}",
                listOf(ImmutableMethodParameter(fieldReference.type, null, null)),
                if (isFluent) fieldReference.definingClass else Type.void,
                AccessFlags.PUBLIC.value or AccessFlags.FINAL.value,
                null,
                null,
                ImmutableMethodImplementation(
                    1 + registerCount(fieldReference.type),
                    listOf(
                        ImmutableInstruction22c(
                            iputOpcode(fieldReference.type),
                            1,
                            0,
                            fieldReference.toImmutable(),
                        ),
                        if (isFluent) {
                            ImmutableInstruction11x(
                                Opcode.RETURN_OBJECT,
                                0,
                            )
                        } else {
                            ImmutableInstruction10x(Opcode.RETURN_VOID)
                        },
                    ),
                    null,
                    null,
                ),
            )
        classDef.methods.add(method.toMutable())
    }
}

fun addSetterMethodPatch(
    fieldReferenceProvider: BytecodePatchContext.() -> FieldReference,
    methodName: String? = null,
    moduleName: String? = null,
    isFluent: Boolean = false,
) = bytecodePatch {
    val fieldReference = MutableFieldReference("", "", "")

    dependsOn(addSetterMethodPatch(fieldReference, methodName, moduleName, isFluent))

    execute {
        fieldReferenceProvider(this).apply {
            fieldReference.definingClass = definingClass
            fieldReference.name = name
            fieldReference.type = type
        }
    }
}

fun addSetterMethodPatch(
    fingerprint: Fingerprint,
    methodName: String? = null,
    moduleName: String? = null,
    isFluent: Boolean = false,
) = addSetterMethodPatch({
    fingerprint
        .matchSingle()
        .instructionMatches
        .first()
        .instruction
        .getReference<FieldReference>()!!
}, methodName, moduleName, isFluent)
