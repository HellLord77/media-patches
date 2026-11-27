package app.morphe.patches.epicon.video

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON_TV
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.hls.HlsMediaSource
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
        compatibleWith(COMPATIBILITY_EPICON_TV)

        dependsOn(
            renameClassPatch(
                mapOf(
                    HlsMediaSource.toString() to MediaSource.toString(),
                    HlsMediaSource.Factory.toString() to DefaultMediaSourceFactory.toString(),
                ),
            ) { classDef -> !classDef.type.startsWith("Landroidx/media3/exoplayer/") },
        )
    }
