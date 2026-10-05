package app.morphe.patches.kabbik.pairip

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.pairip.licensecheck.disableLicenseCheckPatch
import app.morphe.patches.kabbik.shared.Constants.COMPATIBILITY_KABBIK

@Suppress("unused")
val bypassPairIPPatch = bytecodePatch(
    name = "Bypass PairIP",
    description = "Bypasses PairIP checks.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KABBIK)

    dependsOn(disableLicenseCheckPatch)
}