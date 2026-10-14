package app.morphe.patches.platform8.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import app.morphe.patches.shared.isNotExtension
import app.morphe.patches.shared.morphe.kotlin.String
import com.android.tools.smali.dexlib2.Opcode

object ContentAvailabilityFieldAccessFingerprint :
    Fingerprint(
        filters =
            listOf(
                fieldAccess(
                    definingClass = "Lcom/digivive/nexgtv/models/",
                    name = "content_availability",
                    type = String.toString(),
                    opcode = Opcode.IGET_OBJECT,
                )
            )
    )

object PerformRequestMethodCallFingerprint :
    Fingerprint(
        filters =
            listOf(
                methodCall(
                    definingClass = "Lcom/android/volley/Network;",
                    name = "performRequest",
                    parameters = listOf("Lcom/android/volley/Request;"),
                    returnType = "Lcom/android/volley/NetworkResponse;",
                    opcode = Opcode.INVOKE_INTERFACE,
                )
            ),
        custom = ::isNotExtension,
    )
