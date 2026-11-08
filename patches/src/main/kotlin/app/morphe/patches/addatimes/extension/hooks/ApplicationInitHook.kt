package app.morphe.patches.addatimes.extension.hooks

import app.morphe.patches.addatimes.shared.morphe.com.addatimes.addatimes.AddaTimesApp
import app.morphe.patches.addatimes.shared.morphe.com.addatimes.addatimes.base.BaseActivity
import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook

internal val addatimesActivityOnCreateHook =
    activityOnCreateExtensionHook(
        activityClassType = BaseActivity.toString(),
        targetBundleMethod = true,
    )

internal val addatimesApplicationOnCreateHook =
    activityOnCreateExtensionHook(
        activityClassType = AddaTimesApp.toString(),
        targetBundleMethod = false,
    )
