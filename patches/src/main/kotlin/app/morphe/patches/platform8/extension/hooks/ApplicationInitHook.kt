package app.morphe.patches.platform8.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.platform8.shared.morphe.com.digivive.nexgtv.activities.SplashActivity
import app.morphe.patches.platform8.shared.morphe.com.digivive.nexgtv.application.NexgTvApplication

internal val platform8ActivityOnCreateHook =
    activityOnCreateExtensionHook(SplashActivity.toString(), true)

internal val platform8ApplicationOnCreateHook =
    activityOnCreateExtensionHook(NexgTvApplication.toString(), false)
