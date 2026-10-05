package app.morphe.patches.chorkitv.pairip

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.pairip.licensecheck.disableLicenseCheckPatch
import app.morphe.patches.chorkitv.shared.Constants.COMPATIBILITY_CHORKITV

@Suppress("unused")
val bypassPairIPPatch = bytecodePatch(
    name = "Bypass PairIP",
    description = "Bypasses PairIP checks.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CHORKITV)

    dependsOn(disableLicenseCheckPatch)
}