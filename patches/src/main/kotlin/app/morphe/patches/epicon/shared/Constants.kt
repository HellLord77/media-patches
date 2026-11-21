package app.morphe.patches.epicon.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_EPICON =
        Compatibility(
            name = "EPIC ON",
            packageName = "com.epicchannel.epicon",
            apkFileType = ApkFileType.APKM,
            appIconColor = 0x6F048C,
            signatures = setOf("ee0d9ea1ab9d9225ae5b150c1dbed8827eaa2742f5c39b43717abf6041bcc17d"),
            targets =
                listOf(
                    AppTarget(version = "4.0.24", versionCode = 229, minSdk = 24),
                    AppTarget(version = "4.0.22", versionCode = 227, minSdk = 24),
                ),
        )
}
