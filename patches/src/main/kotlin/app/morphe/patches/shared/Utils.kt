package app.morphe.patches.shared

import app.morphe.util.p0Register
import com.android.tools.smali.dexlib2.iface.Method

fun Method.getRegisterName(register: Int): String {
    val firstParameterRegister = if (implementation != null) p0Register else 0

    return if (register >= firstParameterRegister) {
        "p${register - firstParameterRegister}"
    } else {
        "v$register"
    }
}