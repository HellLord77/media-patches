package app.morphe.patches.bongobd.content

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.bongobd.shared.Constants.COMPATIBILITY_BONGO
import app.morphe.patches.bongobd.shared.moprhe.com.bongo.bongobd.view.model.pages.PageModelExtensionsKt
import app.morphe.util.matchSingle
import app.morphe.util.returnEarly

@Suppress("unused")
val freeContentPatch =
    bytecodePatch(
        name = "Free content",
        description = "Marks content as free.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_BONGO)

        execute {
            PageModelExtensionsKt.IsPaidFingerprint
                .matchSingle()
                .method
                .returnEarly(false)
        }
    }
