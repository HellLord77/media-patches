package app.morphe.extension.addatimes.repositories

import app.morphe.extension.shared.extension.org.json.objects
import app.morphe.extension.shared.extension.org.w3c.dom.iterator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Node
import java.net.HttpURLConnection
import java.net.URI
import javax.xml.parsers.DocumentBuilderFactory

internal class ContentRepository(
    private val chain: Interceptor.Chain,
    private val id: String,
    private val playVideo: JSONObject,
) {
    internal val movieDetails: JSONObject by lazy {
        getMovieDetails(id)
    }

    private val relatedMovieDetails: JSONObject by lazy {
        val related = movieDetails["related"] as JSONArray
        for (relatedMovie in related.objects()) {
            val relatedMovieDetails = getMovieDetails(relatedMovie["id"].toString())
            val relatedOfRelated = relatedMovieDetails["related"] as JSONArray
            for (relatedMovieOfRelated in relatedOfRelated.objects()) {
                if (id == relatedMovieOfRelated["id"].toString()) {
                    requireNotNull(relatedMovieOfRelated["download_video_file"])
                    return@lazy relatedMovieOfRelated
                }
            }
        }
        throw NotImplementedError()
    }

    private val listBucketKey: Node by lazy {
        val androidVideoFile = movieDetails["android_video_file"] as String
        val sha1 =
            V3_MPD_FILE_PATTERN
                .find(URI(androidVideoFile).path)!!
                .groups[1]!!
                .value

        val request =
            Request
                .Builder()
                .url(
                    "https://new-addatimes-processed-videos.s3.ap-south-1.amazonaws.com?max-keys=1&prefix=v2/${sha1}_",
                ).build()

        chain.proceed(request).use { response ->
            require(response.isSuccessful)
            DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(response.body.byteStream())
                .run {
                    for (node in getElementsByTagName("Key")) {
                        if (node.textContent.endsWith("_.m3u8")) {
                            return@lazy node
                        }
                    }
                }
        }
        throw NotImplementedError()
    }

    internal val downloadVideoFile: String by lazy {
        if (movieDetails["category_slug"] == "movie") {
            relatedMovieDetails["download_video_file"] as String
        } else {
            "https://cdn6.addatimes.com/${listBucketKey.textContent}"
        }
    }

    private fun getMovieDetails(movieId: String): JSONObject {
        val request = Request.Builder().url("https://app.addatimes.com/api/movie/$movieId").build()

        chain.proceed(request).use { response ->
            require(response.isSuccessful)
            JSONObject(response.body.string()).run {
                require(getBoolean("success"))
                return getJSONObject("data")
            }
        }
    }

    internal fun getResponse(response: Response): Response {
        movieDetails.put("video_url", downloadVideoFile)
        playVideo.put("success", true)
        playVideo.put("subscription_error", false)
        playVideo.put("data", movieDetails)

        val body = playVideo.toString().toResponseBody("application/json".toMediaType())
        return response
            .newBuilder()
            .code(HttpURLConnection.HTTP_OK)
            .header("Content-Length", body.contentLength().toString())
            .body(body)
            .build()
    }

    private companion object {
        private val V3_MPD_FILE_PATTERN =
            Regex("""^/v3/(?<sha1>[a-f0-9]{40})_[a-f0-9]{7}/[^/]+\.mpd$""")
    }
}
