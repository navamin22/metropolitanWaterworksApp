//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Utils {
  public static long lastTime;
  public static final String LASTTIME = "lastTime";
  private static Context context;
  static final String DEFAULT_TAG = "icod";

  public static long getLastTime() {
    if (context == null) {
      return 0L;
    } else {
      SharedPreferences sp = context.getSharedPreferences("icod", 0);
      return sp.getLong("lastTime", 0L);
    }
  }

  public static void setLastTime(long time) {
    if (context != null) {
      SharedPreferences sp = context.getSharedPreferences("icod", 0);
      Editor editor = sp.edit();
      editor.putLong("lastTime", time);
      editor.commit();
    }
  }

  public static void init(Context context) {
    context = context;
  }

  protected Utils() {
  }

  static boolean isEmpty(CharSequence str) {
    return str == null || str.length() == 0;
  }

  static boolean equals(CharSequence a, CharSequence b) {
    if (a == b) {
      return true;
    } else {
      if (a != null && b != null) {
        int length = a.length();
        if (length == b.length()) {
          if (a instanceof String && b instanceof String) {
            return a.equals(b);
          }

          for(int i = 0; i < length; ++i) {
            if (a.charAt(i) != b.charAt(i)) {
              return false;
            }
          }

          return true;
        }
      }

      return false;
    }
  }

  static String getStackTraceString(Throwable tr) {
    if (tr == null) {
      return "";
    } else {
      for(Throwable t = tr; t != null; t = t.getCause()) {
        if (t instanceof UnknownHostException) {
          return "";
        }
      }

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      tr.printStackTrace(pw);
      pw.flush();
      return sw.toString();
    }
  }

  static String logLevel(int value) {
    switch(value) {
      case 2:
        return "VERBOSE";
      case 3:
        return "DEBUG";
      case 4:
        return "INFO";
      case 5:
        return "WARN";
      case 6:
        return "ERROR";
      case 7:
        return "ASSERT";
      default:
        return "UNKNOWN";
    }
  }

  public static String toString(Object object) {
    if (object == null) {
      return "null";
    } else if (!object.getClass().isArray()) {
      return object.toString();
    } else if (object instanceof boolean[]) {
      return Arrays.toString((boolean[])((boolean[])object));
    } else if (object instanceof byte[]) {
      return Arrays.toString((byte[])((byte[])object));
    } else if (object instanceof char[]) {
      return Arrays.toString((char[])((char[])object));
    } else if (object instanceof short[]) {
      return Arrays.toString((short[])((short[])object));
    } else if (object instanceof int[]) {
      return Arrays.toString((int[])((int[])object));
    } else if (object instanceof long[]) {
      return Arrays.toString((long[])((long[])object));
    } else if (object instanceof float[]) {
      return Arrays.toString((float[])((float[])object));
    } else if (object instanceof double[]) {
      return Arrays.toString((double[])((double[])object));
    } else {
      return object instanceof Object[] ? Arrays.deepToString((Object[])((Object[])object)) : "Couldn't find a correct type for the object";
    }
  }

  static <T> T checkNotNull(T obj) {
    if (obj == null) {
      throw new NullPointerException();
    } else {
      return obj;
    }
  }
}
