package app.morphe.patches.kabbik.extension.hooks

import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.kabbik.shared.morphe.com.kabbik.app.KabbikApplication
import app.morphe.patches.kabbik.shared.morphe.com.kabbik.app.MainActivity

internal val kabbikActivityOnCreateHook =
    activityOnCreateExtensionHook(MainActivity.toString(), true)

internal val kabbikApplicationOnCreateHook =
    activityOnCreateExtensionHook(KabbikApplication.toString(), false)
