//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.escpos;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import java.io.IOException;

public class Bmp {
  private Bmp.BmpFileHeader fileHeader = new Bmp.BmpFileHeader();
  private Bmp.BmpInfoHerder infoHeader = new Bmp.BmpInfoHerder();
  private Bmp.RGBQUAD[] rgbquadList = null;
  private byte[] data = null;

  public Bmp(Bitmap bitmap, short bitCount) throws IOException {
    if (bitmap != null) {
      long[] tick = new long[10];
      tick[0] = System.currentTimeMillis();
      this.infoHeader.biSize = 40;
      this.infoHeader.biWidth = bitmap.getWidth();
      this.infoHeader.biHeight = bitmap.getHeight();
      this.infoHeader.biPlanes = 1;
      this.infoHeader.biBitCount = bitCount;
      this.infoHeader.biCompression = 0;
      this.infoHeader.biSizeImage = 0;
      this.infoHeader.biXPelsPerMeter = 0;
      this.infoHeader.biYPelsPerMeter = 0;
      this.infoHeader.biClrUsed = 0;
      this.infoHeader.biClrImportant = 0;
      if (this.infoHeader.biBitCount == 1) {
        this.rgbquadList = new Bmp.RGBQUAD[2];
        this.rgbquadList[0] = new Bmp.RGBQUAD();
        this.rgbquadList[0].rgbBlue = 0;
        this.rgbquadList[0].rgbGreen = 0;
        this.rgbquadList[0].rgbRed = 0;
        this.rgbquadList[0].rgbRed = 0;
        this.rgbquadList[1] = new Bmp.RGBQUAD();
        this.rgbquadList[1].rgbBlue = -1;
        this.rgbquadList[1].rgbGreen = -1;
        this.rgbquadList[1].rgbRed = -1;
        this.rgbquadList[1].rgbRed = 0;
      } else if (this.infoHeader.biBitCount == 4) {
        this.rgbquadList = new Bmp.RGBQUAD[16];
      } else if (this.infoHeader.biBitCount == 8) {
        this.rgbquadList = new Bmp.RGBQUAD[256];
      }

      int bmpWidth = ((this.infoHeader.biWidth + 7) / this.infoHeader.biBitCount / 8 + 3) / 4 * 4;
      int bufferSize = bmpWidth * this.infoHeader.biHeight;
      this.fileHeader.bfType = 19778;
      this.fileHeader.bfReserved1 = 0;
      this.fileHeader.bfReserved2 = 0;
      this.fileHeader.bfOffBits = 54 + this.rgbquadList.length * 4;
      this.fileHeader.bfSize = this.fileHeader.bfOffBits + bufferSize;
      this.infoHeader.biSizeImage = this.fileHeader.bfSize - this.fileHeader.bfOffBits;
      this.data = new byte[this.fileHeader.bfSize];
      int writeSize = 0;
       writeSize = writeSize + this.writeWord(this.data, writeSize, this.fileHeader.bfType);
      writeSize += this.writeDword(this.data, writeSize, (long)this.fileHeader.bfSize);
      writeSize += this.writeWord(this.data, writeSize, this.fileHeader.bfReserved1);
      writeSize += this.writeWord(this.data, writeSize, this.fileHeader.bfReserved2);
      writeSize += this.writeDword(this.data, writeSize, (long)this.fileHeader.bfOffBits);
      writeSize += this.writeDword(this.data, writeSize, (long)this.infoHeader.biSize);
      writeSize += this.writeLong(this.data, writeSize, (long)this.infoHeader.biWidth);
      writeSize += this.writeLong(this.data, writeSize, (long)this.infoHeader.biHeight);
      writeSize += this.writeWord(this.data, writeSize, this.infoHeader.biPlanes);
      writeSize += this.writeWord(this.data, writeSize, this.infoHeader.biBitCount);
      writeSize += this.writeDword(this.data, writeSize, (long)this.infoHeader.biCompression);
      writeSize += this.writeDword(this.data, writeSize, (long)this.infoHeader.biSizeImage);
      writeSize += this.writeLong(this.data, writeSize, (long)this.infoHeader.biXPelsPerMeter);
      writeSize += this.writeLong(this.data, writeSize, (long)this.infoHeader.biYPelsPerMeter);
      writeSize += this.writeDword(this.data, writeSize, (long)this.infoHeader.biClrUsed);
      writeSize += this.writeDword(this.data, writeSize, (long)this.infoHeader.biClrImportant);

      for(int n = 0; n < this.rgbquadList.length; ++n) {
        writeSize += this.writeByte(this.data, writeSize, this.rgbquadList[n].rgbBlue);
        writeSize += this.writeByte(this.data, writeSize, this.rgbquadList[n].rgbGreen);
        writeSize += this.writeByte(this.data, writeSize, this.rgbquadList[n].rgbRed);
        writeSize += this.writeByte(this.data, writeSize, this.rgbquadList[n].rgbReserved);
      }

      int[] pixels = new int[this.infoHeader.biWidth * this.infoHeader.biHeight];
      bitmap.getPixels(pixels, 0, this.infoHeader.biWidth, 0, 0, this.infoHeader.biWidth, this.infoHeader.biHeight);
      int j = 0;
      int nRealCol = this.infoHeader.biHeight - 1;
      int blockSize = 8;
      int maxW = (this.infoHeader.biWidth + blockSize - 1) / blockSize;

      for(tick[1] = System.currentTimeMillis(); j < this.infoHeader.biHeight; --nRealCol) {
        int colStart = writeSize + nRealCol * bmpWidth;

        for(int wRow = 0; wRow < maxW; ++wRow) {
          int startNum = blockSize * wRow;

          for(int n = 0; n < blockSize && startNum + n < this.infoHeader.biWidth; ++n) {
            int clr = pixels[startNum + n + j * this.infoHeader.biWidth];
            int gray = getGreyLevel(clr, 1.0F);
            if (gray > 127) {
              int cz = 1;
              int tmp1016_1015 = colStart + wRow;
              byte[] tmp1119_1114 = this.data;
              tmp1119_1114[tmp1016_1015] += (byte)(cz << 7 - n);
            }
          }
        }

        ++j;
      }

      tick[2] = System.currentTimeMillis();
      tick[3] = tick[2] - tick[1];
      Log.i("delay", tick[3] + "");
    }
  }

