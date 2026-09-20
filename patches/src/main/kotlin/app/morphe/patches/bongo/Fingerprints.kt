package app.morphe.patches.bongo

import app.morphe.patcher.Fingerprint

object ContentDetailsGetterFingerprint : Fingerprint(
    definingClass = "Lcom/bongo/bongobd/view/network/ApiServiceSaas;",
    name = "getContentDetails",
    returnType = "Ljava/lang/Object;",
    parameters = listOf("Ljava/lang/String;", "Lkotlin/coroutines/Continuation;"),
)