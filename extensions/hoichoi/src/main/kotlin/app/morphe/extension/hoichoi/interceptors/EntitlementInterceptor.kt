package app.morphe.extension.hoichoi.interceptors

import android.net.Uri
import app.morphe.extension.shared.Logger
import app.morphe.extension.shared.Utils
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.closeQuietly
import org.json.JSONObject
import java.io.IOException

@Suppress("unused")
class EntitlementInterceptor : Interceptor {
    companion object {
        const val CONTENT_ID = "58f40f48-99de-4413-b0f4-87e9ce67bd5d"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Logger.printDebug { "request: $request" }
        var response = chain.proceed(request)
        Logger.printDebug { "response: $response" }

        val uri = Uri.parse(request.url.toString())
        if (request.method == "POST" && uri.host == "prod-api.hoichoi.dev"
            && uri.path == "/contents/api/v1/entitlement" && response.code == 401
        ) {
            val contentId = uri.getQueryParameter("contentId")!!
            Logger.printInfo { "contentId: $contentId" }
            require(contentId != CONTENT_ID)

            val language = uri.getQueryParameter("language")
            response.closeQuietly()

            val entitlementInfo = getEntitlementInfo(
                chain,
                uri.getQueryParameter("action"),
                language,
                request.header("Anonymous-Token"),
            )
            val videoDetails = getVideoDetails(chain, contentId, language)
            response = buildEntitlementInfo(response, entitlementInfo, videoDetails)
        }

        return response
    }

    private fun getAnonymousToken(chain: Interceptor.Chain): String {
        val request =
            Request.Builder().url("https://prod-api.hoichoi.dev/core/api/v1/users/anonymous-token")
                .build()

        chain.proceed(request).use { response ->
            require(response.isSuccessful)
            val anonymousToken = response.body.string()
            Utils.showToastShort("anonymousToken: $anonymousToken")
            return anonymousToken
        }
    }

    private fun getEntitlementInfo(
        chain: Interceptor.Chain,
        action: String?,
        language: String?,
        anonymousToken: String?,
    ): JSONObject {
        val uri = Uri.parse("https://prod-api.hoichoi.dev/contents/api/v1/entitlement").buildUpon()
            .appendQueryParameter("contentId", CONTENT_ID).appendQueryParameter("action", action)
            .appendQueryParameter("language", language).build()
        val request = Request.Builder().url(uri.toString())
            .header("Anonymous-Token", anonymousToken ?: getAnonymousToken(chain))
            .post(RequestBody.EMPTY).build()

        chain.proceed(request).use { response ->
            require(response.isSuccessful)
            return JSONObject(response.body.string())
        }
    }

    private fun getVideoDetails(
        chain: Interceptor.Chain,
        contentId: String,
        language: String?,
    ): JSONObject {
        val uri =
            Uri.parse("https://prod-contents-api.hoichoi.dev/contents/api/v1/contents").buildUpon()
                .appendQueryParameter("contentId", contentId)
                .appendQueryParameter("language", language).build()
        val request = Request.Builder().url(uri.toString()).build()

        chain.proceed(request).use { response ->
            require(response.isSuccessful)
            return JSONObject(response.body.string())
        }
    }

    private fun buildEntitlementInfo(
        response: Response,
        entitlementInfo: JSONObject,
        videoDetails: JSONObject,
    ): Response {
        entitlementInfo.put("videoDetails", videoDetails)

        val body = entitlementInfo.toString().toResponseBody("application/json".toMediaType())
        return response.newBuilder().code(200)
            .header("Content-Length", body.contentLength().toString()).body(body).build()
    }
}
