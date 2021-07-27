//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

public interface Printer {
  void addAdapter(LogAdapter var1);

  void d(String var1, Object... var2);

  void d(Object var1);

  void e(String var1, Object... var2);

  void e(Throwable var1, String var2, Object... var3);

  void w(String var1, Object... var2);

  void i(String var1, Object... var2);

  void v(String var1, Object... var2);

  void wtf(String var1, Object... var2);

  void log(int var1, String var2, String var3, Throwable var4);

  void clearLogAdapters();
}
