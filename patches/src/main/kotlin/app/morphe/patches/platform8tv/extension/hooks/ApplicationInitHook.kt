package app.morphe.patches.platform8tv.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.platform8tv.shared.morphe.digivive.platform8.androidtv.application.NexgTvApplication
import app.morphe.patches.platform8tv.shared.morphe.digivive.platform8.androidtv.home.Splash

internal val platform8tvActivityOnCreateHook =
    activityOnCreateExtensionHook(Splash.toString(), true)

internal val platform8tvApplicationOnCreateHook =
    activityOnCreateExtensionHook(NexgTvApplication.toString(), false)
