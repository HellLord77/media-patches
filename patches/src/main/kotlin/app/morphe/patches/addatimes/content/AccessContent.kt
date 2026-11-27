package app.morphe.patches.addatimes.content

import app.morphe.patches.addatimes.extension.sharedExtensionPatch
import app.morphe.patches.addatimes.extension.sharedExtensionPatchTv
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES_TV

@Suppress("unused")
val accessContent =
    accessContentPatch("Access content", COMPATIBILITY_ADDATIMES, sharedExtensionPatch)

@Suppress("unused")
val accessContentTv =
    accessContentPatch("Access content (TV)", COMPATIBILITY_ADDATIMES_TV, sharedExtensionPatchTv)
