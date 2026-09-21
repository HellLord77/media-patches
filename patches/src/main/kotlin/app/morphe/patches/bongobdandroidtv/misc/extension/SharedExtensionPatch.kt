package app.morphe.patches.bongobdandroidtv.misc.extension

import app.morphe.patches.all.misc.extension.sharedExtensionPatch
import app.morphe.patches.bongobdandroidtv.misc.extension.hooks.bongobdandroidtvActivityOnCreateHook
import app.morphe.patches.bongobdandroidtv.misc.extension.hooks.bongobdandroidtvApplicationOnCreateHook

val sharedExtensionPatch = sharedExtensionPatch(
    listOf("bongo"),
    bongobdandroidtvActivityOnCreateHook,
    bongobdandroidtvApplicationOnCreateHook
)