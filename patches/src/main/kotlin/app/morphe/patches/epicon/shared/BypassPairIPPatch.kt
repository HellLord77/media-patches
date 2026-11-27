package app.morphe.patches.epicon.shared

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.pairip.bypass.bypassLicenseCheckPatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON_TV

@Suppress("unused")
val bypassPairIPPatch =
    rawResourcePatch(
        name = "Bypass PairIP",
        description = "Bypasses PairIP checks.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON, COMPATIBILITY_EPICON_TV)

        dependsOn(bypassLicenseCheckPatch)
    }
