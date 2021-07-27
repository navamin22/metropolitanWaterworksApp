//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.log;

import android.util.Log;

public class LogcatLogStrategy implements LogStrategy {
  public LogcatLogStrategy() {
  }

  public void log(int priority, String tag, String message) {
    Utils.checkNotNull(message);
    if (tag == null) {
      tag = "icod";
    }

    Log.println(priority, tag, message);
  }
}
