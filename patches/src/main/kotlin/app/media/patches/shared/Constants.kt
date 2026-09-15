package app.media.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_SHADHIN = Compatibility(
        name = "Shadhin",
        packageName = "com.gm.shadhin",
        apkFileType = ApkFileType.XAPK,
        appIconColor = 0x00B0FF,
        signatures = setOf("3257d599a49d2c961a471ca9843f59d341a405884583fc087df4237b733bbd6d"),
        targets = listOf(
            AppTarget(version = "4.4.4", versionCode = 444, minSdk = 23),
            AppTarget(version = "4.4.2", versionCode = 442, minSdk = 23),
            AppTarget(version = "4.4.1", versionCode = 441, minSdk = 23),
            AppTarget(version = "4.3.9", versionCode = 439, minSdk = 23),
            AppTarget(version = "4.3.7", versionCode = 437, minSdk = 21),
            AppTarget(version = "4.3.5", versionCode = 435, minSdk = 21),
            AppTarget(version = "4.3.4", versionCode = 434, minSdk = 21),
        ),
    )
}
