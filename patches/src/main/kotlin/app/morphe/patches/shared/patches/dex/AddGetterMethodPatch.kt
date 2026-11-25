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
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction10x
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction11x
import com.android.tools.smali.dexlib2.immutable.instruction.ImmutableInstruction22c

private fun returnOpcode(type: String) =
    when (type.take(1)) {
        Type.void -> Opcode.RETURN_VOID
        Type.boolean, Type.char, Type.byte, Type.short, Type.int, Type.float -> Opcode.RETURN
        Type.long, Type.double -> Opcode.RETURN_WIDE
        "L", "[" -> Opcode.RETURN_OBJECT
        else -> throw PatchException("Unsupported type: $type")
    }

private fun igetOpcode(type: String) =
    when (type.take(1)) {
        Type.int, Type.float -> Opcode.IGET
        Type.long, Type.double -> Opcode.IGET_WIDE
        "L", "[" -> Opcode.IGET_OBJECT
        Type.boolean -> Opcode.IGET_BOOLEAN
        Type.byte -> Opcode.IGET_BYTE
        Type.char -> Opcode.IGET_CHAR
        Type.short -> Opcode.IGET_SHORT
        else -> throw PatchException("Unsupported type: $type")
    }

fun addGetterMethodPatch(
    fieldReference: MutableFieldReference,
    methodName: String? = null,
    moduleName: String? = null,
) = bytecodePatch {
    finalize {
        val classDef = mutableClassDefBy(fieldReference.definingClass)
        val locals = registerCount(fieldReference.type)
        val method =
            ImmutableMethod(
                classDef.toString(),
                methodName ?: "get${fieldReference.methodNameSuffix(moduleName)}",
                emptyList(),
                fieldReference.type,
                AccessFlags.PUBLIC.value or AccessFlags.FINAL.value,
                null,
                null,
                ImmutableMethodImplementation(
                    1 + locals,
                    listOf(
                        ImmutableInstruction22c(
                            igetOpcode(fieldReference.type),
                            0,
                            locals,
                            fieldReference.toImmutable(),
                        ),
                        if (fieldReference.type == Type.void) {
                            ImmutableInstruction10x(Opcode.RETURN_VOID)
                        } else {
                            ImmutableInstruction11x(
                                returnOpcode(fieldReference.type),
                                0,
                            )
                        },
                    ),
                    null,
                    null,
                ),
            )
        classDef.methods.add(method.toMutable())
    }
}

fun addGetterMethodPatch(
    fieldReferenceProvider: BytecodePatchContext.() -> FieldReference,
    methodName: String? = null,
    moduleName: String? = null,
) = bytecodePatch {
    val fieldReference = MutableFieldReference("", "", "")

    dependsOn(addGetterMethodPatch(fieldReference, methodName, moduleName))

    execute {
        fieldReferenceProvider(this).apply {
            fieldReference.definingClass = definingClass
            fieldReference.name = name
            fieldReference.type = type
        }
    }
}

fun addGetterMethodPatch(
    fingerprint: Fingerprint,
    methodName: String? = null,
    moduleName: String? = null,
) = addGetterMethodPatch({
    fingerprint
        .matchSingle()
        .instructionMatches
        .first()
        .instruction
        .getReference<FieldReference>()!!
}, methodName, moduleName)
