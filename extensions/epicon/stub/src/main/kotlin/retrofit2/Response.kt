package retrofit2

import okhttp3.Response
import okhttp3.internal.http.RealResponseBody

@Suppress("unused")
class Response<T>(
    @JvmField val rawResponse: Response,
    @JvmField val body: T?,
    private val errorBody: RealResponseBody?,
)
