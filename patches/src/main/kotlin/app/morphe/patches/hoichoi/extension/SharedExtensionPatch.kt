package app.morphe.patches.hoichoi.extension

import app.morphe.patches.all.misc.extension.sharedExtensionPatch
import app.morphe.patches.hoichoi.extension.hooks.hoichoiActivityOnCreateHook
import app.morphe.patches.hoichoi.extension.hooks.hoichoiApplicationOnCreateHook

val sharedExtensionPatch = sharedExtensionPatch(
    listOf("hoichoi"),
    hoichoiActivityOnCreateHook,
    hoichoiApplicationOnCreateHook
)