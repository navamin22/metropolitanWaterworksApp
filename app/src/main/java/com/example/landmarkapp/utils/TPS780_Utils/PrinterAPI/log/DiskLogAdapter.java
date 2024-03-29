//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.log;

public class DiskLogAdapter implements LogAdapter {
  private final LogStrategy formatStrategy;

  public DiskLogAdapter() {
    this.formatStrategy = DecorDiskLogStrategy.newBuilder().build();
  }

  public DiskLogAdapter(LogStrategy formatStrategy) {
    this.formatStrategy = (LogStrategy)Utils.checkNotNull(formatStrategy);
  }

  public boolean isLoggable(int priority, String tag) {
    return true;
  }

  public void log(int priority, String tag, String message) {
    this.formatStrategy.log(priority, tag, message);
  }
}
