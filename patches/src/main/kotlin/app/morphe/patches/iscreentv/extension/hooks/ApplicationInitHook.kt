package app.morphe.patches.iscreentv.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.iscreentv.shared.morphe.com.rockstreamer.iscreentv.activity.SplashActivity
import app.morphe.patches.iscreentv.shared.morphe.com.rockstreamer.iscreentv.utils.App

internal val iscreentvActivityOnCreateHook =
    activityOnCreateExtensionHook(SplashActivity.toString(), true)

internal val iscreentvApplicationOnCreateHook = activityOnCreateExtensionHook(App.toString(), false)
