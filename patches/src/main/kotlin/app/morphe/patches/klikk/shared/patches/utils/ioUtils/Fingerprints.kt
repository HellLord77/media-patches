package app.morphe.patches.klikk.shared.patches.utils.ioUtils

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object IsUserLoggedInFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/utils/IOUtils;",
    name = "isUserLoggedIn",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

private object HasValidSubscriptionContextFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/utils/IOUtils;",
    name = "hasValidSubscription",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

private object HasValidSubscriptionStringFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/utils/IOUtils;",
    name = "hasValidSubscription",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(String::class.java.descriptorString()),
)

internal val HasValidSubscriptionFingerprints =
    listOf(HasValidSubscriptionContextFingerprint, HasValidSubscriptionStringFingerprint)

internal object IsVideoWithinValidityPeriodFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/utils/IOUtils;",
    name = "isVideoWithinValidityPeriod",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Lcom/angel/klikk/Database/DownloadEntity;"),
)

internal object ValidateVideoFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/utils/IOUtils;",
    name = "validateVideo",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(
        "Lcom/brightcove/player/model/Video;",
        "Landroid/content/Context;",
        "Lcom/angel/klikk/Database/Repository;"
    ),
)