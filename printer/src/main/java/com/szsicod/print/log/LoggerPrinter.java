//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class LoggerPrinter implements Printer {
  private static final String TAG = "icod";
  private final List<LogAdapter> logAdapters = new ArrayList();

  LoggerPrinter() {
  }

  public void d(String message, Object... args) {
    this.log(3, (Throwable)null, message, (Object[])args);
  }

  public void d(Object object) {
    this.log(3, (Throwable)null, Utils.toString(object), "");
  }

  public void e(String message, Object... args) {
    this.e((Throwable)null, message, args);
  }

  public void e(Throwable throwable, String message, Object... args) {
    this.log(6, (Throwable)throwable, message, (Object[])args);
  }

  public void w(String message, Object... args) {
    this.log(5, (Throwable)null, message, (Object[])args);
  }

  public void i(String message, Object... args) {
    this.log(4, (Throwable)null, message, (Object[])args);
  }

  public void v(String message, Object... args) {
    this.log(2, (Throwable)null, message, (Object[])args);
  }

  public void wtf(String message, Object... args) {
    this.log(7, (Throwable)null, message, (Object[])args);
  }

  public synchronized void log(int priority, String tag, String message, Throwable throwable) {
    if (throwable != null && message != null) {
      message = message + " : " + Utils.getStackTraceString(throwable);
    }

    if (throwable != null && message == null) {
      message = Utils.getStackTraceString(throwable);
    }

    if (Utils.isEmpty(message)) {
      message = "Empty/NULL log message";
    }

    Iterator var5 = this.logAdapters.iterator();

    while(var5.hasNext()) {
      LogAdapter adapter = (LogAdapter)var5.next();
      if (adapter.isLoggable(priority, tag)) {
        adapter.log(priority, tag, message);
      }
    }

  }

  public void clearLogAdapters() {
    this.logAdapters.clear();
  }

  public void addAdapter(LogAdapter adapter) {
    this.logAdapters.add(Utils.checkNotNull(adapter));
  }

  private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
    Utils.checkNotNull(msg);
    String tag = "icod";
    String message = this.createMessage(msg, args);
    this.log(priority, tag, message, throwable);
  }

  private String createMessage(String message, Object... args) {
    return args != null && args.length != 0 ? String.format(message, args) : message;
  }
}
