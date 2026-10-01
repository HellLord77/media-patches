package app.morphe.patches.bongobd.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.Opcode
import kotlin.coroutines.Continuation

object GetContentDetailsInvokerFingerprint : Fingerprint(
    filters = listOf(
        methodCall(
            definingClass = "Lcom/bongo/bongobd/view/network/ApiServiceSaas;",
            name = "getContentDetails",
            parameters = listOf(
                String::class.java.descriptorString(),
                Continuation::class.java.descriptorString(),
            ),
            returnType = Any::class.java.descriptorString(),
            opcode = Opcode.INVOKE_INTERFACE,
        )
    )
)