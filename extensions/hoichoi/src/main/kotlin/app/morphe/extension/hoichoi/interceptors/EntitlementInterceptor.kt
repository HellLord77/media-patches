package app.morphe.extension.hoichoi.interceptors

import android.net.Uri
import app.morphe.extension.hoichoi.repos.ContentRepo
import app.morphe.extension.shared.Logger
import app.morphe.extension.shared.Utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.closeQuietly
import java.io.IOException
import java.net.HttpURLConnection

private fun intercept(
    request: Request,
    response: Response,
) = request.method == "POST" &&
    request.url.host == "prod-api.hoichoi.dev" &&
    request.url.pathSegments == listOf("contents", "api", "v1", "entitlement") &&
    response.code != HttpURLConnection.HTTP_OK

@Suppress("unused")
class EntitlementInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Logger.printDebug { "request: $request" }

        var response = chain.proceed(request)
        Logger.printDebug { "response: $response" }

        if (!intercept(request, response)) return response
        response.closeQuietly()
        val uri = Uri.parse(request.url.toString())

        val contentId = uri.getQueryParameter("contentId")!!
        Utils.showToastShort("contentId: $contentId")

        val action = uri.getQueryParameter("action")
        Logger.printDebug { "action: $action" }

        val language = uri.getQueryParameter("language")
        Logger.printDebug { "language: $language" }

        val anonymousToken = request.header("Anonymous-Token")
        Logger.printDebug { "anonymousToken: $anonymousToken" }

        @Suppress("TooGenericExceptionCaught")
        try {
            ContentRepo(chain, contentId, action, language, anonymousToken).apply {
                Logger.printInfo { "videoDetails: $videoDetails" }
                Logger.printInfo { "freeEntitlementInfo: $freeEntitlementInfo" }
                response = getResponse(response)
            }
        } catch (e: Exception) {
            Logger.printException({ "contentRepo failure" }, e)
        }
        return response
    }
}
