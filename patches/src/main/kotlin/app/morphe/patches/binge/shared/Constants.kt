package app.morphe.patches.binge.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_BINGE =
        Compatibility(
            name = "Binge",
            packageName = "buzz.binge.mobile",
            apkFileType = ApkFileType.APKM,
            appIconColor = 0xF21D2F,
            signatures = setOf("0a473ad54e156d6e06ad56a2e21af9a2b0658fd843f0df6c4c110698d954d0d9"),
            targets =
                listOf(
                    AppTarget(
                        version = "16.0.5",
                        versionCode = 167,
                        isExperimental = true,
                        minSdk = 23,
                    ),
                ),
        )
}
