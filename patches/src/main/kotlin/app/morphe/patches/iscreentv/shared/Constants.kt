package app.morphe.patches.iscreentv.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_ISCREENTV = Compatibility(
        name = "IScreen Tv",
        packageName = "com.rockstreamer.iscreentv",
        apkFileType = ApkFileType.XAPK,
        appIconColor = 0XE50019,
        signatures = setOf("b4fd5f9c2fb15414d0f909a66e511c1b40f4ef541c23e9c0077554b8c29505ad"),
        targets = listOf(
            AppTarget(version = "4.4.4", versionCode = 472, minSdk = 23),
        ),
    )
}
