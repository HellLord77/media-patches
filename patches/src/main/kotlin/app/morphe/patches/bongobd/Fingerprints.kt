package app.morphe.patches.bongobd

import app.morphe.patcher.Fingerprint

object GetContentDetailsFingerprint : Fingerprint(
    definingClass = "Lcom/bongo/bongobd/view/network/ApiServiceSaas;",
    name = "getContentDetails",
    returnType = "Ljava/lang/Object;",
    parameters = listOf("Ljava/lang/String;", "Lkotlin/coroutines/Continuation;"),
)