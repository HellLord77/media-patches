package app.morphe.patches.hoichoi.shared

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.pairip.strip.stripPairIPPatch
import app.morphe.patches.hoichoi.shared.Constants.COMPATIBILITY_HOICHOI

@Suppress("unused")
val stripPairIPPatch = bytecodePatch(
    name = "Strip PairIP",
    description = "Strips PairIP checks.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_HOICHOI)

    dependsOn(stripPairIPPatch("hoichoi"))
}