package app.morphe.patches.chorki.content

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.fix.changepackageinstaller.changePackageInstallerPatch
import app.morphe.patches.all.misc.hex.hexPatch
import app.morphe.patches.all.misc.stripnativelibraries.stripNonArmNativeLibraryPatch
import app.morphe.patches.chorki.shared.Constants.COMPATIBILITY_CHORKI
import app.morphe.patches.shared.requireArm

@Suppress("unused")
val contentAccessPatch = rawResourcePatch(
    name = "Content access",
    description = "Resolve content_access to ContentAccess.free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CHORKI)

    availability(requireArm)

    dependsOn(
        stripNonArmNativeLibraryPatch, changePackageInstallerPatch(), hexPatch(true, block = {
            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase_or_subscription -> ContentAccess.free
            // ADD             R0, R5, #0xB000 -> ADD             R0, R5, #0x8000
            // LDR             R0, [R0,#0x77B] -> LDR             R0, [R0,#0xC87]
            """
                0b 0a 85 e2
                7b 07 90 e5
            """ asPatternTo """
                02 09 85 e2
                87 0c 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"

            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.subscription -> ContentAccess.free
            // ADD             R0, R5, #0xB000 -> ADD             R0, R5, #0x8000
            // LDR             R0, [R0,#0x77F] -> LDR             R0, [R0,#0xC87]
            """
                0b 0a 85 e2
                7f 07 90 e5
            """ asPatternTo """
                02 09 85 e2
                87 0c 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"

            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase -> ContentAccess.free
            // ADD             R0, R5, #0xB000 -> ADD             R0, R5, #0x8000
            // LDR             R0, [R0,#0x787] -> LDR             R0, [R0,#0xC87]
            """
                0b 0a 85 e2
                87 07 90 e5
            """ asPatternTo """
                02 09 85 e2
                87 0c 90 e5
            """ inFile "lib/armeabi-v7a/libapp.so"
        }), hexPatch(true, block = {
            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase_or_subscription -> ContentAccess.free
            // ADD             X0, X27, #0x15,LSL#12 -> ADD             X0, X27, #0x10,LSL#12
            // LDR             X0, [X0,#0x848]       -> LDR             X0, [X0,#0x2F0]
            """
                60 57 40 91
                00 24 44 f9
            """ asPatternTo """
                60 43 40 91
                00 78 41 f9
            """ inFile "lib/arm64-v8a/libapp.so"

            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.subscription -> ContentAccess.free
            // ADD             X0, X27, #0x15,LSL#12 -> ADD             X0, X27, #0x10,LSL#12
            // LDR             X0, [X0,#0x850]       -> LDR             X0, [X0,#0x2F0]
            """
                60 57 40 91
                00 28 44 f9
            """ asPatternTo """
                60 43 40 91
                00 78 41 f9
            """ inFile "lib/arm64-v8a/libapp.so"

            // package:chorki/data/models/content_model.dart -> ContentModel.toEntity
            // ContentAccess.purchase -> ContentAccess.free
            // ADD             X0, X27, #0x15,LSL#12 -> ADD             X0, X27, #0x10,LSL#12
            // LDR             X0, [X0,#0x860]       -> LDR             X0, [X0,#0x2F0]
            """
                60 57 40 91
                00 30 44 f9
            """ asPatternTo """
                60 43 40 91
                00 78 41 f9
            """ inFile "lib/arm64-v8a/libapp.so"
        })
    )
}