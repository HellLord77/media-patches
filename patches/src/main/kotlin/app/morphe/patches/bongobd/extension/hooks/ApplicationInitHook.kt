package app.morphe.patches.bongobd.extension.hooks

import app.morphe.patcher.Fingerprint
import app.morphe.patches.all.misc.extension.ExtensionHook
import app.morphe.patches.all.misc.extension.activityOnCreateExtensionHook
import app.morphe.patches.shared.Type
import com.android.tools.smali.dexlib2.AccessFlags


internal object BongobdActivityOnCreateFingerprint : Fingerprint(
    definingClass = "Lcom/bongo/ottandroidbuildvariant/splash/view/SplashActivity;",
    name = "onCreate",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = Type.void,
    parameters = listOf(Type.BUNDLE)
)

internal val bongobdActivityOnCreateHook = ExtensionHook(BongobdActivityOnCreateFingerprint)

internal val bongobdApplicationOnCreateHook = activityOnCreateExtensionHook(
    activityClassType = "Lcom/bongo/ottandroidbuildvariant/MainApplication;"
)
