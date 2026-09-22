package app.morphe.patches.chorki

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.fix.changepackageinstaller.changePackageInstallerPatch
import app.morphe.patches.chorki.shared.Constants.COMPATIBILITY_CHORKI

@Suppress("unused")
val streamPatch = rawResourcePatch(
    name = "Stream paid content",
    description = "Stream movies and series for free.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CHORKI)

    dependsOn(changePackageInstallerPatch())

    execute {
        val lib = get("lib/arm64-v8a/libapp.so")
        val bytes = lib.readBytes()

        val yes = 0x82.toByte()
        val no = 0xC2.toByte()

        val canAccessContent = 0x006B9039
        val isUserSubscribed = 0x006B9459
        val hasReachedStreamingLimit = 0x00891559
        val hasSubscriptionRestriction = 0x00892ADD
        val hasSubscriptionRestriction0 = 0x00892AED
        val isLoginRequired = 0x00893C31

        require(bytes[canAccessContent] == no)
        require(bytes[isUserSubscribed] == no)
        require(bytes[hasReachedStreamingLimit] == yes)
        require(bytes[hasSubscriptionRestriction] == yes)
        require(bytes[hasSubscriptionRestriction0] == yes)
        require(bytes[isLoginRequired] == yes)

        bytes[canAccessContent] = yes
        // bytes[isUserSubscribed] = yes
        // bytes[hasReachedStreamingLimit] = no
        // bytes[hasSubscriptionRestriction] = no
        // bytes[hasSubscriptionRestriction0] = no
        bytes[isLoginRequired] = no

        lib.writeBytes(bytes)
    }
}
