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
    public static Object showURL(@NotNull EpicOnApiService self, @NotNull PlayUrlInputData playUrlInputData, @NotNull Continuation<Response<ShowUrlResponse>> continuation) {
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
                    result = getResponse(contentId, (Response<ShowUrlResponse>) result);
                }
                continuation.resumeWith(result);
            }
        });
    }

    @NotNull
    private static Response<ShowUrlResponse> getResponse(@NotNull String contentId, @NotNull Response<ShowUrlResponse> response) {
        var finalResponse = response;
        Logger.printDebug(() -> String.format("response: %s", finalResponse));

        if (response.rawResponse.isSuccessful()) {
            try {
                var showUrlResponse = response.body;
                Logger.printDebug(() -> String.format("showUrlResponse: %s", showUrlResponse));

                var it = new ContentRepository(contentId);
                Logger.printInfo(() -> String.format("episode: %s", it.getEpisode$epicon()));
                response = it.getResponse$epicon(showUrlResponse);
            } catch (Throwable e) {
                Logger.printException(() -> "contentRepository failure", e);
            }
        }
        return response;
    }
}
