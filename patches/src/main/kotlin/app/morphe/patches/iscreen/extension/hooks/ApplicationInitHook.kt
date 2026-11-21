package app.morphe.patches.iscreen.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.iscreen.shared.morphe.com.rockstreamer.iscreen.App
import app.morphe.patches.iscreen.shared.morphe.com.rockstreamer.iscreen.activities.SplashScreen

internal val iscreenActivityOnCreateHook =
    activityOnCreateExtensionHook(SplashScreen.toString(), true)

internal val iscreenApplicationOnCreateHook = activityOnCreateExtensionHook(App.toString(), false)
