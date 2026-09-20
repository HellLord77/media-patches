package app.morphe.extension.bongo.utils;

import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GsonUtil {
  private static Method gsonFromJsonMethod = null;

  static {
    for (var method : Gson.class.getMethods()) {
      if (method.getParameterCount() == 2
          && method.getParameterTypes()[0] == String.class
          && method.getParameterTypes()[1] == Class.class
          && method.getReturnType() == Object.class) {
        Log.v("com.app.extension.bongo", "gsonFromJsonMethod: " + method.getName());
        gsonFromJsonMethod = method;
        break;
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public static <T> T invokeFromJson(
      @NotNull Gson gson, @NotNull String json, @NotNull Class<T> classOfT)
      throws InvocationTargetException, IllegalAccessException {
    if (gsonFromJsonMethod == null) {
      return null;
    }

    return (T) gsonFromJsonMethod.invoke(gson, json, classOfT);
  }
}
