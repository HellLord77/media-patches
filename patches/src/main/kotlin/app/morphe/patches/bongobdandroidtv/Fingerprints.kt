package app.morphe.patches.bongobdandroidtv

import app.morphe.patcher.Fingerprint

object GetVideoDetailsDataFingerprint : Fingerprint(
    definingClass = "Lsaas/ott/smarttv/ui/details/data/DetailsEndPoint;",
    name = "getVideoDetailsData",
    returnType = "Lretrofit2/Call;",
    parameters = listOf("Ljava/lang/String;"),
)