package app.morphe.patches.chorkitv.content

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.fix.changepackageinstaller.changePackageInstallerPatch
import app.morphe.patches.all.misc.hex.hexPatch
import app.morphe.patches.all.misc.stripnativelibraries.stripNonArmNativeLibraryPatch
import app.morphe.patches.chorkitv.shared.Constants.COMPATIBILITY_CHORKITV
import app.morphe.patches.shared.requireArm

@Suppress("unused")
val contentAccessPatch = rawResourcePatch(
    name = "Content access",
    description = "Resolve content_access to ContentAccess.free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CHORKITV)

    availability(requireArm)

    dependsOn(
        stripNonArmNativeLibraryPatch, changePackageInstallerPatch(), hexPatch(true, block = {
            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase_or_subscription -> ContentAccess.free
            // ADD             R0, R5, #0x5000
            // LDR             R0, [R0,#0xCF]  -> LDR             R0, [R0,#0xE3]
            """
                05 0a 85 e2
                cf 00 90 e5
            """ asPatternTo """
                05 0a 85 e2
                e3 00 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"

            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.subscription -> ContentAccess.free
            // ADD             R0, R5, #0x5000
            // LDR             R0, [R0,#0xD7]  -> LDR             R0, [R0,#0xE3]
            """
                05 0a 85 e2
                d7 00 90 e5
            """ asPatternTo """
                05 0a 85 e2
                e3 00 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"

            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase -> ContentAccess.free
            // ADD             R0, R5, #0x5000
            // LDR             R0, [R0,#0xDF]  -> LDR             R0, [R0,#0xE3]
            """
                05 0a 85 e2
                df 00 90 e5
            """ asPatternTo """
                05 0a 85 e2
                e3 00 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"
        }), hexPatch(true, block = {
            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase_or_subscription -> ContentAccess.free
            // ADD             X0, X27, #8,LSL#12
            // LDR             X0, [X0,#0xCE8]    -> LDR             X0, [X0,#0xD10]
            """
                60 23 40 91
                00 74 46 f9
            """ asPatternTo """
                60 23 40 91
                00 88 46 f9
            """ inFile "lib/arm64-v8a/libapp.so"

            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.subscription -> ContentAccess.free
            // ADD             X0, X27, #8,LSL#12
            // LDR             X0, [X0,#0xCF8]    -> LDR             X0, [X0,#0xD10]
            """
                60 23 40 91
                00 7c 46 f9
            """ asPatternTo """
                60 23 40 91
                00 88 46 f9
            """ inFile "lib/arm64-v8a/libapp.so"

            // package:goplay_tv/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase -> ContentAccess.free
            // ADD             X0, X27, #8,LSL#12
            // LDR             X0, [X0,#0xD08]    -> LDR             X0, [X0,#0xD10]
            """
                60 23 40 91
                00 84 46 f9
            """ asPatternTo """
                60 23 40 91
                00 88 46 f9
            """ inFile "lib/arm64-v8a/libapp.so"
        })
    )
}