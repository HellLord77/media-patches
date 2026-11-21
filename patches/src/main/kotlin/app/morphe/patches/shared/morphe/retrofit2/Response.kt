package app.morphe.patches.shared.morphe.retrofit2

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.morphe.java.lang.String
import com.android.tools.smali.dexlib2.AccessFlags
import app.morphe.patches.shared.morphe.okhttp3.Response as OkHttp3Response

object Response {
    override fun toString() = "Lretrofit2/Response;"

    object RawFingerprint : Fingerprint(
        definingClass = Response.toString(),
        name = "raw",
        accessFlags = listOf(AccessFlags.PUBLIC),
        returnType = OkHttp3Response.toString(),
        parameters = emptyList(),
    )

    object ToStringFingerprint : Fingerprint(
        definingClass = Response.toString(),
        name = "toString",
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
        returnType = String.toString(),
        parameters = emptyList(),
    )
}
