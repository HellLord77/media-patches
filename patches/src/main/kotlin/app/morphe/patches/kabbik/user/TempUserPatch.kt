package app.morphe.patches.kabbik.user

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.misc.fix.changepackageinstaller.changePackageInstallerPatch
import app.morphe.patches.kabbik.extension.sharedExtensionPatch
import app.morphe.patches.kabbik.shared.Constants.COMPATIBILITY_KABBIK

@Suppress("unused")
val tempUserPatch = bytecodePatch(
    name = "Temp user",
    description = "Log in as subscribed temp user.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KABBIK)

    dependsOn(sharedExtensionPatch, changePackageInstallerPatch())

    execute {
        // TODO hook onCreate to add temp user data
    }
}