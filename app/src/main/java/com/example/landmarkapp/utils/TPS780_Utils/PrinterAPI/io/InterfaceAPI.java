//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.io;

public interface InterfaceAPI {
  int openDevice();

  int closeDevice();

  Boolean isOpen();

  int readBuffer(byte[] var1, int var2, int var3, int var4);

  int writeBuffer(byte[] var1, int var2, int var3, int var4);
}
