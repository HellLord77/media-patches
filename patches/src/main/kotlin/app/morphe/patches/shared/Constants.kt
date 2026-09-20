package app.morphe.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_BONGO = Compatibility(
        name = "Bongobd",
        packageName = "com.bongo.bongobd",
        apkFileType = ApkFileType.XAPK,
        appIconColor = 0XD8062D,
        signatures = setOf("29ccbd820d40b90e7ab5206b20bcafa538508f842d21890aca39df9ee5aec9c6"),
        targets = listOf(
            AppTarget(version = "6.9.2", versionCode = 210060902, minSdk = 21)
        )
    )

    val COMPATIBILITY_SHADHIN = Compatibility(
        name = "Shadhin",
        packageName = "com.gm.shadhin",
        apkFileType = ApkFileType.XAPK,
        appIconColor = 0x00B0FF,
        signatures = setOf("ad22e167e88efc585f295a1f604d5c85fceb26bc501b1edb693bfe95b206eaf0"),
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
