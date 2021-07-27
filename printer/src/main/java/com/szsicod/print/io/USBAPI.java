//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.io;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.szsicod.print.log.Logger;

import java.util.HashMap;
import java.util.Iterator;

public class USBAPI implements InterfaceAPI {
  private int hasPermission = -2;
  private Context mActivity = null;
  private UsbManager mUsbManager = null;
  private UsbDevice mDevice = null;
  private UsbDeviceConnection mUsbDeviceConnection = null;
  private PendingIntent mPermissionIntent = null;
  private UsbEndpoint mEndpointIn = null;
  private UsbEndpoint mEndpointOut = null;
  private boolean unRegister;
  private UsbCheckSRT[] mUsbCheckSRT = {
          new UsbCheckSRT(1155, 30016),
          new UsbCheckSRT(1157, 30017),
          new UsbCheckSRT(6790, 30084),
          new UsbCheckSRT(3544, 5120),
          new UsbCheckSRT(483, 7540),
          new UsbCheckSRT(8401, 28680),
          new UsbCheckSRT(1659, 8965),
          new UsbCheckSRT(1046, 20497),
          new UsbCheckSRT(7344, 3),
          new UsbCheckSRT(5455,5455),
          new UsbCheckSRT(26728,1280),
          new UsbCheckSRT(26728,512),
          new UsbCheckSRT(26728,1536),
          new UsbCheckSRT(34918,1137),
          new UsbCheckSRT(1659,1137),
          new UsbCheckSRT(1155,17224),
          new UsbCheckSRT(7358,6790),
          new UsbCheckSRT(1155,22304)
  };
  private static final String ACTION_USB_PERMISSION = "com.szsicod.print.USB_PERMISSION";
  private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if ("com.szsicod.print.USB_PERMISSION".equals(action)) {
        synchronized(this) {
          UsbDevice device = (UsbDevice)intent.getParcelableExtra("device");
          if (intent.getBooleanExtra("permission", false) && device != null) {
            Logger.i("usb 设备已经授权 ", new Object[0]);
            USBAPI.this.hasPermission = 0;
          } else {
            USBAPI.this.hasPermission = -1;
            Logger.i("usb 设备没有授权 ", new Object[0]);
          }
        }
      }

