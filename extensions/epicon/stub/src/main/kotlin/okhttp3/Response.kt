package okhttp3

class Response {
    @JvmField
    val body: ResponseBody? = null

    fun isSuccessful(): Boolean = throw NotImplementedError()

    class Builder {
        @JvmField
        var request: Request? = null

        @JvmField
        var protocol: Protocol? = null

        @JvmField
        var code = -1

        @JvmField
        var message: String? = null

        fun build(): Response = throw NotImplementedError()
    }
}
