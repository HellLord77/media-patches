package app.media.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_SHADHIN = Compatibility(
        name = "Shadhin",
        packageName = "com.gm.shadhin",
        apkFileType = ApkFileType.APK,
        appIconColor = 0x00B0FF,
        targets = listOf(
            AppTarget(version = "4.4.4", versionCode = 444),
        ),
    )
}
