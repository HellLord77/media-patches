package app.morphe.patches.all.pairip.strip

import app.morphe.patcher.patch.rawResourcePatch

@Suppress("unused")
fun stripPairIP() = rawResourcePatch(
    name = "Strip PairIP libraries",
    description = "Strips Play Integrity API (pairip) client-side libraries.",
    default = false,
) {
    throw NotImplementedError()
}