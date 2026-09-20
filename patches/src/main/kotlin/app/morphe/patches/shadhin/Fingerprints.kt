package app.morphe.patches.shadhin

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object IsPaidGetterFingerprint : Fingerprint(
    definingClass = "Lcom/gm/shadhin/data/remote/api/model/MainContentModel;",
    name = "isPaid",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        fieldAccess(
            opcode = Opcode.IGET_BOOLEAN,
            definingClass = "this",
            name = "isPaid",
            type = "Z",
        ),
    ),
)

object FourParameterMethodFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = "Ljava/lang/Object;",
    parameters = listOf(
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "L",
    ),
)