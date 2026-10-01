package app.morphe.patches.klikk.shared.patches.ui.activity.newDetailsAndDownloadActivity

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal object DeviceCheckGetterFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            definingClass = "Lcom/angel/klikk/ui/activity/NewDetailsAndDownloadActivity;",
            name = "DeviceCheck",
            type = "Z",
            opcode = Opcode.IGET_BOOLEAN,
        )
    )
)

internal object IsDownloadLimitExceededFingerprint : Fingerprint(
    definingClass = "Lcom/angel/klikk/ui/activity/NewDetailsAndDownloadActivity;",
    name = "isDownloadLimitExceeded",
    accessFlags = listOf(AccessFlags.PRIVATE, AccessFlags.FINAL),
    returnType = "Z",
    parameters = emptyList(),
)