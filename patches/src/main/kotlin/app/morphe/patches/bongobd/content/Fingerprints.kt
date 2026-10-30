package app.morphe.patches.bongobd.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.isNotExtension
import app.morphe.patches.shared.morphe.com.google.gson.Gson
import app.morphe.patches.shared.morphe.java.lang.Class
import app.morphe.patches.shared.morphe.java.lang.Object
import app.morphe.patches.shared.morphe.java.lang.String
import app.morphe.patches.shared.morphe.okhttp3.Request
import app.morphe.patches.shared.morphe.okhttp3.Response
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object GetContentDetailsMethodCallFingerprint :
    Fingerprint(
        filters =
            listOf(
                methodCall(
                    definingClass = "Lcom/bongo/bongobd/view/network/ApiServiceSaas;",
                    name = "getContentDetails",
                    parameters = listOf(Type.STRING, Type.CONTINUATION),
                    returnType = Type.OBJECT,
                    opcode = Opcode.INVOKE_INTERFACE,
                ),
            ),
        custom = ::isNotExtension,
    )

object FromJsonFingerprint : Fingerprint(
    definingClass = Gson.toString(),
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = Object.toString(),
    parameters = listOf(String.toString(), Class.toString()),
)

object RequestFingerprint : Fingerprint(
    definingClass = Response.toString(),
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = Request.toString(),
    parameters = emptyList(),
)

object HeaderFingerprint :
    Fingerprint(
        definingClass = Request.toString(),
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
        returnType = String.toString(),
        parameters = listOf(String.toString()),
    )
