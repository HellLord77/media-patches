package app.morphe.patches.epicon.extension.hooks

import app.morphe.patcher.Fingerprint
import app.morphe.patches.all.misc.extension.ExtensionHook
import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.utils.MyApplication
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.android.os.Bundle

internal val epiconActivityOnCreateHook =
    ExtensionHook(
        Fingerprint(
            name = "onCreate",
            definingClass = "Lcom/epicchannel/epicon/ui/launcher/activity/",
            returnType = Type.void,
            parameters = listOf(Bundle.toString()),
        ),
    )

internal val epiconApplicationOnCreateHook =
    activityOnCreateExtensionHook(MyApplication.toString(), false)
