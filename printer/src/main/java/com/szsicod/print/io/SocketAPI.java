//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.io;

import com.szsicod.print.log.Logger;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketAPI implements InterfaceAPI {
  private static Socket client;
  private String site;
  private int port;
  private OutputStream out;

  public SocketAPI(String site, int port) {
    this.site = site;
    this.port = port;
  }

  public void closeSocket() {
    try {
      client.close();
    } catch (Exception var2) {
      var2.printStackTrace();
    }

  }

  public synchronized int openDevice() {
    try {
      if (client == null || client.isClosed()) {
        Logger.i("socket 已关闭 重新开启", new Object[0]);
        client = new Socket(this.site, this.port);
        this.out = client.getOutputStream();
      }

      if (client.isConnected()) {
        this.out = client.getOutputStream();
        Logger.i("socket 已连接并获取oi流", new Object[0]);
      }

      return 0;
    } catch (Exception var2) {
      var2.printStackTrace();
      Logger.e(var2.getMessage(), new Object[0]);
      return -1;
    }
  }

  public synchronized int closeDevice() {
    if (!client.isClosed()) {
      try {
        client.close();
        Logger.i("socket 已关闭", new Object[0]);
      } catch (IOException var2) {
        var2.printStackTrace();
        Logger.i("socket 关闭异常 " + var2.getMessage(), new Object[0]);
      }

      return 0;
    } else {
      return -1;
    }
  }

  public synchronized Boolean isOpen() {
    try {
      if (client == null || client.isClosed()) {
        client = new Socket(this.site, this.port);
        this.out = client.getOutputStream();
      }

      if (client.isConnected()) {
        this.out = client.getOutputStream();
      }

      return true;
    } catch (Exception var2) {
      var2.printStackTrace();
      return false;
    }
  }

  public synchronized int readBuffer(byte[] readBuffer, int offsetSize, int readSize, int waitTime) {
    return 0;
  }

  public synchronized int writeBuffer(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime) {
    try {
      if (client == null || client.isClosed()) {
        client = new Socket(this.site, this.port);
        this.out = client.getOutputStream();
      }

      if (client.isConnected()) {
        this.out = client.getOutputStream();
      }

      this.out.write(writeBuffer, offsetSize, writeSize);
      this.out.flush();
      return writeSize;
    } catch (Exception var6) {
      var6.printStackTrace();
      return -1;
    }
  }
}
