package app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.data.model.search

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.morphe.java.lang.Boolean
import com.android.tools.smali.dexlib2.AccessFlags

object RecentSearch {
    override fun toString() = "Lcom/epicchannel/epicon/data/model/search/RecentSearch;"

    object GetFreePremiumFingerprint : Fingerprint(
        definingClass = RecentSearch.toString(),
        name = "getFree_premium",
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
        returnType = Boolean.toString(),
        parameters = emptyList(),
    )
}
