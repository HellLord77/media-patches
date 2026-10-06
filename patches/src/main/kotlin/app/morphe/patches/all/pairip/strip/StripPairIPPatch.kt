package app.morphe.patches.all.pairip.strip

import app.morphe.patcher.patch.bytecodePatch

fun stripPairIPPatch(appName: String) = bytecodePatch {
    dependsOn(
        restoreDexPatch(),
        restoreFieldPatch("/pairip/${appName}.json"),
        stripLibrariesPatch,
    )
}