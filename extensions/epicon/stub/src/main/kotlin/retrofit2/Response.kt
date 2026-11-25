package retrofit2

import okhttp3.Response
import okhttp3.internal.http.RealResponseBody

@Suppress("unused")
class Response<T>(
    private val rawResponse: Response,
    private val body: T?,
    private val errorBody: RealResponseBody?,
) {
    fun raw(): Response = throw NotImplementedError()

    @Suppress("RedundantNullableReturnType")
    fun body(): T? = throw NotImplementedError()
}
