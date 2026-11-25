package app.morphe.patches.addatimes.video

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES
import app.morphe.patches.addatimes.shared.Constants.COMPATIBILITY_ADDATIMES_TV
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.dash.DashMediaSource
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.source.MediaSource
import app.morphe.patches.shared.patches.dex.renameClassPatch

@Suppress("unused")
val defaultSourcePatch =
    bytecodePatch(
        name = "Default source",
        description = "Use default media source for video player.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_ADDATIMES, COMPATIBILITY_ADDATIMES_TV)

        dependsOn(
            renameClassPatch(
                mapOf(
                    DashMediaSource.toString() to MediaSource.toString(),
                    DashMediaSource.Factory.toString() to DefaultMediaSourceFactory.toString(),
                ),
            ) { classDef -> classDef.type.startsWith("Lcom/addatimes/addatimes/ui/") },
        )
    }
