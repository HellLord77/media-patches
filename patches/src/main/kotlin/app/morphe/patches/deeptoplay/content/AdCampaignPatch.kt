package app.morphe.patches.deeptoplay.content

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.hex.hexPatch
import app.morphe.patches.all.misc.lib.stripNonArmNativeLibraryPatch
import app.morphe.patches.deeptoplay.shared.Constants.COMPATIBILITY_DEEPTOPLAY
import app.morphe.patches.shared.requireArm


@Suppress("unused")
val adCampaignPatch = rawResourcePatch(
    name = "Ad campaign",
    description = "Resolve ad_campaign using ads.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_DEEPTOPLAY)

    availability(requireArm)

    dependsOn(stripNonArmNativeLibraryPatch, hexPatch(true, block = {
        // package:core_models/src/content_model.dart -> ContentModel.fromJson
        // ad_campaign -> ads
        // ADD             X2, X27, #0x14,LSL#12 -> ADD             X2, X27, #0x1D,LSL#12
        // LDR             X2, [X2,#0x108]       -> LDR             X2, [X2,#0xF50]
        """
            62 53 40 91
            42 84 40 f9
        """ asPatternTo """
            62 77 40 91
            42 a8 47 f9
        """ inFile "lib/arm64-v8a/libapp.so"
    }))
}