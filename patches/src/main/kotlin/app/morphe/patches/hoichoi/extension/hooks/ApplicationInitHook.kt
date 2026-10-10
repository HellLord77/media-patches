package app.morphe.patches.hoichoi.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook

internal val hoichoiActivityOnCreateHook =
    activityOnCreateExtensionHook(
        activityClassType = "Lcom/viewlift/hoichoi/HoichoiDashboardActivity;",
        targetBundleMethod = true,
    )

internal val hoichoiApplicationOnCreateHook = activityOnCreateExtensionHook(
    activityClassType = "Lcom/viewlift/hoichoi/HoichoiApplication;",
    targetBundleMethod = false,
)
