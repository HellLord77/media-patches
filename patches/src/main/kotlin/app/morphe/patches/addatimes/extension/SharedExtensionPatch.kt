package app.morphe.patches.addatimes.extension

import app.morphe.patches.addatimes.extension.hooks.addatimesActivityOnCreateHook
import app.morphe.patches.addatimes.extension.hooks.addatimesApplicationOnCreateHook
import app.morphe.patches.all.misc.extension.sharedExtensionPatch

val sharedExtensionPatch =
    sharedExtensionPatch(
        listOf("addatimes"),
        addatimesActivityOnCreateHook,
        addatimesApplicationOnCreateHook,
    )
