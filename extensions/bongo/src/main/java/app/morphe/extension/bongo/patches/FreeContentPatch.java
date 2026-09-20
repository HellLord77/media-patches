package app.morphe.extension.bongo.patches;

import android.util.Log;
import app.morphe.extension.bongo.repos.ContentRepo;
import app.morphe.extension.bongo.utils.OkHttpUtil;
import com.bongo.bongobd.view.model.ContentDetailsResponse;
import com.bongo.bongobd.view.network.ApiServiceSaas;
import com.goebl.david.Webb;
import java.util.Objects;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Response;

@SuppressWarnings("unused")
public class FreeContentPatch {
  @Nullable
  public static Object getContentDetails(
      @NotNull ApiServiceSaas self,
      @Nullable String bongoId,
      @NotNull Continuation<Response<ContentDetailsResponse>> continuation) {
    Log.d("com.app.extension.bongo", String.format("bongoId: %s", bongoId));

    var interceptingContinuation =
        new Continuation<Response<ContentDetailsResponse>>() {
          @NotNull
          @Override
          public CoroutineContext getContext() {
            return continuation.getContext();
          }

          @Override
          public void resumeWith(@NotNull Object result) {
            Log.i("com.app.extension.bongo", String.format("result: %s", result));

            if (result instanceof Response<?> response && response.code() == 403) {
              String authorization = null;
              String acceptLanguage = null;

              try {
                var request = OkHttpUtil.invokeResponseRequest(response.raw());
                if (request != null) {
                  authorization = OkHttpUtil.invokeRequestHeader(request, Webb.HDR_AUTHORIZATION);
                  Log.v(
                      "com.app.extension.bongo", String.format("authorization: %s", authorization));
                  acceptLanguage = OkHttpUtil.invokeRequestHeader(request, "Accept-Language");
                  Log.v(
                      "com.app.extension.bongo",
                      String.format("acceptLanguage: %s", acceptLanguage));
                }

                Objects.requireNonNull(bongoId);
                Objects.requireNonNull(authorization);
                Objects.requireNonNull(acceptLanguage);

                var contentDetails =
                    ContentRepo.getContentDetails(bongoId, authorization, acceptLanguage);
                assert contentDetails != null;

                Log.d(
                    "com.app.extension.bongo", String.format("contentDetails: %s", contentDetails));
                result = Response.success(contentDetails);
              } catch (Exception e) {
                Log.w("com.app.extension.bongo", String.format("exception: %s", e.getClass()), e);
              }
            }

            Log.i("com.app.extension.bongo", String.format("result: %s", result));
            continuation.resumeWith(result);
          }
        };

    return self.getContentDetails(bongoId, interceptingContinuation);
  }
}
