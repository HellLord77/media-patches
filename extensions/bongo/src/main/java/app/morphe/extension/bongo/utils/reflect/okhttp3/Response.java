package app.morphe.extension.bongo.utils.reflect.okhttp3;

import app.morphe.extension.bongo.utils.reflect.ReflectUtil;
import java.lang.reflect.Method;
import okhttp3.Request;
import org.jetbrains.annotations.Nullable;

public class Response {
  private static Method REQUEST =
      ReflectUtil.getMethod("okhttp3.Response", "request", Request.class);

  @Nullable
  public static Object request(Object self) throws Exception {
    if (REQUEST == null) {
      REQUEST = ReflectUtil.getMethod(self.getClass(), "request", Request.class);
    }

    return REQUEST.invoke(self);
  }

  @Nullable
  public static Request request(okhttp3.Response self) throws Exception {
    return (Request) request((Object) self);
  }
}
