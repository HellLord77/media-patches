package app.morphe.patches.epicon.content

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.data.model.content.Content
import app.morphe.util.matchSingle
import app.morphe.util.returnBoxedBooleanEarly

@Suppress("unused")
val freeContentPatch =
    bytecodePatch(
        name = "Free content",
        description = "Marks content as free.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON)

        execute {
            Content.GetFree_PremiumFingerprint
                .matchSingle()
                .method
                .returnBoxedBooleanEarly(false)

            Content.Is_PremiumFingerprint
                .matchSingle()
                .method
                .returnBoxedBooleanEarly(false)
        }
    }