  public byte[] getData() {
    return this.data;
  }

  public int getWidth() {
    return this.infoHeader.biWidth;
  }

  public int getHeight() {
    return this.infoHeader.biHeight;
  }

  public int getBitCount() {
    return this.infoHeader.biBitCount;
  }

  private int writeWord(byte[] stream, int start, int value) throws IOException {
    byte[] b = new byte[]{(byte)(value & 255), (byte)(value >> 8 & 255)};
    System.arraycopy(b, 0, stream, start, 2);
    return 2;
  }

  private int writeDword(byte[] stream, int start, long value) throws IOException {
    byte[] b = new byte[]{(byte)((int)(value & 255L)), (byte)((int)(value >> 8 & 255L)), (byte)((int)(value >> 16 & 255L)), (byte)((int)(value >> 24 & 255L))};
    System.arraycopy(b, 0, stream, start, 4);
    return 4;
  }

  private int writeLong(byte[] stream, int start, long value) throws IOException {
    byte[] b = new byte[]{(byte)((int)(value & 255L)), (byte)((int)(value >> 8 & 255L)), (byte)((int)(value >> 16 & 255L)), (byte)((int)(value >> 24 & 255L))};
    System.arraycopy(b, 0, stream, start, 4);
    return 4;
  }

  private int writeByte(byte[] stream, int start, byte value) throws IOException {
    stream[start] = value;
    return 1;
  }

  public static int getGreyLevel(int pixel, float intensity) {
    float alpha = (float)Color.alpha(pixel);
    if (alpha < 50.0F) {
      return 255;
    } else {
      float red = (float)Color.red(pixel);
      float green = (float)Color.green(pixel);
      float blue = (float)Color.blue(pixel);
      float parcial = red + green + blue;
      parcial = (float)((double)parcial / 3.0D);
      int gray = (int)(parcial * intensity);
      if (gray > 255) {
        gray = 255;
      }

      return gray;
    }
  }

  public class RGBQUAD {
    public byte rgbBlue;
    public byte rgbGreen;
    public byte rgbRed;
    public byte rgbReserved;

    public RGBQUAD() {
    }
  }

  public class BmpInfoHerder {
    public short biSize;
    public int biWidth;
    public int biHeight;
    public short biPlanes;
    public short biBitCount;
    public int biCompression;
    public int biSizeImage;
    public int biXPelsPerMeter;
    public int biYPelsPerMeter;
    public int biClrUsed;
    public int biClrImportant;

    public BmpInfoHerder() {
    }
  }

  public class BmpFileHeader {
    public short bfType;
    public int bfSize;
    public short bfReserved1;
    public short bfReserved2;
    public int bfOffBits;

    public BmpFileHeader() {
    }
  }
}
