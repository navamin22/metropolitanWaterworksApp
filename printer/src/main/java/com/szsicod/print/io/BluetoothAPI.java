//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.io;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.util.Log;
import com.szsicod.print.log.Logger;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class BluetoothAPI implements InterfaceAPI {
  private Context context;
  public static final int WRITEBYTEMAX = 4096;
  public static final int READBYTEMAX = 1024;
  private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private BluetoothAdapter btAdapter;
  private BluetoothDevice btDevice;
  private BluetoothSocket btSocket;
  private OutputStream os = null;
  private byte[] mTempData = null;
  private int mReadbyte = 0;
  private Boolean mBExceptionBoolean = false;
  public static final int READTIMEOUT = 90000;
  protected int mTimeAfterRead = 100;

  public BluetoothAPI(Context context) {
    this.context = context;
    this.btAdapter = BluetoothAdapter.getDefaultAdapter();
  }

  public boolean isBTSupport() {
    if (this.btAdapter == null) {
      return false;
    } else {
      Logger.i("支持蓝牙", new Object[0]);
      return true;
    }
  }

  public String getAddress() {
    return this.btAdapter != null ? this.btAdapter.getAddress() : null;
  }

  public String getName() {
    return this.btAdapter != null ? this.btAdapter.getName() : null;
  }

  public int getState() {
    return this.btAdapter != null ? this.btAdapter.getState() : 10;
  }

  @SuppressLint({"NewApi"})
  public Boolean isConnected() {
    if (this.btSocket == null) {
      return false;
    } else if (VERSION.SDK_INT >= 14) {
      boolean connected = this.btSocket.isConnected();
      if (connected) {
        Logger.i("bluetooth 已经连接", new Object[0]);
      } else {
        Logger.i("bluetooth 没有连接", new Object[0]);
      }

      return connected;
    } else {
      return false;
    }
  }

  public Boolean isOpen() {
    if (this.btDevice != null && this.isConnected()) {
      Logger.i("bluetooth 已经开启", new Object[0]);
      return true;
    } else {
      return false;
    }
  }

  public boolean openBluetooth(int waitTime) {
    boolean ret = false;

    try {
      if (this.btAdapter == null) {
        return ret;
      }

      if (!this.isEnabledBluetooth()) {
        Intent enableIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
        this.context.startActivity(enableIntent);
        if (waitTime > 0) {
          int minTime = 150;
          int waitCount = (waitTime + minTime - 1) / minTime;

          for(int n = 0; n < waitCount; ++n) {
            if (this.isEnabledBluetooth()) {
              ret = true;
              break;
            }

            SystemClock.sleep((long)minTime);
          }
        } else if (waitTime == 0) {
          ret = true;
        }
      } else {
        ret = true;
      }
    } catch (Exception var7) {
      ret = false;
    }

    return ret;
  }

  public boolean isEnabledBluetooth() {
    return this.btAdapter.isEnabled();
  }

  public boolean closeBluetooth() {
    boolean result = true;

    try {
      if (this.btAdapter != null && this.isEnabledBluetooth()) {
        this.btAdapter.disable();
      }
    } catch (Exception var3) {
      var3.printStackTrace();
      result = false;
    }

    return result;
  }

  public boolean setDiscoverable(int time) {
    if (time > 0 && time <= 300) {
      boolean result = true;
      if (this.btAdapter != null) {
        Intent discoverableIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
        discoverableIntent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", time);
        this.context.startActivity(discoverableIntent);
      } else {
        result = false;
      }

      return result;
    } else {
      Log.e("SNBC_POS", "setDiscoverable error time:" + time);
      return false;
    }
  }

  public boolean startDiscovery() {
    boolean result = true;
    if (this.btAdapter != null && this.btAdapter.getState() == BluetoothAdapter.STATE_ON) {
      if (this.btAdapter.isDiscovering()) {
        this.btAdapter.cancelDiscovery();
      }

      result = this.btAdapter.startDiscovery();
    } else {
      result = false;
    }

    return result;
  }

  public boolean isDiscovery() {
    return this.btAdapter.isDiscovering();
  }

  public boolean cancelDiscovery() {
    boolean result = false;
    if (this.btAdapter.isDiscovering()) {
      result = this.btAdapter.cancelDiscovery();
    }

    return result;
  }

  public Set<BluetoothDevice> getBondedDevices() {
    return this.btAdapter != null ? this.btAdapter.getBondedDevices() : null;
  }

  public int checkDevice(String macAddress) {
    if (this.btAdapter == null) {
      return 1001;
    } else if (!BluetoothAdapter.checkBluetoothAddress(macAddress)) {
      return 1001;
    } else {
      this.btDevice = this.btAdapter.getRemoteDevice(macAddress);
      return 1000;
    }
  }

  public int openDevice() {
    if (this.btDevice == null) {
      return -1;
    } else {
      byte ret = 0;

      try {
        this.btSocket = this.btDevice.createRfcommSocketToServiceRecord(uuid);
        this.btSocket.connect();
      } catch (Exception var3) {
        var3.printStackTrace();
        ret = -1;
      }

      return ret;
    }
  }

  public int closeDevice() {
    int result = 1000;
    if (this.btDevice != null) {
      this.btDevice = null;
    }

    try {
      if (this.btSocket != null) {
        this.btSocket.close();
      }
    } catch (IOException var3) {
      result = 1001;
      var3.printStackTrace();
    }

    return result;
  }

  public int writeBuffer(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime) {
    int i = 0;
    long tick = 0L;
    long totulTick = 0L;
    byte[] temp_buf = null;
    byte[] res_buf = null;
    if (writeBuffer != null && writeSize > 0 && offsetSize >= 0) {
      if (this.getState() == 10) {
        return 0;
      } else {
        tick = System.currentTimeMillis();
        totulTick = tick + (long)waitTime;

        try {
          this.os = this.btSocket.getOutputStream();
        } catch (Exception var20) {
          var20.printStackTrace();
          return 0;
        }

        if (this.os == null) {
          return 0;
        } else {
          byte[] des_buf = new byte[writeSize];
          Arrays.fill(des_buf, (byte)0);
          if (offsetSize + writeSize > writeBuffer.length) {
            des_buf = null;
            return 0;
          } else {
            System.arraycopy(writeBuffer, offsetSize, des_buf, 0, writeSize);
            if (des_buf.length > 512) {
            temp_buf = new byte[512];
              Arrays.fill(temp_buf, (byte)0);
              int mode_len = des_buf.length / 512;
              int res_len = des_buf.length % 512;

              for(int count = 0; count < mode_len; ++count) {
                tick = System.currentTimeMillis();
                if (tick > totulTick) {
                  temp_buf = null;
                  des_buf = null;
                  Logger.i("bluetooth 传输异常 超时", new Object[0]);
                  return count * 512;
                }

                System.arraycopy(des_buf, count * 512 + i, temp_buf, 0, 512);

                try {
                  this.os.write(temp_buf);
                  this.os.flush();
                } catch (IOException var19) {
                  Logger.i("bluetooth 传输异常" + var19.getMessage(), new Object[0]);
                  var19.printStackTrace();
                  temp_buf = null;
                  des_buf = null;
                  return count * 512;
                }
              }

              if (res_len != 0) {
                res_buf = new byte[res_len];

                for( i = 0; i < res_len; ++i) {
                  res_buf[i] = des_buf[mode_len * 512 + i];
                }

                try {
                  this.os.write(res_buf);
                  this.os.flush();
                } catch (IOException var18) {
                  var18.printStackTrace();
                  Logger.i("bluetooth 传输异常" + var18.getMessage(), new Object[0]);
                  temp_buf = null;
                  res_buf = null;
                  des_buf = null;
                  return -1;
                }
              }
            } else {
              try {
                this.os.write(des_buf);
              } catch (IOException var17) {
                var17.printStackTrace();
                temp_buf = null;
                res_buf = null;
                des_buf = null;
                Logger.i("bluetooth 传输异常" + var17.getMessage(), new Object[0]);
                return -1;
              }
            }

            temp_buf = null;
            res_buf = null;
            des_buf = null;
            Logger.i("bluetooth 传输完成", new Object[0]);
            return writeSize;
          }
        }
      }
    } else {
      return 0;
    }
  }

  public int readBuffer(byte[] readBuffer, int offsetSize, int readSize, int ReadTimeOut) {
    try {
      return this.readBuffer(readBuffer, readSize, ReadTimeOut);
    } catch (SocketTimeoutException var6) {
      var6.printStackTrace();
    } catch (IOException var7) {
      var7.printStackTrace();
    } catch (InterruptedException var8) {
      var8.printStackTrace();
    }

    return 0;
  }

  public int readBuffer(byte[] data, int readSize, int ReadTimeOut) throws IOException, SocketTimeoutException, InterruptedException {
    if (data == null) {
      return 0;
    } else {
      int data_len = data.length;
      BluetoothAPI.ReadThread readThread = new BluetoothAPI.ReadThread();
      readThread.setreadSize(readSize);
      readThread.start();
      long beginTime = System.currentTimeMillis();

      while(readThread.isAlive() && System.currentTimeMillis() < beginTime + (long)ReadTimeOut) {
        ;
      }

      readThread.close();
      if (readThread != null && readThread.isAlive()) {
        readThread.interrupt();
      }

      if (this.mBExceptionBoolean) {
        this.mBExceptionBoolean = false;
        return 0;
      } else {
        if (this.mReadbyte > 0 && this.mReadbyte <= data_len) {
          System.arraycopy(this.mTempData, 0, data, 0, this.mReadbyte);
          Log.i("Test", new String(data));
        }

        return this.mReadbyte;
      }
    }
  }

  public String byte2hex(byte[] buffer) {
    String h = "";

    for(int i = 0; i < buffer.length; ++i) {
      String temp = Integer.toHexString(buffer[i] & 255);
      if (temp.length() == 1) {
        temp = "0" + temp;
      }

      h = h + " " + temp;
    }

    return h;
  }

  public void finallize() {
    this.closeDevice();
    this.closeBluetooth();
  }

  private class ReadThread extends Thread {
    private volatile boolean isRun;
    private int readSize;

    private ReadThread() {
      this.isRun = true;
      this.readSize = 0;
    }

    public void run() {
      BluetoothAPI.this.mTempData = new byte[4096];
      BluetoothAPI.this.mReadbyte = 0;
      BluetoothAPI.this.mBExceptionBoolean = false;

      while(this.readSize > BluetoothAPI.this.mReadbyte) {
        if (this.isRun) {
          try {
            byte[] thisTempData = new byte[4096];
            int thisReadbyte = BluetoothAPI.this.btSocket.getInputStream().read(thisTempData);
            if (thisReadbyte > 0) {
              System.arraycopy(thisTempData, 0, BluetoothAPI.this.mTempData, BluetoothAPI.this.mReadbyte, thisReadbyte);
            }

            BluetoothAPI var3 = BluetoothAPI.this;
            var3.mReadbyte = var3.mReadbyte + thisReadbyte;
          } catch (IOException var4) {
            BluetoothAPI.this.mBExceptionBoolean = true;
            var4.printStackTrace();
          }
        }
      }

    }

    public void close() {
      this.isRun = false;
    }

    public void setreadSize(int readSize) {
      this.readSize = readSize;
    }
  }
}
