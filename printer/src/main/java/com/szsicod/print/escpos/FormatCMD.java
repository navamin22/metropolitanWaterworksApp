//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.escpos;

import android.graphics.Bitmap;
import java.io.IOException;

public class FormatCMD {
  public FormatCMD() {
  }

  private int byteArrayToIntLowFirst(byte[] b, int offset) {
    int value = 0;

    for(int i = 0; i < 4; ++i) {
      int shift = i * 8;
      value += (b[i + offset] & 255) << shift;
    }

    return value;
  }

  public byte[] BitmapToBMPData(Bitmap bitmap) throws IOException {
    Bmp bmp = new Bmp(bitmap, (short)1);
    return bmp.getData();
  }

  public int jbitmap2cmd(byte[] data, int dataSize, byte[] outData, int outDataMaxSize) {
    int outSize = 0;
    byte[] pInData = data;
    byte[] pOutData = outData;
    if (data != null && outData != null && dataSize > 0 && outDataMaxSize > 0) {
      if (dataSize <= 64) {
        return -2;
      } else {
        int bmihbiWidth = this.byteArrayToIntLowFirst(data, 18);
        int bmihbiHeight = this.byteArrayToIntLowFirst(data, 22);
        int bmfhbfOffBits = this.byteArrayToIntLowFirst(data, 10);
        int iWidth = bmihbiWidth;
        int iHeight = bmihbiHeight;
        int ByteWidth = (bmihbiWidth + 31) / 32 * 4;
        int TrueByteWidth = (bmihbiWidth + 7) / 8;
        int TrueHeight = bmihbiHeight;
        int Readed = 0;
        outData[0] = 29;
        outData[1] = 118;
        outData[2] = 48;
        outData[3] = 0;
        outData[4] = (byte)(TrueByteWidth % 256);
        outData[5] = (byte)(TrueByteWidth / 256);
        outData[6] = (byte)(bmihbiHeight % 256);
        outData[7] = (byte)(bmihbiHeight / 256);
         outSize = outSize + 8;

        for(int i = 0; i < iHeight; ++i) {
          int foff = bmfhbfOffBits + (iHeight - 1 - i) * ByteWidth;
          System.arraycopy(pInData, foff, pOutData, 8 + Readed * TrueByteWidth, TrueByteWidth);
          ++Readed;
          outSize += TrueByteWidth;
          if (Readed == TrueHeight || i == iHeight - 1) {
            break;
          }
        }

        for(int j = 0; j < Readed; ++j) {
          for(int m = 0; m < TrueByteWidth; ++m) {
            if (m == TrueByteWidth - 1) {
              byte[] mask = new byte[]{-128, -64, -32, -16, -8, -4, -2, -1};
              if (iWidth % 8 > 0) {
                pOutData[8 + j * TrueByteWidth + m] = (byte)(~pOutData[8 + j * TrueByteWidth + m] & mask[iWidth % 8 - 1]);
              } else {
                pOutData[8 + j * TrueByteWidth + m] = (byte)(~pOutData[8 + j * TrueByteWidth + m]);
              }
            } else {
              pOutData[8 + j * TrueByteWidth + m] = (byte)(~pOutData[8 + j * TrueByteWidth + m]);
            }
          }
        }

        return outSize;
      }
    } else {
      return -1;
    }
  }
}
