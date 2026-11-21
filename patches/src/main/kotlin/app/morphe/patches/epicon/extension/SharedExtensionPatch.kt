package app.morphe.patches.epicon.extension

import app.morphe.patches.all.misc.extension.sharedExtensionPatch
import app.morphe.patches.epicon.extension.hooks.epiconActivityOnCreateHook
import app.morphe.patches.epicon.extension.hooks.epiconApplicationOnCreateHook

val sharedExtensionPatch =
    sharedExtensionPatch(
        listOf("epicon"),
        epiconActivityOnCreateHook,
        epiconApplicationOnCreateHook,
    )
