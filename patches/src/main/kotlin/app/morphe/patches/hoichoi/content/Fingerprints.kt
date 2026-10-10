package app.morphe.patches.hoichoi.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.okhttp3.MediaType
import app.morphe.patches.shared.morphe.okhttp3.ResponseBody
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object MediaTypeCompanionFieldAccessFingerprint : Fingerprint(
    definingClass = MediaType.toString(),
    name = "<clinit>",
    accessFlags = listOf(AccessFlags.STATIC, AccessFlags.CONSTRUCTOR),
    returnType = Type.void,
    parameters = emptyList(),
    filters = listOf(
        fieldAccess(
            definingClass = MediaType.toString(),
            type = MediaType.Companion.toString(),
            opcode = Opcode.SPUT_OBJECT,
        )
    ),
)

object ResponseBodyCompanionFieldAccessFingerprint : Fingerprint(
    definingClass = ResponseBody.toString(),
    name = "<clinit>",
    accessFlags = listOf(AccessFlags.STATIC, AccessFlags.CONSTRUCTOR),
    returnType = Type.void,
    parameters = emptyList(),
    filters = listOf(
        fieldAccess(
            definingClass = ResponseBody.toString(),
            type = ResponseBody.Companion.toString(),
            opcode = Opcode.SPUT_OBJECT,
        )
    ),
)
