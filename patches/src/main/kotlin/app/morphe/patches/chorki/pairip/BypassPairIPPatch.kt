package app.morphe.patches.chorki.pairip

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.pairip.licensecheck.disableLicenseCheckPatch
import app.morphe.patches.chorki.shared.Constants.COMPATIBILITY_CHORKI

@Suppress("unused")
val bypassPairIPPatch = bytecodePatch(
    name = "Bypass PairIP",
    description = "Bypasses PairIP checks.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CHORKI)

    dependsOn(disableLicenseCheckPatch)
}