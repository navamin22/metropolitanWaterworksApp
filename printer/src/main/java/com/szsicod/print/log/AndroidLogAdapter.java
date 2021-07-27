//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

public class AndroidLogAdapter implements LogAdapter {
  private final LogStrategy formatStrategy;

  public AndroidLogAdapter() {
    this.formatStrategy = new LogcatLogStrategy();
  }

  public AndroidLogAdapter(LogStrategy formatStrategy) {
    this.formatStrategy = (LogStrategy)Utils.checkNotNull(formatStrategy);
  }

  public boolean isLoggable(int priority, String tag) {
    return true;
  }

  public void log(int priority, String tag, String message) {
    this.formatStrategy.log(priority, tag, message);
  }
}
