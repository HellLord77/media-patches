package app.morphe.patches.shared.morphe.okhttp3

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.morphe.java.net.URI
import app.morphe.patches.shared.morphe.java.net.URL
import com.android.tools.smali.dexlib2.AccessFlags

object HttpUrl {
    override fun toString() = "Lokhttp3/HttpUrl;"

    object ToUrlFingerprint :
        Fingerprint(
            definingClass = HttpUrl.toString(),
            name = "toUrl",
            accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
            returnType = URL.toString(),
            parameters = emptyList(),
        )

    object ToUriFingerprint :
        Fingerprint(
            definingClass = HttpUrl.toString(),
            name = "toUri",
            accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
            returnType = URI.toString(),
            parameters = emptyList(),
        )

    object Builder {
        override fun toString() = $$"Lokhttp3/HttpUrl$Builder;"
    }

    object Companion {
        override fun toString() = $$"Lokhttp3/HttpUrl$Companion;"
    }
}
