package app.morphe.patches.shared.morphe.com.pairip.licensecheck

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.com.android.content.Context
import com.android.tools.smali.dexlib2.AccessFlags

object LicenseClient {
    override fun toString() = "Lcom/pairip/licensecheck/LicenseClient;"

    object CheckLicenseFingerprint :
        Fingerprint(
            definingClass = LicenseClient.toString(),
            name = "checkLicense",
            accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
            returnType = Type.void,
            parameters = listOf(Context.toString()),
        )
}
