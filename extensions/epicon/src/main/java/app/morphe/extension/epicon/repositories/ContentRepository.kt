package app.morphe.extension.epicon.repositories

import app.morphe.extension.shared.extension.org.json.objects
import com.goebl.david.Webb
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.Protocol
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import okhttp3.Response as OkHttp3Response
import retrofit2.Response as Retrofit2Response

internal class ContentRepository(
    private val contentId: String,
) {
    private val trailersAndMore: JSONObject by lazy {
        WEBB
            .post("https://contentapiprod-njsapi.epicon.in/index/pages/trailers-and-more")
            .param("content_id", contentId)
            .header("X-Access-Auth-Token", TOKEN)
            .ensureSuccess()
            .asJsonObject()
            .body
            .run {
                require(getBoolean("success"))
                getJSONObject("data")
            }
    }

    private val contentUrl: String by lazy {
        requireNotNull(
            trailersAndMore
                .getJSONArray("list")
                .objects()
                .flatMap { it.getJSONArray("content").objects() }
                .find { it.getInt("ID").toString() == contentId },
        ).getString("url").split('/', limit = 3).take(2).joinToString("/")
    }

    internal val episode: JSONObject by lazy {
        WEBB
            .get("https://contentapiprod-njsapi.epicon.in/index/pages/episode/$contentUrl")
            .header("X-Access-Auth-Token", TOKEN)
            .ensureSuccess()
            .asJsonObject()
            .body
            .run {
                require(getBoolean("success"))
                getJSONObject("content")
            }
    }

    private val urls: Map<Int, JSONObject> by lazy {
        val urls = mutableListOf(episode)
        urls.addAll(
            (episode.optJSONArray("season") ?: JSONArray())
                .objects()
                .onEach { urls.add(it) }
                .flatMap { it.getJSONArray("episodes").objects() },
        )

        urls.associateBy { it.getInt("ID") }.also {
            it.values.forEach { url ->
                url.apply {
                    put("content_id", remove("ID"))
                    put("userDuration", remove("user_duration"))
                    put("skip_start_time", remove("intro_start_time"))
                    put("skip_end_time", remove("intro_end_time"))

                    val videoUrl = getString("video_url")
                    if ("://" !in videoUrl) {
                        put("video_url", "https://source.epicon.in/$videoUrl")
                    }
                }
            }
        }
    }

    internal fun <T> getResponse(
        showUrlResponse: T?,
        classOfT: Class<T>,
    ): Retrofit2Response<T> {
        showUrlResponse?.also {
            val oldUrls =
                JSONObject(GSON.toJson(it))
                    .getJSONArray("url")
                    .objects()
                    .associateBy { url -> url.getString("content_id").toInt() }

            urls.entries.forEach { (id, url) ->
                oldUrls[id]?.apply {
                    url
                        .put("subtitles", optJSONArray("subtitles"))
                        .put("promo_url", optString("promo_url"))

                    val videoUrl = optString("video_url")
                    if (videoUrl.isNotEmpty()) {
                        url.put("video_url", videoUrl)
                    }
                }
            }
        }
        val json = JSONObject().put("success", true).put("url", JSONArray(urls.values))

        val body = GSON.fromJson(json.toString(), classOfT)
        return runCatching {
            Retrofit2Response::class.java.getMethod("success", Any::class.java).let {
                { body: T? ->
                    @Suppress("UNCHECKED_CAST")
                    it.invoke(null, body) as Retrofit2Response<T>
                }
            }
        }.getOrDefault(::successResponse)(body)
    }

    private companion object {
        private const val TOKEN = "tkLkjzlYe876feNbkfbTeEVgkG9VgH7Y"

        private val GSON = Gson()
        private val WEBB = Webb.create()

        fun <T> successResponse(body: T?) =
            Retrofit2Response(
                OkHttp3Response
                    .Builder()
                    .request(
                        Request
                            .Builder()
                            .url(
                                HttpUrl(
                                    "http",
                                    "",
                                    "",
                                    "localhost",
                                    -1,
                                    arrayListOf(),
                                    null,
                                    null,
                                    "http://localhost/",
                                ),
                            ).build(),
                    ).protocol(Protocol.HTTP_1_1)
                    .code(HttpURLConnection.HTTP_OK)
                    .message("OK")
                    .build(),
                body,
                null,
            )
    }
}
