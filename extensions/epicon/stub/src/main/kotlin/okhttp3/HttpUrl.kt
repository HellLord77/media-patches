package okhttp3

@Suppress("unused")
class HttpUrl(
    val scheme: String,
    val username: String,
    val password: String,
    val host: String,
    val port: Int,
    val pathSegments: ArrayList<String>,
    val queryNamesAndValues: ArrayList<String?>?,
    val fragment: String?,
    private val url: String,
)
