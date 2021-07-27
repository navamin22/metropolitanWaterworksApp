//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import com.szsicod.print.log.DiskLogStrategy.WriteHandler;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DecorDiskLogStrategy implements LogStrategy {
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final String NEW_LINE_REPLACEMENT = " <br> ";
  private static final String SEPARATOR = ",";
  private final Date date;
  private final SimpleDateFormat dateFormat;
  private final LogStrategy logStrategy;
  private final String tag;

  private DecorDiskLogStrategy(DecorDiskLogStrategy.Builder builder) {
    Utils.checkNotNull(builder);
    this.date = builder.date;
    this.dateFormat = builder.dateFormat;
    this.logStrategy = builder.logStrategy;
    this.tag = builder.tag;
  }

  public static DecorDiskLogStrategy.Builder newBuilder() {
    return new DecorDiskLogStrategy.Builder();
  }

  public void log(int priority, String onceOnlyTag, String message) {
    Utils.checkNotNull(message);
    String tag = this.formatTag(onceOnlyTag);
    this.date.setTime(System.currentTimeMillis());
    StringBuilder builder = new StringBuilder();
    builder.append(this.dateFormat.format(this.date));
    builder.append(",");
    builder.append(Utils.logLevel(priority));
    builder.append(",");
    builder.append(tag);
    if (message.contains(NEW_LINE)) {
      message = message.replaceAll(NEW_LINE, " <br> ");
    }

    builder.append(",");
    builder.append(message);
    builder.append(NEW_LINE);
    this.logStrategy.log(priority, tag, builder.toString());
  }

  private String formatTag(String tag) {
    return !Utils.isEmpty(tag) && !Utils.equals(this.tag, tag) ? this.tag + "-" + tag : this.tag;
  }

  public static final class Builder {
    private static final int MAX_BYTES = 512000;
    Date date;
    SimpleDateFormat dateFormat;
    LogStrategy logStrategy;
    String tag;

    private Builder() {
      this.tag = "szc_icod";
    }

    public DecorDiskLogStrategy.Builder date(Date val) {
      this.date = val;
      return this;
    }

    public DecorDiskLogStrategy.Builder dateFormat(SimpleDateFormat val) {
      this.dateFormat = val;
      return this;
    }

    public DecorDiskLogStrategy.Builder logStrategy(LogStrategy val) {
      this.logStrategy = val;
      return this;
    }

    public DecorDiskLogStrategy.Builder tag(String tag) {
      this.tag = tag;
      return this;
    }

    public DecorDiskLogStrategy build() {
      if (this.date == null) {
        this.date = new Date();
      }

      if (this.dateFormat == null) {
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
      }

      if (this.logStrategy == null) {
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState)) {
          String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
          String folder = diskPath + File.separatorChar + "icod";
          HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
          ht.start();
          Handler handler = new WriteHandler(ht.getLooper(), folder, 512000);
          this.logStrategy = new DiskLogStrategy(handler);
        }
      }

      return new DecorDiskLogStrategy(this);
    }
  }
}
