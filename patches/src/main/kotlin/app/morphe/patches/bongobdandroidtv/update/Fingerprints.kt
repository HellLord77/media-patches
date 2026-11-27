package app.morphe.patches.bongobdandroidtv.update

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patches.bongobdandroidtv.shared.morphe.saas.ott.smarttv.ui.splash.model.VersionCheckResponseModel
import app.morphe.patches.shared.Type
import com.android.tools.smali.dexlib2.Opcode

object ForceUpdateFieldAccessFingerprint :
    Fingerprint(
        filters =
            listOf(
                fieldAccess(
                    definingClass = VersionCheckResponseModel.toString(),
                    name = "force_update",
                    type = Type.boolean,
                    opcode = Opcode.IGET_BOOLEAN,
                ),
            ),
    )
