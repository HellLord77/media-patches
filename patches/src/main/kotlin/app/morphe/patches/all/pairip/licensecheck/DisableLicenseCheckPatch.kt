package app.morphe.patches.all.pairip.licensecheck

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.pairip.shared.patches.licensecheck.licenseClient.checkLicensePatch

@Suppress("unused")
val disableLicenseCheckPatch = bytecodePatch(
    name = "Disable PairIP license check",
    description = "Disables Play Integrity API (pairip) client-side license check.",
    default = false
) {
    dependsOn(checkLicensePatch)
}