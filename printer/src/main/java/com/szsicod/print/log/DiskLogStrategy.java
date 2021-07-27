//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiskLogStrategy implements LogStrategy {
  private final Handler handler;

  public DiskLogStrategy(Handler handler) {
    this.handler = (Handler)Utils.checkNotNull(handler);
  }

  public void log(int level, String tag, String message) {
    Utils.checkNotNull(message);
    this.handler.sendMessage(this.handler.obtainMessage(level, message));
  }

  static class WriteHandler extends Handler {
    private final String folder;
    private final int maxFileSize;

    WriteHandler(Looper looper, String folder, int maxFileSize) {
      super((Looper)Utils.checkNotNull(looper));
      this.folder = (String)Utils.checkNotNull(folder);
      this.maxFileSize = maxFileSize;
    }

    public void handleMessage(Message msg) {
      String content = (String)msg.obj;
      FileWriter fileWriter = null;
      File logFile = this.getLogFile(this.folder, "icod");

      try {
        fileWriter = new FileWriter(logFile, true);
        this.writeLog(fileWriter, content);
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException var8) {
        if (fileWriter != null) {
          try {
            fileWriter.flush();
            fileWriter.close();
          } catch (IOException var7) {
            ;
          }
        }
      }

    }

    private void writeLog(FileWriter fileWriter, String content) throws IOException {
      Utils.checkNotNull(fileWriter);
      Utils.checkNotNull(content);
      fileWriter.append(content);
    }

    private File getLogFile(String folderName, String fileName) {
      Utils.checkNotNull(folderName);
      Utils.checkNotNull(fileName);
      File folder = new File(folderName);
      if (!folder.exists()) {
        folder.mkdirs();
      }

      File newFile = null;
      File existingFile = null;
      Date date = new Date(System.currentTimeMillis());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy_MM_dd_HH_mm");
      long lastTime = Utils.getLastTime();
      long time = date.getTime();
      String format = simpleDateFormat.format(time);
      if (lastTime == 0L) {
        newFile = new File(folder, String.format("%s_%s.csv", fileName, format));
      //  Log.i("tag", "第一次");
      } else {
        format = simpleDateFormat.format(lastTime);
        newFile = new File(folder, String.format("%s_%s.csv", fileName, format));
        if (!newFile.exists() && lastTime > 0L) {
          lastTime = time;
          format = simpleDateFormat.format(time);
          newFile = new File(folder, String.format("%s_%s.csv", fileName, format));
        }
      }

      while(newFile.exists()) {
        if (newFile != null) {
          if (newFile.length() < (long)this.maxFileSize) {
            return newFile;
          }

          lastTime = date.getTime();
          format = simpleDateFormat.format(lastTime);
          newFile = new File(folder, String.format("%s_%s.csv", fileName, format));
        }
      }

      if (lastTime == 0L) {
        lastTime = time;
      }

      Utils.setLastTime(lastTime);
      return newFile;
    }
  }
}
