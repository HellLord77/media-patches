package okhttp3

class Request {
    class Builder {
        @JvmField
        var url: HttpUrl? = null

        fun build(): Request = throw NotImplementedError()
    }
}
