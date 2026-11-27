package app.morphe.patches.epicon.shared

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.android.os.Bundle
import com.android.tools.smali.dexlib2.AccessFlags

object XposedBridgeFingerprint :
    Fingerprint(
        definingClass = "Lcom/epicchannel/epicon/utils/base/",
        name = "onCreate",
        accessFlags = listOf(AccessFlags.PUBLIC),
        returnType = Type.void,
        parameters = listOf(Bundle.toString()),
        filters = listOf(string("/system/framework/XposedBridge.jar")),
    )
