package app.morphe.patches.epicon.shared

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.pairip.bypass.bypassLicenseCheckPatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON

@Suppress("unused")
val bypassPairIPPatch =
    rawResourcePatch(
        name = "Bypass PairIP",
        description = "Bypasses PairIP checks.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON)

        dependsOn(bypassLicenseCheckPatch)
    }
