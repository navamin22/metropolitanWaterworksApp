//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szsicod.print.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;

public class BitmapUtils {
  private static int[] p0 = new int[]{0, 128};
  private static int[] p1 = new int[]{0, 64};
  private static int[] p2 = new int[]{0, 32};
  private static int[] p3 = new int[]{0, 16};
  private static int[] p4 = new int[]{0, 8};
  private static int[] p5 = new int[]{0, 4};
  private static int[] p6 = new int[]{0, 2};
  private static int[][] Floyd16x16 = new int[][]{{0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 170}, {192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 234, 106}, {48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 26, 154}, {240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 122, 218, 90}, {12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 166}, {204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 230, 102}, {60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 22, 150}, {252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86}, {3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 169}, {195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 233, 105}, {51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 25, 153}, {243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, 217, 89}, {15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 165}, {207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101}, {63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 21, 149}, {254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 117, 213, 85}};

  public BitmapUtils() {
  }

  public static byte[] parseBmpToByte(Bitmap bitmap) {
    int height = bitmap.getHeight();
    int width = bitmap.getWidth();
    int bitWidth = (width + 7) / 8 * 8;
    int scaleHeight = bitWidth / width * height;
    bitmap = reSize(bitmap, bitWidth, scaleHeight);
    scaleHeight = bitmap.getHeight();
    bitWidth = bitmap.getWidth();
    System.out.println(scaleHeight);
    System.out.println(bitWidth);
    int[] data = new int[bitWidth * scaleHeight];
    int index = 0;

    for(int h = 0; h < scaleHeight; ++h) {
      for(int w = 0; w < bitWidth; ++w) {
        data[index] = bitmap.getPixel(w, h);
        ++index;
      }
    }

    byte[] dataVec = new byte[bitWidth * scaleHeight];
    format_K_dither16x16(data, bitWidth, scaleHeight, dataVec);
    dataVec = pixToEscRastBitImageCmd(dataVec, bitWidth, scaleHeight);
    return dataVec;
  }

  public static byte[] pixToEscRastBitImageCmd(byte[] src, int nWidth, int height) {
    byte[] data = new byte[8 + src.length / 8];
    data[0] = 29;
    data[1] = 118;
    data[2] = 48;
    data[3] = 0;
    data[4] = (byte)(nWidth / 8 % 256);
    data[5] = (byte)(nWidth / 8 / 256);
    data[6] = (byte)(height % 256);
    data[7] = (byte)(height / 256);
    int i = 8;

    for(int k = 0; i < data.length; ++i) {
      data[i] = (byte)(p0[src[k]] + p1[src[k + 1]] + p2[src[k + 2]] + p3[src[k + 3]] + p4[src[k + 4]] + p5[src[k + 5]] + p6[src[k + 6]] + src[k + 7]);
      k += 8;
    }

    return data;
  }

  private static void format_K_dither16x16_int(int[] orgpixels, int xsize, int ysize, int[] despixels) {
    int k = 0;

    for(int y = 0; y < ysize; ++y) {
      for(int x = 0; x < xsize; ++x) {
        if ((orgpixels[k] & 255) > Floyd16x16[x & 15][y & 15]) {
          despixels[k] = -1;
        } else {
          despixels[k] = -16777216;
        }

        ++k;
      }
    }

  }

  private static void format_K_dither16x16(int[] orgpixels, int xsize, int ysize, byte[] despixels) {
    int k = 0;

    for(int y = 0; y < ysize; ++y) {
      for(int x = 0; x < xsize; ++x) {
        if ((orgpixels[k] & 255) > Floyd16x16[x & 15][y & 15]) {
          despixels[k] = 0;
        } else {
          despixels[k] = 1;
        }

        ++k;
      }
    }

  }

  private static int changePointPx(byte[] arry) {
    int v = 0;

    for(int j = 0; j < arry.length; ++j) {
      if (arry[j] == 1) {
        v |= 1 << j;
      }
    }

    return v;
  }

  public static Bitmap reSize(Bitmap bitmap, int reWidth, int reHeight) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    float scaleWidth = (float)reWidth / (float)width;
    float scaleHeight = (float)reHeight / (float)height;
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    return resizedBitmap;
  }

  public static Bitmap reSizeByWidth(Bitmap bitmap, int reWidth) {
    int width = bitmap.getWidth();
    float scaleWidth = (float)reWidth / (float)width;
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleWidth);
    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, bitmap.getHeight(), matrix, true);
    return resizedBitmap;
  }

  public static Bitmap convertGreyImgByFloyd(Bitmap img) {
    int width = img.getWidth();
    int height = img.getHeight();
    int[] pixels = new int[width * height];
    img.getPixels(pixels, 0, width, 0, 0, width, height);
    int[] gray = new int[height * width];

    int e;
    int i;
    int j;
    int g;
    for(e = 0; e < height; ++e) {
      for(i = 0; i < width; ++i) {
        j = pixels[width * e + i];
        g = (j & 16711680) >> 16;
        gray[width * e + i] = g;
      }
    }


    for(i = 0; i < height; ++i) {
      for(j = 0; j < width; ++j) {
        g = gray[width * i + j];
        if (g >= 128) {
          pixels[width * i + j] = -1;
          e = g - 255;
        } else {
          pixels[width * i + j] = -16777216;
          e = g - 0;
        }

        if (j < width - 1 && i < height - 1) {
          gray[width * i + j + 1] += 3 * e / 8;
          gray[width * (i + 1) + j] += 3 * e / 8;
          gray[width * (i + 1) + j + 1] += e / 4;
        } else if (j == width - 1 && i < height - 1) {
          gray[width * (i + 1) + j] += 3 * e / 8;
        } else if (j < width - 1 && i == height - 1) {
          gray[width * i + j + 1] += e / 4;
        }
      }
    }

    Bitmap mBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
    mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    return mBitmap;
  }

  public static Bitmap toGrays(Bitmap bitmap) {
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0.0F);
    ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
    Bitmap gray = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.RGB_565);
    Canvas canvas = new Canvas(gray);
    Paint paint = new Paint(1);
    paint.setStyle(Style.STROKE);
    paint.setColorFilter(colorMatrixColorFilter);
    canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
    return gray;
  }
}
