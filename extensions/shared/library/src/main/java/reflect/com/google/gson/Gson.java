package reflect.com.google.gson;

import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reflect.Utils;

public class Gson {
  private static final Method FROM_JSON =
      Utils.getMethod(
          com.google.gson.Gson.class, "fromJson", Object.class, String.class, Class.class);

  @SuppressWarnings("unchecked")
  @Nullable
  public static <T> T fromJson(
      @NotNull com.google.gson.Gson self, @Nullable String json, @NotNull Class<T> classOfT)
      throws Exception {
    return (T) FROM_JSON.invoke(self, json, classOfT);
  }
}
