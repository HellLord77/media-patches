package app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.model.content

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.morphe.java.lang.Boolean
import com.android.tools.smali.dexlib2.AccessFlags

object Season {
    override fun toString() = "Lcom/epicchannel/epicon/model/content/Season;"

    @Suppress("ktlint:standard:class-naming")
    object GetFree_PremiumFingerprint : Fingerprint(
        definingClass = Season.toString(),
        name = "getFree_premium",
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
        returnType = Boolean.toString(),
        parameters = emptyList(),
    )
}
