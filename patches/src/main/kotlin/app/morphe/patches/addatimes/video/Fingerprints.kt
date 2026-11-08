package app.morphe.patches.addatimes.video

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.newInstance
import app.morphe.patcher.opcode
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.androidx.media3.common.MediaItem
import app.morphe.patches.shared.morphe.androidx.media3.datasource.DataSource
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.dash.DashMediaSource
import app.morphe.patches.shared.morphe.androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import app.morphe.patches.shared.morphe.java.lang.Object
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object InvokeFingerprint :
    Fingerprint(
        definingClass = "Lcom/addatimes/addatimes/ui/activity/",
        name = "invoke",
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
        returnType = Object.toString(),
        parameters = listOf(Object.toString()),
        filters =
            listOf(
                newInstance(DashMediaSource.Factory.toString()),
                methodCall(
                    definingClass = DashMediaSource.Factory.toString(),
                    name = "<init>",
                    parameters = listOf(DataSource.Factory.toString()),
                    returnType = Type.void,
                    opcode = Opcode.INVOKE_DIRECT,
                ),
                methodCall(
                    definingClass = DashMediaSource.Factory.toString(),
                    name = "setDrmSessionManagerProvider",
                    parameters = listOf(DrmSessionManagerProvider.toString()),
                    returnType = DashMediaSource.Factory.toString(),
                    opcode = Opcode.INVOKE_VIRTUAL,
                ),
                methodCall(
                    definingClass = DashMediaSource.Factory.toString(),
                    name = "createMediaSource",
                    parameters = listOf(MediaItem.toString()),
                    returnType = DashMediaSource.toString(),
                    opcode = Opcode.INVOKE_VIRTUAL,
                ),
            ),
    )
