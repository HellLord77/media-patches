package app.morphe.patches.all.pairip.bypass

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.morphe.com.pairip.licensecheck.LicenseClient
import app.morphe.util.matchSingle
import app.morphe.util.returnEarly

@Suppress("unused")
val bypassLicenseCheckPatch =
    bytecodePatch(
        name = "Bypass PairIP license check",
        description = "Bypasses Play Integrity API (pairip) client-side license check.",
        default = false,
    ) {
        execute {
            LicenseClient.CheckLicenseFingerprint
                .matchSingle()
                .method
                .returnEarly()
        }
    }
