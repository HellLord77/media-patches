package app.morphe.patches.epicon.content

import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON_TV
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.data.model.content.Content
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.model.content.Content as ContentTv

@Suppress("unused")
val freeContent =
    freeContentPatch(
        name = "Free content",
        compatibility = COMPATIBILITY_EPICON,
        Content.GetFree_PremiumFingerprint,
        Content.Is_PremiumFingerprint,
    )

@Suppress("unused")
val freeContentTv =
    freeContentPatch(
        name = "Free content (TV)",
        compatibility = COMPATIBILITY_EPICON_TV,
        ContentTv.GetFree_PremiumFingerprint,
        ContentTv.Is_PremiumFingerprint,
    )