      if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
        USBAPI.this.mDevice = (UsbDevice)intent.getParcelableExtra("device");
      }

    }
  };

  public USBAPI(Context context) {
    this.mActivity = context;
    this.mUsbManager = (UsbManager)this.mActivity.getSystemService("usb");
    this.mPermissionIntent = PendingIntent.getBroadcast(this.mActivity, 0, new Intent("com.szsicod.print.USB_PERMISSION"), 0);
  }

  private boolean checkID(int pid, int vid) {
    for(int n = 0; n < this.mUsbCheckSRT.length; ++n) {
      if (this.mUsbCheckSRT[n].check(pid, vid)) {
        return true;
      }
    }

    return false;
  }

  public int openDevice() {
    if (this.isOpen()) {
      return 0;
    } else {
      HashMap<String, UsbDevice> deviceList = this.mUsbManager.getDeviceList();

      for(Iterator deviceIterator = deviceList.values().iterator(); deviceIterator.hasNext(); this.mDevice = null) {
        this.mDevice = (UsbDevice)deviceIterator.next();
        Logger.i(this.mDevice.getVendorId() + " PID=" + this.mDevice.getProductId(), new Object[0]);
        if (this.checkID(this.mDevice.getVendorId(), this.mDevice.getProductId())) {
          Logger.i(" 找到 匹配USB VID=" + this.mDevice.getVendorId() + " PID=" + this.mDevice.getProductId(), new Object[0]);
          break;
        }
      }

      if (this.mDevice == null) {
        Logger.i(" 找不到 匹配USB VID 和PID", new Object[0]);
        return -1;
      } else {
        IntentFilter filter = new IntentFilter("com.szsicod.print.USB_PERMISSION");
        this.mActivity.registerReceiver(this.mUsbReceiver, filter);
        this.unRegister = false;
        if (!this.mUsbManager.hasPermission(this.mDevice)) {
          this.mUsbManager.requestPermission(this.mDevice, this.mPermissionIntent);
          this.hasPermission = -2;

          while(this.hasPermission == -2) {
            try {
              Thread.sleep(50L);
            } catch (InterruptedException var5) {
              var5.printStackTrace();
            }
          }
        }

        if (this.hasPermission == -1) {
          if (!this.unRegister) {
            this.mActivity.unregisterReceiver(this.mUsbReceiver);
          }

          this.unRegister = true;
          return -1;
        } else {
          if (this.hasPermission == 0) {
            this.mActivity.unregisterReceiver(this.mUsbReceiver);
            this.unRegister = true;
          }

          this.mUsbDeviceConnection = this.mUsbManager.openDevice(this.mDevice);
          if (this.mUsbDeviceConnection == null) {
            Logger.e("mUsbDeviceConnection 为空 ", new Object[0]);
            if (!this.unRegister) {
              this.mActivity.unregisterReceiver(this.mUsbReceiver);
            }

            this.unRegister = true;
            this.mDevice = null;
            return -1;
          } else if (!this.InitIOConfig()) {
            this.closeDevice();
            return -1;
          } else {
            return 0;
          }
        }
      }
    }
  }

  public void deviceControlTransfer() {
    this.mUsbDeviceConnection.controlTransfer(64, 178, 0, 0, (byte[])null, 0, 0);
    this.mUsbDeviceConnection.controlTransfer(64, 180, 0, 0, (byte[])null, 0, 0);
  }

  private Boolean InitIOConfig() {
    this.mEndpointIn = null;
    this.mEndpointOut = null;
    if (this.mDevice != null && this.mUsbDeviceConnection != null) {
      int interfaceCount = 1;
      UsbInterface intf = null;
      UsbEndpoint endpoint = null;
      if (this.mDevice.getInterfaceCount() == 0) {
        Logger.e("InterfaceCount 为零个", new Object[0]);
        return false;
      } else {
        for(int n = 0; n < interfaceCount; ++n) {
          intf = this.mDevice.getInterface(n);
          boolean forceClaim = true;
          this.mUsbDeviceConnection.claimInterface(intf, forceClaim);
          int endpoitCount = intf.getEndpointCount();
          if (endpoitCount == 0) {
            return false;
          }

          for(int m = 0; m < endpoitCount; ++m) {
            endpoint = intf.getEndpoint(m);
            if (endpoint.getType() == 2) {
              if (endpoint.getDirection() == 0) {
                this.mEndpointOut = endpoint;
              } else if (endpoint.getDirection() == 128) {
                this.mEndpointIn = endpoint;
              }
            }
          }
        }

        if (this.mEndpointOut == null || this.mEndpointIn == null) {
          Logger.e(" usb 找不到输入输出 ", new Object[0]);
          return false;
        }

        return true;
      }
    } else {
      Logger.e("mUsbDeviceConnection 为空 ", new Object[0]);
      return false;
    }
  }

  public int closeDevice() {
    if (!this.isOpen()) {
      return -1;
    } else {
      this.mUsbDeviceConnection.close();
      this.mUsbDeviceConnection = null;
      if (!this.unRegister) {
        this.mActivity.unregisterReceiver(this.mUsbReceiver);
      }

      Logger.i("usb 设备关闭 ", new Object[0]);
      return 0;
    }
  }

  public Boolean isOpen() {
    return this.mUsbDeviceConnection == null ? false : true;
  }

  @SuppressLint({"NewApi"})
  public int readBuffer(byte[] readBuffer, int offsetSize, int readSize, int waitTime) {
    if (this.mEndpointIn == null) {
      return -1;
    } else {
      int bulkTransferSize = this.mUsbDeviceConnection.bulkTransfer(this.mEndpointIn, readBuffer, offsetSize, readSize, waitTime);
      if (bulkTransferSize == readSize) {
        Logger.i("usb 读取成功 大小为" + bulkTransferSize, new Object[0]);
        return bulkTransferSize;
      } else {
        return -1;
      }
    }
  }

  @SuppressLint({"NewApi"})
  public int writeBuffer(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime) {
    if (null != this.mUsbDeviceConnection && null != this.mEndpointOut) {
      int writeTrueSize = 16384;
      int writeStartSieze = 0;
      byte[] writeTrueBuffer = new byte[writeTrueSize];
      if (this.mEndpointOut == null) {
        return -1;
      } else {
        int allTransferSize = 0;
        if (writeSize > writeTrueSize) {
          int bulkTransferSize;
          for(int i = 0; i < writeSize / writeTrueSize; ++i) {
            System.arraycopy(writeBuffer, writeStartSieze, writeTrueBuffer, 0, writeTrueSize);
            bulkTransferSize = this.mUsbDeviceConnection.bulkTransfer(this.mEndpointOut, writeTrueBuffer, offsetSize, writeTrueSize, waitTime);
            allTransferSize += bulkTransferSize;
            writeStartSieze += writeTrueSize;
          }

          System.arraycopy(writeBuffer, writeStartSieze, writeTrueBuffer, 0, writeSize - writeStartSieze);
          bulkTransferSize = this.mUsbDeviceConnection.bulkTransfer(this.mEndpointOut, writeTrueBuffer, offsetSize, writeSize - writeStartSieze, waitTime);
          allTransferSize += bulkTransferSize;
          if (allTransferSize == writeSize) {
            Logger.i("usb 传输成功 数据总数" + allTransferSize, new Object[0]);
            return allTransferSize;
          }
        } else {
          allTransferSize = this.mUsbDeviceConnection.bulkTransfer(this.mEndpointOut, writeBuffer, offsetSize, writeSize, waitTime);
          if (allTransferSize == writeSize) {
            Logger.i(" usb 传输成功 数据总数" + writeSize, new Object[0]);
            return allTransferSize;
          }
        }

        Logger.i("usb 传输失败 已经传输" + allTransferSize + " 传输总数" + writeSize, new Object[0]);
        return -1;
      }
    } else {
      Logger.i("mUsbDeviceConnection为空不能输入 ", new Object[0]);
      return -1;
    }
  }

  private class UsbCheckSRT {
    public int PID = 0;
    public int VID = 0;

    public UsbCheckSRT(int vid, int pid) {
      this.PID = pid;
      this.VID = vid;
    }

    public boolean check(int vid, int pid) {
      return this.PID == pid && this.VID == vid;
    }
  }
}
