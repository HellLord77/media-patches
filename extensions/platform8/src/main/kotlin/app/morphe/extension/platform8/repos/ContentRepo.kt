package app.morphe.extension.platform8.repos

import android.net.Uri
import com.android.volley.Network
import com.android.volley.NetworkResponse
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject

internal class ContentRepo(
    private val network: Network,
    private val code: String,
    private val catalogue: String?,
    private val authorization: String?,
) {
    internal val assetData: JSONObject by lazy {
        getAssetData(code)
    }

    internal val cloudFront: JSONObject by lazy {
        runCatching { assetData["cloud_front"] as JSONObject }
            .getOrNull()
            ?.let {
                if (it.length() != 0) return@lazy it
            }

        getAssetData(CODE)["cloud_front"] as JSONObject
    }

    internal val response: NetworkResponse by lazy {
        cloudFront.put("cloud_url", (assetData["watchable_content"] as JSONArray)[0])

        NetworkResponse(
            JSONObject()
                .put("error_code", 200)
                .put("assetData", assetData)
                .put("cloud_front", cloudFront)
                .toString()
                .toByteArray()
        )
    }

    private fun getAssetData(code: String): JSONObject {
        val request =
            ContentRequest(
                Uri.parse("https://apps.digivive.com/platform8_dynamic/assetdetail")
                    .buildUpon()
                    .appendQueryParameter("code", code)
                    .appendQueryParameter("catlogue", catalogue ?: CATALOGUE)
                    .appendQueryParameter("type", "vod")
                    .build()
            )

        val response = network.performRequest(request).apply { require(statusCode == 200) }
        JSONObject(response.data.decodeToString()).apply {
            require(getString("error_code").toInt() == 200)
            return getJSONObject("result")
        }
    }

    private inner class ContentRequest(uri: Uri) : StringRequest(uri.toString(), null, null) {
        override fun getHeaders(): Map<String, String> =
            mapOf("Authorization" to (authorization ?: AUTHORIZATION))
    }

    private companion object {
        private const val CODE = "VOD28519049368"
        private const val CATALOGUE = "60508372dabf5464540a2460"
        private const val AUTHORIZATION = "60194480f127015d9011475a"
    }
}
