package app.morphe.patches.addatimes.user

import app.morphe.patches.addatimes.extension.sharedExtensionPatch
import app.morphe.patches.addatimes.extension.sharedExtensionPatchTv
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES_TV
import app.morphe.patches.addatimes.shared.morphe.com.addatimes.addatimes.AddaTimesApp
import app.morphe.patches.addatimes.shared.morphe.com.addatimes.addatimes.Hilt_AddatimesTvApp

@Suppress("unused")
val anonymousUser =
    anonymousUserPatch(
        "Anonymous user",
        COMPATIBILITY_ADDATIMES,
        sharedExtensionPatch,
        AddaTimesApp.OnCreateFingerprint,
    )

@Suppress("unused")
val anonymousUserTv =
    anonymousUserPatch(
        "Anonymous user (TV)",
        COMPATIBILITY_ADDATIMES_TV,
        sharedExtensionPatchTv,
        Hilt_AddatimesTvApp.OnCreateFingerprint,
    )
