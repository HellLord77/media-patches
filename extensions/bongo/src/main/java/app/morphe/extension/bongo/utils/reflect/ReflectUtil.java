package app.morphe.extension.bongo.utils.reflect;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReflectUtil {
  private static final String TAG = "com.app.extension.bongo";

  @Nullable
  public static Method getMethod(
      @NotNull String className,
      @NotNull String name,
      @Nullable Class<?> returnType,
      @NotNull Class<?>... parameterTypes) {
    try {
      return getMethod(Class.forName(className), name, returnType, parameterTypes);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  @NotNull
  public static Method getMethod(
      @NotNull Class<?> self,
      @NotNull String name,
      @Nullable Class<?> returnType,
      @NotNull Class<?>... parameterTypes) {
    Method method = null;
    try {
      method = self.getMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      for (var publicMethod : self.getMethods()) {
        if ((returnType == null || publicMethod.getReturnType() == returnType)
            && Arrays.equals(publicMethod.getParameterTypes(), parameterTypes)) {

          if (method != null) {
            Log.v(
                TAG,
                String.format(
                    "getMethod: %s -> %s, %s",
                    self.getName(), method.getName(), publicMethod.getName()));
            throw new NullPointerException();
          }
          method = publicMethod;
        }
      }
    }

    Log.v(TAG, String.format("getMethod: %s.%s -> %s", self.getName(), name, method));
    return Objects.requireNonNull(method);
  }
}
