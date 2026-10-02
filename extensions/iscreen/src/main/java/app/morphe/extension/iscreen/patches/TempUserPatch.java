package app.morphe.extension.iscreen.patches;

import static com.rockstreamer.iscreen.extensions.ExtensionsKt.getTempApiCall;

import app.morphe.extension.shared.Logger;
import app.morphe.extension.shared.Utils;
import com.google.gson.Gson;
import com.playoffstudio.modelmodule.LoginResponse;
import com.rockstreamer.iscreen.util.PreferenceUtil;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class TempUserPatch {
  public static String getRefreshToken(@NotNull PreferenceUtil self) {
    var refreshToken = self.getRefreshToken();

    if (refreshToken.isEmpty()) {
      try {
        refreshToken = getRefreshToken();
        Utils.showToastShort(String.format("refreshToken: %s", refreshToken));
        self.setRefreshToken(refreshToken);
      } catch (IOException e) {
        Logger.printException(() -> "getRefreshToken failure", e);
      }
    }

    return refreshToken;
  }

  @NotNull
  private static String getRefreshToken() throws IOException {
    var body = RequestBody.create(getTempApiCall().toString(), MediaType.get("application/json"));
    var request =
        new Request.Builder()
            .url("https://api.rockstreamer.com/auth/token/temp")
            .post(body)
            .build();

    String string;
    try (var response = new OkHttpClient().newCall(request).execute()) {
      string = response.body().string();
      Logger.printDebug(() -> String.format("response: %s", string));
    }

    var loginResponse = new Gson().fromJson(string, LoginResponse.class);
    Logger.printInfo(() -> String.format("loginResponse: %s", loginResponse));
    return loginResponse.getRefreshToken();
  }
}
