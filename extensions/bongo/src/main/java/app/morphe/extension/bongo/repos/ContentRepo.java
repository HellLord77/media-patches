package app.morphe.extension.bongo.repos;

import android.util.Log;
import app.morphe.extension.bongo.utils.GsonUtil;
import app.morphe.extension.bongo.utils.JSONUtil;
import com.bongo.bongobd.view.model.ContentDetailsResponse;
import com.goebl.david.Webb;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentRepo {
  private static final Webb WEBB = Webb.create();
  private static final Gson GSON = new Gson();

  @Nullable
  public static ContentDetailsResponse getContentDetails(
      @NotNull String bongoId, @NotNull String authorization, @NotNull String acceptLanguage)
      throws Exception {
    var contentTrailer = getContentTrailer(bongoId, authorization, acceptLanguage);
    Log.v("com.app.extension.bongo", String.format("contentTrailer: %s", contentTrailer));

    var content = getContent(contentTrailer.getJSONObject("content").getString("id"));
    Log.v("com.app.extension.bongo", String.format("content: %s", content));

    var contentDetails = buildContentDetails(contentTrailer, content, acceptLanguage);
    Log.v("com.app.extension.bongo", String.format("contentDetails: %s", contentDetails));

    return GsonUtil.invokeFromJson(GSON, contentDetails.toString(), ContentDetailsResponse.class);
  }

  @NotNull
  private static JSONObject getContentTrailer(
      @NotNull String bongoId, @NotNull String authorization, @NotNull String acceptLanguage) {
    return WEBB.get(
            "https://api.bongo-solutions.com/ironman/api/v1/content/content-trailer/" + bongoId)
        .header(Webb.HDR_AUTHORIZATION, authorization)
        .header("Accept-Language", acceptLanguage)
        .header("Country-Code", "QkQ=")
        .ensureSuccess()
        .asJsonObject()
        .getBody();
  }

  @NotNull
  private static JSONObject getContent(@NotNull String contentId) {
    return WEBB.get("https://api.bongo-solutions.com/ironman/api/v1/contents/" + contentId)
        .ensureSuccess()
        .asJsonObject()
        .getBody();
  }

  @NotNull
  private static JSONObject buildContentDetails(
      @NotNull JSONObject contentTrailer,
      @NotNull JSONObject content,
      @NotNull String acceptLanguage)
      throws JSONException {
    var translatedContent = JSONUtil.translateTo(content, acceptLanguage);
    var contentTrailerContent = contentTrailer.getJSONObject("content");
    translatedContent.put("cast_and_crew", contentTrailerContent.get("castAndCrew"));
    translatedContent.put("genre", contentTrailerContent.get("genre"));

    for (var key : new String[] {"shorts", "teaser", "vod"}) {
      try {
        var urls =
            translatedContent
                .getJSONObject(key)
                .getJSONObject("active_encode")
                .getJSONObject("urls");
        var keys = urls.keys();
        while (keys.hasNext()) {
          var urlKey = keys.next();
          var url = urls.getJSONObject(urlKey);
          url.put("url", "https://vod.bongobd.com/vod/vod" + url.getString("url"));
        }
      } catch (JSONException ignored) {
      }
    }

    var contentDetails = JSONUtil.snakeToCamel(translatedContent);
    try {
      var protectionScheme =
          contentDetails
              .getJSONObject("vod")
              .getJSONObject("activeEncode")
              .getJSONObject("protectionScheme");
      protectionScheme.put("drm_id", protectionScheme.remove("drmId"));
    } catch (JSONException ignored) {
    }
    return contentDetails;
  }
}
