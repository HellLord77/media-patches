package app.morphe.patches.epicon.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.matchSingle
import app.morphe.util.returnBoxedBooleanEarly

fun freeContentPatch(
    name: String,
    compatibility: Compatibility,
    getFreePremiumFingerprint: Fingerprint,
    isFreeFingerprint: Fingerprint,
) = bytecodePatch(
    name = name,
    description = "Marks content as free.",
    default = true,
) {
    compatibleWith(compatibility)

    execute {
        getFreePremiumFingerprint
            .matchSingle()
            .method
            .returnBoxedBooleanEarly(false)

        isFreeFingerprint
            .matchSingle()
            .method
            .returnBoxedBooleanEarly(false)
    }
}
