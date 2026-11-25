package app.morphe.extension.epicon.patches;

import com.epicchannel.epicon.data.apiService.EpicOnApiService;
import com.epicchannel.epicon.data.model.showURL.ShowUrlResponse;
import com.epicchannel.epicon.data.model.videoURL.PlayUrlInputData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import app.morphe.extension.epicon.repositories.ContentRepository;
import app.morphe.extension.shared.Logger;
import app.morphe.extension.shared.Utils;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import retrofit2.Response;

@SuppressWarnings("unused")
public class AccessContentPatch {
    @Nullable
    public static Object showURL(
            @NotNull EpicOnApiService self,
            @NotNull PlayUrlInputData playUrlInputData,
            @NotNull Continuation<Response<ShowUrlResponse>> continuation
    ) {
        var contentId = playUrlInputData.getContent_id();
        Utils.showToastShort(String.format("contentId: %s", contentId));

        return self.showURL(playUrlInputData, new Continuation<>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return continuation.getContext();
            }

            @SuppressWarnings({"unchecked", "KotlinInternalInJava"})
            @Override
            public void resumeWith(@NotNull Object result) {
                if (!(result instanceof Result.Failure)) {
                    result = getResponse(contentId, (Response<ShowUrlResponse>) result, ShowUrlResponse.class);
                }
                continuation.resumeWith(result);
            }
        });
    }

    @Nullable
    public static Object show_url(
            @NotNull com.epicchannel.epicon.apiService.EpicOnApiService self,
            @NotNull com.epicchannel.epicon.model.videoURL.PlayUrlInputData playUrlInputData,
            @NotNull Continuation<Response<com.epicchannel.epicon.model.show_url.ShowUrlResponse>> continuation
    ) {
        var contentId = playUrlInputData.getContent_id();
        Utils.showToastShort(String.format("contentId: %s", contentId));

        return self.show_url(playUrlInputData, new Continuation<>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return continuation.getContext();
            }

            @SuppressWarnings({"unchecked", "KotlinInternalInJava"})
            @Override
            public void resumeWith(@NotNull Object result) {
                if (!(result instanceof Result.Failure)) {
                    result = getResponse(
                            contentId,
                            (Response<com.epicchannel.epicon.model.show_url.ShowUrlResponse>) result,
                            com.epicchannel.epicon.model.show_url.ShowUrlResponse.class
                    );
                }
                continuation.resumeWith(result);
            }
        });
    }

    @NotNull
    private static <T> Response<T> getResponse(@NotNull String contentId, @NotNull Response<T> response, @NotNull Class<T> classOfT) {
        var finalResponse = response;
        Logger.printDebug(() -> String.format("response: %s", finalResponse));

        //noinspection resource
        if (response.raw().isSuccessful()) {
            try {
                var showUrlResponse = response.body();
                Logger.printDebug(() -> String.format("showUrlResponse: %s", showUrlResponse));

                var it = new ContentRepository(contentId);
                Logger.printInfo(() -> String.format("episode: %s", it.getEpisode$epicon()));
                response = it.getResponse$epicon(showUrlResponse, classOfT);
            } catch (Throwable e) {
                Logger.printException(() -> "contentRepository failure", e);
            }
        }
        return response;
    }
}
