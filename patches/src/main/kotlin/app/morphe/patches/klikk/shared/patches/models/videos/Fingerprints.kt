package app.morphe.patches.klikk.shared.patches.models.videos

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object IsPaidFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/models/Videos;",
    name = "isPaid",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Ljava/lang/Boolean;",
    parameters = emptyList(),
)

internal object IsSubscribedFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/models/Videos;",
    name = "isSubscribed",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Ljava/lang/Boolean;",
    parameters = emptyList(),
)