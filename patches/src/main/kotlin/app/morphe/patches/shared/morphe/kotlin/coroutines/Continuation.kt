package app.morphe.patches.shared.morphe.kotlin.coroutines

import app.morphe.patcher.Fingerprint
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.morphe.java.lang.Object
import com.android.tools.smali.dexlib2.AccessFlags
import kotlin.coroutines.Continuation

object Continuation {
    override fun toString(): String = Continuation::class.java.descriptorString()

    object ResumeWithFingerprint : Fingerprint(
        definingClass = Continuation::class.java.descriptorString(),
        name = "resumeWith",
        accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
        returnType = Type.void,
        parameters = listOf(Object.toString()),
    )
}
