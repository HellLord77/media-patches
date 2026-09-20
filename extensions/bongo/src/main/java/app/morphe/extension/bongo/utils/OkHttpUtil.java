package app.morphe.extension.bongo.utils;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OkHttpUtil {
  private static Method requestHeaderMethod = null;
  private static Method responseRequestMethod = null;

  static {
    for (var method : Request.class.getMethods()) {
      if (method.getParameterCount() == 1
          && method.getParameterTypes()[0] == String.class
          && method.getReturnType() == String.class) {
        Log.v("com.app.extension.bongo", "requestHeaderMethod: " + method.getName());
        requestHeaderMethod = method;
        break;
      }
    }

    for (var method : Response.class.getMethods()) {
      if (method.getParameterCount() == 0 && method.getReturnType() == Request.class) {
        Log.v("com.app.extension.bongo", "responseRequestMethod: " + method.getName());
        responseRequestMethod = method;
        break;
      }
    }
  }

  @Nullable
  public static String invokeRequestHeader(@NotNull Request request, @NotNull String name)
      throws InvocationTargetException, IllegalAccessException {
    if (requestHeaderMethod == null) {
      return null;
    }

    return (String) requestHeaderMethod.invoke(request, name);
  }

  @Nullable
  public static Request invokeResponseRequest(@NotNull Response response)
      throws InvocationTargetException, IllegalAccessException {
    if (responseRequestMethod == null) {
      return null;
    }

    return (Request) responseRequestMethod.invoke(response);
  }
}
