package app.morphe.patches.deeptoplay.content

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.hex.hexPatch
import app.morphe.patches.all.misc.lib.stripNonArmNativeLibraryPatch
import app.morphe.patches.deeptoplay.shared.Constants.COMPATIBILITY_DEEPTOPLAY
import app.morphe.patches.shared.requireArm

@Suppress("unused")
val contentAccessPatch = rawResourcePatch(
    name = "Content access",
    description = "Resolve content_access to ContentAccess.free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_DEEPTOPLAY)

    availability(requireArm)

    dependsOn(stripNonArmNativeLibraryPatch, hexPatch(true, block = {
        // package:core_models/src/content_model.dart -> ContentModel.fromJson
        // content_access -> id
        // ADD             X2, X27, #0x14,LSL#12 -> ADD             X2, X27, #9,LSL#12
        // LDR             X2, [X2,#0x18]        -> LDR             X2, [X2,#0x758]
        """
            62 53 40 91
            42 0c 40 f9
        """ asPatternTo """
            62 27 40 91
            42 ac 43 f9
        """ inFile "lib/arm64-v8a/libapp.so"

        // package:core_models/src/content_model.dart -> ContentModel.fromJson
        // ContentAccess.unspecified -> ContentAccess.free
        // ADD             X16, X27, #0x14,LSL#12 -> ADD             X16, X27, #0x23,LSL#12
        // LDR             X16, [X16,#0x30]       -> LDR             X16, [X16,0x9A0]
        """
            70 53 40 91
            10 1a 40 f9
        """ asPatternTo """
            70 8f 40 91
            10 d2 44 f9
        """ inFile "lib/arm64-v8a/libapp.so"
    }))
}