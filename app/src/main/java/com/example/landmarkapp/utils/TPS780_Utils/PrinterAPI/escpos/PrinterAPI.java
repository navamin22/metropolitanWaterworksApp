//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.escpos;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.io.InterfaceAPI;
import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.log.DiskLogAdapter;
import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class PrinterAPI {
    public static final int OFF_LINE = 1;
    public static final int FEEDING = 2;
    public static final int FEEDING_10 = 16;
    public static final int HAPPENERROR = 4;
    public static final int OUTOFPAPER_8 = 8;
    public static final int BOXOPEN = 32;
    public static final int CORRECTINGERROR = 64;
    public static final int NOTCORRECTERROR = 128;
    public static final int MACHINEERROR = 256;
    public static final int PAPERFEW = 512;
    public static final int OUTOFPAPER = 1024;
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    public static final int ERR_PARAM = -2;
    public static final int STATE_NORMAL = 303174166;
    public static final int STATE_CoverOpen = 1024;
    public static final int STATE_PaperNearEnd = 201326592;
    public static final int STATE_PaperEnd = 1610612736;
    public static final int IMPACT_PRINTER = 0;
    public static final int STRINGLONG = -3;
    public static final int NOSUPPROT = -4;
    public static final int THERMAL_PRINTER = 1;
    private InterfaceAPI mIO = null;
    private byte[] mCmd = new byte[48];
    private boolean output = false;
    private static volatile PrinterAPI mPrinterAPI;
    private static int[][] Floyd16x16 = new int[][]{{0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 170}, {192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 234, 106}, {48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 26, 154}, {240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 122, 218, 90}, {12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 166}, {204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 230, 102}, {60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 22, 150}, {252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86}, {3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 169}, {195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 233, 105}, {51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 25, 153}, {243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, 217, 89}, {15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 165}, {207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101}, {63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 21, 149}, {254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 117, 213, 85}};
    private static int[][] Floyd8x8 = new int[][]{{0, 32, 8, 40, 2, 34, 10, 42}, {48, 16, 56, 24, 50, 18, 58, 26}, {12, 44, 4, 36, 14, 46, 6, 38}, {60, 28, 52, 20, 62, 30, 54, 22}, {3, 35, 11, 43, 1, 33, 9, 41}, {51, 19, 59, 27, 49, 17, 57, 25}, {15, 47, 7, 39, 13, 45, 5, 37}, {63, 31, 55, 23, 61, 29, 53, 21}};

    private PrinterAPI() {
    }

    public static synchronized PrinterAPI getInstance() {
        if (null == mPrinterAPI) {
            Class var0 = PrinterAPI.class;
            synchronized (PrinterAPI.class) {
                if (null == mPrinterAPI) {
                    mPrinterAPI = new PrinterAPI();
                }
            }
        }

        return mPrinterAPI;
    }

    public void setOutput(boolean output) {
        Logger.i("设置 日志文件输出" + output, new Object[0]);
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState)) {
            if (output) {
                Logger.clearLogAdapters();
                Logger.addLogAdapter(new DiskLogAdapter());
            } else {
                Logger.clearLogAdapters();
            }
        }

    }

    private static byte[] fileGetBytes(String filePath) {
        byte[] buffer = null;

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];

            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return buffer;
    }

    private int GetSum(byte[] aryData) {
        int sum = 0;

        for (int i = 0; i < aryData.length; ++i) {
            sum += aryData[i] & 255;
        }

        sum &= 65535;
        return sum;
    }

    public int updateFirmware(File file) {
        Logger.i("固件升级", new Object[0]);
        byte[] filebytes = fileGetBytes(file.getAbsolutePath());
        byte[] writeBytes = new byte[4096];
        byte[] readBytes = new byte[10];
        int fileSize = filebytes.length;
        int writeSize = 0;
        writeBytes[0] = 28;
        writeBytes[1] = 72;
        writeBytes[2] = 85;
        writeBytes[3] = 68;
        writeBytes[4] = 51;
        writeBytes[5] = (byte) ((fileSize + 4095) / 4096 * 4);
        this.verifyWriteIO(writeBytes, 0, 6, 16000);

        for (int i = 0; i < 4096; ++i) {
            writeBytes[i] = -1;
        }

        int readSize = 2;

        for (int writeLength = 4096; fileSize - writeSize > 0; writeSize += 4096) {
            if (fileSize - writeSize < 4096) {
                writeLength = fileSize - writeSize;
            }

            System.arraycopy(filebytes, writeSize, writeBytes, 0, writeLength);
            this.verifyWriteIO(writeBytes, 0, 4096, 16000);
            int sum = this.GetSum(writeBytes);
            writeBytes[0] = (byte) (sum & 255);
            writeBytes[1] = (byte) (sum >> 8 & 255);
            this.verifyWriteIO(writeBytes, 0, 2, 16000);

            int i;
            for (i = 0; i < 10; ++i) {
                readBytes[i] = 0;
            }

            if (readSize != this.readIO(readBytes, 0, readSize, 16000)) {
                Logger.i("固件升级 失败 未能读取数据", new Object[0]);
                return -1;
            }

            if (readBytes[0] != writeBytes[0] || readBytes[1] != writeBytes[1]) {
                Logger.i("固件升级  数据校验失败", new Object[0]);
                return -1;
            }

            for (i = 0; i < 4096; ++i) {
                writeBytes[i] = -1;
            }
        }

        return 0;
    }

    public synchronized int connect(InterfaceAPI io) {
        if (!io.isOpen()) {
            if (io.openDevice() != 0) {
                return -2;
            }

            if (!io.isOpen()) {
                return -1;
            }
        }

        this.mIO = io;
        Logger.i("设备已经连接", new Object[0]);
        return 0;
    }

    public synchronized int disconnect() {
        if (this.mIO == null) {
            return -1;
        } else if (this.mIO.isOpen() && this.mIO.closeDevice() != 0) {
            return -1;
        } else {
            this.mIO = null;
            Logger.i("设备已经断开连接", new Object[0]);
            return 0;
        }
    }

    public synchronized int sendOrder(byte[] cmd) {
        Logger.i("发送自定义命令", new Object[0]);
        return this.verifyWriteIO(cmd, 0, cmd.length, 2000);
    }

    public synchronized int writeIO(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime) {
        Logger.i(" writeIO 数据开始传输" + Arrays.toString(writeBuffer), new Object[0]);
        if (this.mIO == null) {
            return -1;
        } else {
            int ret = this.mIO.writeBuffer(writeBuffer, offsetSize, writeSize, waitTime);
            if (ret < 0) {
                return -1;
            } else {
                Logger.i("数据传输结束", new Object[0]);
                return ret;
            }
        }
    }

    private synchronized int verifyWriteIO(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime) {
        return writeSize != this.writeIO(writeBuffer, offsetSize, writeSize, waitTime) ? -1 : 0;
    }

    public synchronized int readIO(byte[] readBuffer, int offsetSize, int readSize, int waitTime) {
        if (this.mIO == null) {
            Logger.i("mio==null ", new Object[0]);
            return -1;
        } else {
            int ret = this.mIO.readBuffer(readBuffer, offsetSize, readSize, waitTime);
            if (ret < 0) {
                Logger.i("数据读取不到", new Object[0]);
                return -1;
            } else {
                Logger.i(" readIO 数据读取为" + Arrays.toString(readBuffer), new Object[0]);
                return ret;
            }
        }
    }

    public synchronized String getPrinterVersionNew() {
        byte[] version = new byte[4];
        this.mCmd[0] = 16;
        this.mCmd[1] = 4;
        this.mCmd[2] = 6;
        this.verifyWriteIO(this.mCmd, 0, 3, 5000);
        int readSize = 4;
        if (readSize != this.readIO(this.mCmd, 0, readSize, 5000)) {
            return "-1";
        } else {
            version[0] = this.mCmd[0];
            version[1] = this.mCmd[1];
            version[2] = this.mCmd[2];
            version[3] = this.mCmd[3];
            return new String(version);
        }
    }

    public synchronized String getPrinterVersion() {
        byte[] version = new byte[4];
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 65;
        this.mCmd[3] = 2;
        this.mCmd[4] = 0;
        this.mCmd[5] = 0;
        this.mCmd[6] = 6;
        this.verifyWriteIO(this.mCmd, 0, 7, 2000);
        int readSize = 4;
        if (readSize != this.readIO(this.mCmd, 0, readSize, 2000)) {
            return "-1231414";
        } else {
            version[0] = this.mCmd[0];
            version[1] = this.mCmd[1];
            version[2] = this.mCmd[2];
            version[3] = this.mCmd[3];
            return new String(version);
        }
    }

    public synchronized void init() {
        Logger.i("初始化 打印机", new Object[0]);
        this.mCmd[0] = 27;
        this.mCmd[1] = 64;
        this.writeIO(this.mCmd, 0, 2, 2000);
        this.fontSizeSet(0);
        this.setFontStyle(0);
    }

    public synchronized int initAllPrinter(int n) {
        Logger.i("清除缓冲区和初始化", new Object[0]);
        this.mCmd[0] = 27;
        this.mCmd[1] = 64;
        this.mCmd[2] = 16;
        this.mCmd[3] = 4;
        this.mCmd[4] = (byte) n;
        return this.writeIO(this.mCmd, 0, 5, 2000);
    }

    public synchronized int getStatus() {
        Logger.i("获取状态", new Object[0]);
        byte[] statusD = new byte[4];
        this.mCmd[0] = 16;
        this.mCmd[1] = 4;
        this.mCmd[2] = 1;
        this.mCmd[3] = 16;
        this.mCmd[4] = 4;
        this.mCmd[5] = 2;
        this.mCmd[6] = 16;
        this.mCmd[7] = 4;
        this.mCmd[8] = 3;
        this.mCmd[9] = 16;
        this.mCmd[10] = 4;
        this.mCmd[11] = 4;
        int writeSize = 12;
        if (this.verifyWriteIO(this.mCmd, 0, writeSize, 500) != 0) {
            return -1;
        } else {
            this.readIO(statusD, 0, 4, 1000);
            int getStatus = 0;

            try {
                Logger.i("getStatus: " + Arrays.toString(statusD), new Object[0]);
                getStatus = this.parseStatus(statusD);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            return getStatus;
        }
    }

    public synchronized int printString(String text, String charsetName, boolean isFeed) throws UnsupportedEncodingException {
        Logger.i("打印文本", new Object[0]);
        byte[] textData = text.getBytes(charsetName);
        this.verifyWriteIO(textData, 0, textData.length, 2000);
        return isFeed ? this.printFeed() : 0;
    }

    public synchronized int printRasterBitmap(Bitmap bitmap) throws IOException {
        Bmp bp = new Bmp(bitmap, (short) 1);
        byte[] data = bp.getData();
        int outDataMaxSize = data.length + 128;
        byte[] outData = new byte[outDataMaxSize];
        FormatCMD fc = new FormatCMD();
        int outSize = fc.jbitmap2cmd(data, data.length, outData, outDataMaxSize);
        return outSize < 0 ? -1 : this.verifyWriteIO(outData, 0, outSize, outSize);
    }

    public synchronized int printQRCode2(String text) {
        int len = text.length();
        if (len <= 0) {
            return -2;
        } else {
            byte[] qrcode = text.getBytes(Charset.forName("GBK"));
            if (qrcode.length != len) {
                return -2;
            } else {
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = (byte) ((len + 3) % 256);
                this.mCmd[4] = (byte) ((len + 3) / 256);
                this.mCmd[5] = 49;
                this.mCmd[6] = 80;
                this.mCmd[7] = 48;
                if (this.verifyWriteIO(this.mCmd, 0, 8, 2000) != 0) {
                    return -1;
                } else if (this.verifyWriteIO(qrcode, 0, len, 2000) != 0) {
                    return -1;
                } else {
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 3;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 49;
                    this.mCmd[6] = 81;
                    this.mCmd[7] = 48;
                    return this.verifyWriteIO(this.mCmd, 0, 8, 2000) != 0 ? -1 : 0;
                }
            }
        }
    }

    public synchronized int printQRCode(String text, int modeSize, boolean isCut) {
        int len = text.length();
        if (len <= 0) {
            return -1;
        } else {
            byte[] qrcode = text.getBytes();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < qrcode.length; ++i) {
                sb.append(qrcode[i] + " ");
            }

            this.mCmd[0] = 29;
            this.mCmd[1] = 40;
            this.mCmd[2] = 107;
            this.mCmd[3] = 4;
            this.mCmd[4] = 0;
            this.mCmd[5] = 49;
            this.mCmd[6] = 65;
            this.mCmd[7] = 49;
            this.mCmd[8] = 0;
            if (this.verifyWriteIO(this.mCmd, 0, 9, 2000) != 0) {
                return -1;
            } else {
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 67;
                if (modeSize >= 1 && modeSize <= 16) {
                    this.mCmd[7] = (byte) modeSize;
                } else {
                    this.mCmd[7] = 6;
                }

                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 69;
                this.mCmd[7] = 48;
                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                byte[] qrCodeHeader = new byte[]{29, 40, 107, (byte) (qrcode.length + 3 & 255), (byte) (qrcode.length + 3 >> 8 & 255), 49, 80, 48};
                ArrayList<Byte> copy = new ArrayList();

                int i;
                for (i = 0; i < qrCodeHeader.length; ++i) {
                    copy.add(qrCodeHeader[i]);
                }

                for (i = 0; i < qrcode.length; ++i) {
                    copy.add(qrcode[i]);
                }

                byte[] qrcodeByte = new byte[copy.size()];


                for (i = 0; i < qrcodeByte.length; ++i) {
                    qrcodeByte[i] = (Byte) copy.get(i);
                }

                sb = new StringBuilder();

                for (i = 0; i < qrcodeByte.length; ++i) {
                    sb.append(qrcodeByte[i] + " ");
                }

                this.verifyWriteIO(qrcodeByte, 0, qrcodeByte.length, 2000);
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 81;
                this.mCmd[7] = 48;
                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                if (isCut) {
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 86;
                    this.mCmd[2] = 66;
                    this.mCmd[3] = 0;
                    return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
                } else {
                    return 0;
                }
            }
        }
    }

    public synchronized int printKanjiQRCode(String text, boolean isCut) {
        int len = text.length();
        if (len <= 0) {
            return -1;
        } else {
            byte[] qrcode = null;

            try {
                qrcode = text.getBytes("GBK");
            } catch (UnsupportedEncodingException var10) {
                var10.printStackTrace();
            }

            Log.i("info", "qrcode.length:" + qrcode.length);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < qrcode.length; ++i) {
                sb.append(qrcode[i] + " ");
            }

            Log.i("info", "qrcode:" + sb.toString());
            this.mCmd[0] = 29;
            this.mCmd[1] = 40;
            this.mCmd[2] = 107;
            this.mCmd[3] = 4;
            this.mCmd[4] = 0;
            this.mCmd[5] = 49;
            this.mCmd[6] = 65;
            this.mCmd[7] = 49;
            this.mCmd[8] = 0;
            if (this.verifyWriteIO(this.mCmd, 0, 9, 2000) != 0) {
                return -1;
            } else {
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 67;
                this.mCmd[7] = 3;
                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 69;
                this.mCmd[7] = 48;
                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                byte[] qrCodeHeader = new byte[]{29, 40, 107, 19, 1, 49, 80, 48};
                ArrayList<Byte> copy = new ArrayList();

                int i;
                for (i = 0; i < qrCodeHeader.length; ++i) {
                    copy.add(qrCodeHeader[i]);
                }

                for (i = 0; i < qrcode.length; ++i) {
                    copy.add(qrcode[i]);
                }

                byte[] qrcodeByte = new byte[copy.size()];


                for (i = 0; i < qrcodeByte.length; ++i) {
                    qrcodeByte[i] = (Byte) copy.get(i);
                }

                sb = new StringBuilder();

                for (i = 0; i < qrcodeByte.length; ++i) {
                    sb.append(qrcodeByte[i] + " ");
                }

                this.verifyWriteIO(qrcodeByte, 0, qrcodeByte.length, 2000);
                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 49;
                this.mCmd[6] = 81;
                this.mCmd[7] = 48;
                this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                if (isCut) {
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 86;
                    this.mCmd[2] = 66;
                    this.mCmd[3] = 0;
                    return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
                } else {
                    return 0;
                }
            }
        }
    }

    public synchronized int printPDFCode(String text, int errorSize, int hSize, int vSize) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 64;
        if (this.verifyWriteIO(this.mCmd, 0, 2, 2000) != 0) {
            return -1;
        } else {
            int len = text.length();
            if (len <= 0) {
                return -1;
            } else {
                byte[] qrcode = text.getBytes();
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < qrcode.length; ++i) {
                    sb.append(qrcode[i] + " ");
                }

                this.mCmd[0] = 29;
                this.mCmd[1] = 40;
                this.mCmd[2] = 107;
                this.mCmd[3] = 3;
                this.mCmd[4] = 0;
                this.mCmd[5] = 48;
                this.mCmd[6] = 65;
                this.mCmd[7] = 0;
                if (vSize > 0 && vSize < 9) {
                    this.mCmd[7] = (byte) vSize;
                }

                if (this.verifyWriteIO(this.mCmd, 0, 8, 2000) != 0) {
                    return -1;
                } else {
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 3;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 48;
                    this.mCmd[6] = 66;
                    this.mCmd[7] = 0;
                    if (hSize > 0 && hSize < 9) {
                        this.mCmd[7] = (byte) hSize;
                    }

                    this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 3;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 48;
                    this.mCmd[6] = 67;
                    this.mCmd[7] = 2;
                    this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 3;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 48;
                    this.mCmd[6] = 68;
                    this.mCmd[7] = 2;
                    this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 4;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 48;
                    this.mCmd[6] = 69;
                    this.mCmd[7] = 48;
                    switch (errorSize) {
                        case 4:
                            this.mCmd[8] = 52;
                            break;
                        case 5:
                            this.mCmd[8] = 53;
                            break;
                        case 6:
                            this.mCmd[8] = 54;
                            break;
                        case 7:
                            this.mCmd[8] = 55;
                            break;
                        case 8:
                            this.mCmd[8] = 56;
                            break;
                        default:
                            this.mCmd[8] = 56;
                    }

                    this.verifyWriteIO(this.mCmd, 0, 9, 2000);
                    byte[] qrCodeHeader = new byte[]{29, 40, 107, (byte) ((qrcode.length + 3) % 256), (byte) ((qrcode.length + 3) / 256), 48, 80, 48};
                    ArrayList<Byte> copy = new ArrayList();

                    int i;
                    for (i = 0; i < qrCodeHeader.length; ++i) {
                        copy.add(qrCodeHeader[i]);
                    }

                    for (i = 0; i < qrcode.length; ++i) {
                        copy.add(qrcode[i]);
                    }

                    byte[] qrcodeByte = new byte[copy.size()];


                    for (i = 0; i < qrcodeByte.length; ++i) {
                        qrcodeByte[i] = (Byte) copy.get(i);
                    }

                    Log.i("info", "qrCodeHeader.length:" + qrCodeHeader.length);
                    sb = new StringBuilder();

                    for (i = 0; i < qrcodeByte.length; ++i) {
                        sb.append(qrcodeByte[i] + " ");
                    }

                    Log.i("info", "qrcodeByte:" + sb.toString());
                    this.verifyWriteIO(qrcodeByte, 0, qrcodeByte.length, 2000);
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 107;
                    this.mCmd[3] = 3;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = 48;
                    this.mCmd[6] = 81;
                    this.mCmd[7] = 48;
                    this.verifyWriteIO(this.mCmd, 0, 8, 2000);
                    return 0;
                }
            }
        }
    }

    public synchronized int setPrintQRCodeType(int type) {
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 107;
        this.mCmd[3] = 4;
        this.mCmd[4] = 0;
        this.mCmd[5] = 49;
        this.mCmd[6] = 65;
        this.mCmd[7] = (byte) (48 + type);
        this.mCmd[8] = 0;
        return this.verifyWriteIO(this.mCmd, 0, 9, 2000);
    }

    public synchronized int setPrintQRCodeSize(int size) {
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 107;
        this.mCmd[3] = 3;
        this.mCmd[4] = 0;
        this.mCmd[5] = 49;
        this.mCmd[6] = 67;
        this.mCmd[7] = (byte) size;
        return this.verifyWriteIO(this.mCmd, 0, 8, 2000);
    }

    public synchronized int setPrintQRCodeErrCL(int level) {
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 107;
        this.mCmd[3] = 3;
        this.mCmd[4] = 0;
        this.mCmd[5] = 49;
        this.mCmd[6] = 69;
        this.mCmd[7] = (byte) (71 + level);
        return this.verifyWriteIO(this.mCmd, 0, 8, 2000);
    }

    public synchronized int printFeed() {
        this.mCmd[0] = 10;
        return this.verifyWriteIO(this.mCmd, 0, 1, 2000);
    }

    public synchronized int printBackFlow(int n) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 75;
        this.mCmd[2] = (byte) n;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    public synchronized int printAndBackToStd() {
        this.mCmd[0] = 12;
        return this.verifyWriteIO(this.mCmd, 0, 1, 2000);
    }

    public synchronized int printerRequestsRealTime(int type) {
        if (type != 1 && type != 2) {
            return -2;
        } else {
            this.mCmd[0] = 16;
            this.mCmd[1] = 5;
            this.mCmd[2] = (byte) type;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        }
    }

    public synchronized int setFontStyle(int type) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 33;
        this.mCmd[2] = (byte) type;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    public synchronized int setCharRightSpace(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 32;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setEnableUnderLine(int enable) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 45;
        this.mCmd[2] = (byte) enable;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    public synchronized int setNullPointer() {
        byte[] cmd = new byte[]{0, 0};
        return this.verifyWriteIO(cmd, 0, 2, 2000);
    }

    public synchronized int setDefaultLineSpace() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 50;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int setLineSpace(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 51;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setEmphasizedMode(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 69;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setPrintColorSize(int n) {
        Logger.i("浓度设置——" + n, new Object[0]);
        byte[] cmd = new byte[]{29, 40, 66, 2, 0, 1, 0};
        switch (n) {
            case 1:
                cmd[6] = -13;
                break;
            case 2:
                cmd[6] = -1;
                break;
            case 3:
                cmd[6] = -5;
                break;
            case 4:
                cmd[6] = -9;
                break;
            default:
                return 1;
        }

        return this.verifyWriteIO(cmd, 0, cmd.length, 2000);
    }

    public synchronized int setPrintColorSize2(int n) {
        Log.i("info", "浓度设置——" + n);
        byte[] cmd = new byte[]{27, -3, 0, 0};
        switch (n) {
            case 1:
                cmd[2] = 1;
                break;
            case 2:
                cmd[2] = 2;
                break;
            case 3:
                cmd[2] = 3;
                break;
            default:
                return 1;
        }

        cmd[3] = 10;
        return this.verifyWriteIO(cmd, 0, cmd.length, 2000);
    }

    public synchronized int setOverlapMode(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 71;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int printAndFeedPaper(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 74;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setInterCharSet(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 82;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int fullCut() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 105;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int halfCut() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 109;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int setAlignMode(int type) {
        if (type != 0 && type != 1 && type != 2 && type != 48 && type != 49 && type != 50) {
            return -2;
        } else {
            this.mCmd[0] = 27;
            this.mCmd[1] = 97;
            this.mCmd[2] = (byte) type;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        }
    }

    public synchronized int setPaperSensor(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 99;
            this.mCmd[2] = 51;
            this.mCmd[3] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setSensorToStopPrint(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 99;
            this.mCmd[2] = 52;
            this.mCmd[3] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setEnablePanelButton(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 99;
            this.mCmd[2] = 53;
            this.mCmd[3] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int printAndFeedLine(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 100;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setCharCodeTable(int n) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 116;
        if ((n < 0 || n > 5) && (n < 16 || n > 19) && n != 255) {
            return -2;
        } else {
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        }
    }

    public synchronized int feedToStartPos() {
        this.mCmd[0] = 29;
        this.mCmd[1] = 12;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int setCharSize(int hsize, int vsize) {
        int Width = 0;
        if (hsize == 0) {
            Width = 0;
        }

        if (hsize == 1) {
            Width = 16;
        }

        if (hsize == 2) {
            Width = 32;
        }

        if (hsize == 3) {
            Width = 48;
        }

        if (hsize == 4) {
            Width = 64;
        }

        if (hsize == 5) {
            Width = 80;
        }

        if (hsize == 6) {
            Width = 96;
        }

        if (hsize == 7) {
            Width = 112;
        }

        if (Width <= 0) {
            Width = 0;
        }

        if (Width >= 112) {
            Width = 112;
        }

        if (vsize <= 0) {
            vsize = 0;
        }

        if (vsize >= 7) {
            vsize = 7;
        }

        int Mul = Width + vsize;
        this.mCmd[0] = 29;
        this.mCmd[1] = 33;
        this.mCmd[2] = (byte) Mul;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    public synchronized int doTestPrint(int n, int m) {
        if (n >= 0 && n <= 50) {
            if (n > 2 && n < 48) {
                return -2;
            } else if (m >= 1 && m <= 51) {
                if (m > 3 && m < 49 && m != 6) {
                    return -2;
                } else {
                    this.mCmd[0] = 29;
                    this.mCmd[1] = 40;
                    this.mCmd[2] = 65;
                    this.mCmd[3] = 2;
                    this.mCmd[4] = 0;
                    this.mCmd[5] = (byte) n;
                    this.mCmd[6] = (byte) m;
                    return this.verifyWriteIO(this.mCmd, 0, 7, 2000);
                }
            } else {
                return -2;
            }
        } else {
            return -2;
        }
    }

    public synchronized int setLeftMargin(int nL, int nH) {
        if (nL >= 0 && nL <= 255 && nH >= 0 && nH <= 255) {
            this.mCmd[0] = 29;
            this.mCmd[1] = 76;
            this.mCmd[2] = (byte) nL;
            this.mCmd[3] = (byte) nH;
            return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int cutPaper(int m, int n) {
        this.printFeed();

        if (m != 0 && m != 1 && m != 48 && m != 49 && m != 66) {
            return -2;
        } else {
            this.mCmd[0] = 29;
            this.mCmd[1] = 86;
            byte k;
            if (m == 66) {
                if (n < 0 || n > 255) {
                    return -2;
                }

                this.mCmd[2] = (byte) m;
                this.mCmd[3] = (byte) n;
                k = 4;
            } else {
                this.mCmd[2] = (byte) m;
                k = 3;
            }

            return this.verifyWriteIO(this.mCmd, 0, k, 2000);
        }
    }

    public synchronized int setPrnAreaWidth(int nL, int nH) {
        if (nL >= 0 && nL <= 255 && nH >= 0 && nH <= 255) {
            this.mCmd[0] = 29;
            this.mCmd[1] = 87;
            this.mCmd[2] = (byte) nL;
            this.mCmd[3] = (byte) nH;
            return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setEnableSmoothPrn(int n) {
        if (n >= 0 && n <= 255) {
            this.mCmd[0] = 29;
            this.mCmd[1] = 98;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int setBarCodeHeight(int n) {
        if (n >= 1 && n <= 255) {
            this.mCmd[0] = 29;
            this.mCmd[1] = 104;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int printBarCode(int m, int n, String barcode) throws UnsupportedEncodingException {
        int j = 0;
        int k = 0;
        int i = barcode.length();
        byte[] data = barcode.getBytes("gbk");
        if (i <= 0) {
            return -2;
        } else if (m < 0 || m > 75 || m > 8 && m < 65) {
            return -2;
        } else if (n >= 1 && n <= 255) {
            int total = i + 4;
            byte[] cmd = new byte[total];
            cmd[0] = 29;
            cmd[1] = 107;
            cmd[2] = (byte) m;
            if (m >= 65 && m <= 75) {
                Log.i(TAG, "printBarCode: " + m + "n的值是" + n);
                cmd[3] = (byte) n;
            }

            byte code;
            label571:
            switch (m) {
                case 0:
                    if (i >= 11 && i <= 12) {
                        while (true) {
                            if (j >= i) {
                                break label571;
                            }

                            code = data[k];
                            if (code < 48 || code > 57) {
                                return -4;
                            }

                            cmd[j + 3] = code;
                            ++j;
                            ++k;
                        }
                    }

                    return -3;
                case 1:
                    if (i >= 11 && i <= 12) {
                        while (true) {
                            if (j >= i) {
                                break label571;
                            }

                            code = data[k];
                            if (code < 48 || code > 57) {
                                return -4;
                            }

                            cmd[j + 3] = code;
                            ++j;
                            ++k;
                        }
                    }

                    return -3;
                case 2:
                    if (i < 12 || i > 13) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label571;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 3] = code;
                        ++j;
                        ++k;
                    }
                case 3:
                    if (i >= 7 && i <= 8) {
                        while (true) {
                            if (j >= i) {
                                break label571;
                            }

                            code = data[k];
                            if (code < 48 || code > 57) {
                                return -4;
                            }

                            cmd[j + 3] = code;
                            ++j;
                            ++k;
                        }
                    }

                    return -3;
                case 4:
                    if (i >= 1 && i <= 255) {
                        while (true) {
                            if (j >= i) {
                                break label571;
                            }

                            code = data[k];
                            if (code != 32 && code != 36 && code != 37 && (code < 45 || code > 57) && (code < 65 || code > 90) && code != 43) {
                                return -4;
                            }

                            cmd[j + 3] = code;
                            ++j;
                            ++k;
                        }
                    }

                    return -3;
                case 5:
                    if (i < 1 || i % 2 != 0 || i > 255) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label571;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 3] = code;
                        ++j;
                        ++k;
                    }
                case 6:
                    if (i >= 1 && i <= 255) {
                        while (true) {
                            if (j >= i) {
                                break label571;
                            }

                            code = data[k];
                            if (code != 36 && (code < 45 || code > 58) && (code < 65 || code > 90) && code != 43) {
                                return -4;
                            }

                            cmd[j + 3] = code;
                            ++j;
                            ++k;
                        }
                    }

                    return -3;
                case 7:
                    if (i < 12 && i > 13) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label571;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 3] = code;
                        ++j;
                        ++k;
                    }
                case 8:
                    if (i < 7 || i > 8) {
                        return -3;
                    }

                    while (j < i) {
                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 3] = code;
                        ++j;
                        ++k;
                    }
            }

            label409:
            switch (m) {
                case 65:
                    if (i < 11 || i > 12) {
                        return -3;
                    }

                    while (j < i) {
                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 66:
                    if (i < 11 || i > 12) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 67:
                    if (i < 12 && i > 13) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 68:
                    if (i < 7 || i > 8) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 69:
                    if (i < 1 || i > 255) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code != 32 && code != 36 && code != 37 && (code < 45 || code > 57) && (code < 65 || code > 90) && code != 43) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 70:
                    if (i < 1 || i > 255 || i % 2 != 0) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 71:
                    if (i < 1 || i > 255) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code != 36 && (code < 45 || code > 58) && (code < 65 || code > 90) && code != 43) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 72:
                    if (i < 1 || i > 255) {
                        return -3;
                    }

                    while (j < i) {
                        code = data[k];
                        if (code > 127 && code < 0) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                    break;
                case 73:
                    if (i >= 1 && i <= 255) {
                        Log.i(TAG, "printBarCode: " + j);
                        while (j < i) {
                            code = data[k];
                            if (code > 127 && code < 0) {
                                return -4;
                            }

                            cmd[j + 4] = code;
                            ++j;
                            ++k;
                        }

                        return this.printCode128(barcode);
                    }

                    return -3;
                case 74:
                    if (i < 12 || i > 13) {
                        return -3;
                    }

                    while (true) {
                        if (j >= i) {
                            break label409;
                        }

                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
                case 75:
                    if (i < 7 || i > 8) {
                        return -3;
                    }

                    while (j < i) {
                        code = data[k];
                        if (code < 48 || code > 57) {
                            return -4;
                        }

                        cmd[j + 4] = code;
                        ++j;
                        ++k;
                    }
            }

            if (m >= 0 && m <= 8) {
                cmd[j + 3] = 0;
            }

            return this.verifyWriteIO(cmd, 0, total, 2000);
        } else {
            return -2;
        }
    }

    public synchronized int getPrinterStatus(int n) {
        if (n != 1 && n != 49) {
            return -2;
        } else {
            this.mCmd[0] = 29;
            this.mCmd[1] = 114;
            this.mCmd[2] = (byte) n;
            if (this.verifyWriteIO(this.mCmd, 0, 3, 2000) != 0) {
                return -1;
            } else if (1 != this.readIO(this.mCmd, 0, 1, 2000)) {
                return -1;
            } else {
                int ret = this.mCmd[0];
                return ret;
            }
        }
    }

    public synchronized int setBarCodeWidth(int n) {
        if (n >= 2 && n <= 16) {
            this.mCmd[0] = 29;
            this.mCmd[1] = 119;
            this.mCmd[2] = (byte) n;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            return -1;
        }
    }

    public synchronized int pagePrintAndBack2Standard() {
        this.mCmd[0] = 12;
        return this.verifyWriteIO(this.mCmd, 0, 1, 2000);
    }

    public synchronized int pageRemoveAllData() {
        this.mCmd[0] = 24;
        return this.verifyWriteIO(this.mCmd, 0, 1, 2000);
    }

    public synchronized int pagePrint() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 12;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int pageMode() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 76;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int standardMode() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 83;
        return this.verifyWriteIO(this.mCmd, 0, 2, 2000);
    }

    public synchronized int pageSelectDirection(int n) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 84;
        this.mCmd[2] = (byte) n;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    public synchronized int setRotate(int n) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 86;
        this.mCmd[2] = (byte) n;
        return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public synchronized int cachu() {
        this.mCmd[0] = 27;
        this.mCmd[1] = -3;
        this.mCmd[2] = -3;
        this.mCmd[3] = 102;
        this.mCmd[4] = 108;
        this.mCmd[5] = 97;
        this.mCmd[6] = 115;
        this.mCmd[7] = 104;
        this.mCmd[8] = 69;
        return this.verifyWriteIO(this.mCmd, 0, 9, 200);
    }

    public synchronized int set58mm() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 78;
        this.mCmd[2] = 10;
        this.mCmd[3] = 1;
        return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
    }

    public synchronized int set80mm() {
        this.mCmd[0] = 27;
        this.mCmd[1] = 78;
        this.mCmd[2] = 10;
        this.mCmd[3] = 0;
        return this.verifyWriteIO(this.mCmd, 0, 4, 2000);
    }

    public synchronized int chaxun() {
        this.mCmd[0] = 27;
        this.mCmd[1] = -3;
        this.mCmd[2] = -3;
        this.mCmd[3] = 102;
        this.mCmd[4] = 108;
        this.mCmd[5] = 97;
        this.mCmd[6] = 115;
        this.mCmd[7] = 104;
        this.mCmd[8] = 82;
        this.verifyWriteIO(this.mCmd, 0, 9, 2000);
        this.readIO(this.mCmd, 0, 10, 500);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; ++i) {
            sb.append(this.mCmd[i]);
        }

        return 0;
    }

    public synchronized int hexMsg(String msg) {
        if (msg.length() < 4) {
            return -1;
        } else if (msg.charAt(0) == '#' && msg.charAt(1) == '#' && msg.charAt(msg.length() - 1) == '#' && msg.charAt(msg.length() - 2) == '#') {
            msg = msg.substring(2);
            Logger.i("hexMsg" + msg, new Object[0]);
            msg = msg.substring(0, msg.length() - 2);
            Logger.i("hexMsg" + msg, new Object[0]);
            String[] msgByte = msg.split(" ");
            int hexNum = msgByte.length;
            Logger.i("hexMsg " + hexNum, new Object[0]);
            byte[] cmd = new byte[hexNum];

            for (int i = 0; i < hexNum; ++i) {
                int firstNum = 0;
                Log.i("info", msgByte[i]);
                if (msgByte[i].length() != 2) {
                    return -1;
                }

                for (int j = 0; j < 2; ++j) {
                    int stringNum;
                    if ('0' <= msgByte[i].charAt(j) && msgByte[i].charAt(j) <= '9') {
                        stringNum = Integer.parseInt(msgByte[i].charAt(j) + "");
                        Logger.i("hexMsg首位字符" + stringNum, new Object[0]);
                    } else {
                        switch (msgByte[i].charAt(j)) {
                            case 'a':
                                stringNum = 10;
                                break;
                            case 'b':
                                stringNum = 11;
                                break;
                            case 'c':
                                stringNum = 12;
                                break;
                            case 'd':
                                stringNum = 13;
                                break;
                            case 'e':
                                stringNum = 14;
                                break;
                            case 'f':
                                stringNum = 15;
                                break;
                            default:
                                return -1;
                        }
                    }

                    if (j == 0) {
                        firstNum = stringNum * 16;
                        Logger.i("hexMsgj=0,stringNum:" + firstNum, new Object[0]);
                    } else if (j == 1) {
                        Logger.i("hexMsgj=1,stringNum:" + (firstNum + stringNum), new Object[0]);
                        cmd[i] = (byte) (firstNum + stringNum);
                        Log.i("info", "cmd[" + i + "]:" + cmd[i]);
                    }
                }
            }

            if (this.verifyWriteIO(cmd, 0, cmd.length, 5000) < 0) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public synchronized int markLengthSet(int printerType, int ticketType, int Q0, int L0, int mPrintLen, int mTicketLen, int mCutLen) {
        int Q;
        if (ticketType == 0) {
            Q = mTicketLen - mPrintLen + Q0;
        } else {
            Q = mPrintLen + Q0;
        }

        int C;
        for (C = mCutLen + L0; Q > mTicketLen; Q -= mTicketLen) {
            ;
        }

        Q %= mTicketLen;
        C %= mTicketLen;
        double nTemp1;
        double nTemp2;
        if (printerType == 0) {
            nTemp1 = (double) ((float) Q * 1.0F) / 0.176D;
            nTemp2 = (double) ((float) C * 1.0F) / 0.176D;
        } else {
            nTemp1 = (double) ((float) Q * 1.0F) / 0.125D;
            nTemp2 = (double) ((float) C * 1.0F) / 0.125D;
        }

        int nQuot = (int) nTemp1 / 256;
        int nRem = (int) nTemp1 % 256;
        byte[] b = new byte[]{29, 40, 70, 4, 0, 1, 0, (byte) nRem, (byte) nQuot, 0};
        if (-1 == this.verifyWriteIO(b, 0, b.length, 2000)) {
            return -1;
        } else {
            nQuot = (int) nTemp2 / 256;
            nRem = (int) nTemp2 % 256;
            b[0] = 29;
            b[1] = 40;
            b[2] = 70;
            b[3] = 4;
            b[4] = 0;
            b[5] = 2;
            b[6] = 0;
            b[7] = (byte) nRem;
            b[8] = (byte) nQuot;
            return this.verifyWriteIO(b, 0, b.length, 2000);
        }
    }

    public synchronized int pointTest() {
        byte[] b = new byte[]{29, 12};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int fontSizeSet(int n) {
        //byte[] b = new byte[]{29, 12};
        this.mCmd[0] = 28;
        this.mCmd[1] = 33;
        this.mCmd[2] = (byte) n;
        return this.verifyWriteIO(mCmd, 0, 3, 2000);
    }

    public synchronized int chineseFontSet(int n) {
        byte[] b = new byte[]{28, 33};
        b[2] = (byte) n;
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int smallFontSizeSet() {
        byte[] b = new byte[]{28, 46, 27, 116, 0, 28, 38, 27, 33, 1, 10};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int markDefalut() {
        byte[] b = new byte[]{29, 40, 70, 4, 0, 1, 0, 0, 0, 0};
        this.verifyWriteIO(b, 0, b.length, 2000);
        b[0] = 29;
        b[1] = 40;
        b[2] = 70;
        b[3] = 4;
        b[4] = 0;
        b[5] = 2;
        b[6] = 0;
        b[7] = 0;
        b[8] = 0;
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int openMark() {
        byte[] b = new byte[]{27, 78, 7, 1};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int closeMark() {
        byte[] b = new byte[]{27, 78, 7, 0};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int cutMark() {
        byte[] b = new byte[]{29, 86, 66, 0};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int selfTestPage() {
        byte[] b = new byte[]{29, 40, 65, 2, 0, 0, 2};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int comeInHex() {
        byte[] b = new byte[]{29, 40, 65, 2, 0, 0, 1};
        return this.verifyWriteIO(b, 0, b.length, 2000);
    }

    public synchronized int setHorizontalTab(int[] k, boolean isSetDefault) {
        int length = k.length;
        if (length > 32) {
            return -4;
        } else if (isSetDefault) {
            this.mCmd[0] = 27;
            this.mCmd[1] = 68;
            this.mCmd[2] = 0;
            return this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        } else {
            byte[] tab = new byte[length + 4];
            int position = 0;
            position = position + 1;
            tab[position] = 27;
            tab[position] = 68;

            for (int i = 0; i < length; ++i) {
                tab[position] = (byte) k[i];
            }

            tab[position++] = 0;
            return this.verifyWriteIO(tab, 0, position, 2000);
        }
    }

    public synchronized int printTab() {
        this.mCmd[0] = 9;
        return this.verifyWriteIO(this.mCmd, 0, 1, 2000);
    }

    public synchronized int set2400() {
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 69;
        this.mCmd[3] = 8;
        this.mCmd[4] = 0;
        this.mCmd[5] = 11;
        this.mCmd[6] = 1;
        this.mCmd[7] = 34;
        this.mCmd[8] = 50;
        this.mCmd[9] = 52;
        this.mCmd[10] = 48;
        this.mCmd[11] = 48;
        this.mCmd[12] = 34;
        this.mCmd[13] = 10;
        return this.verifyWriteIO(this.mCmd, 0, 14, 2000);
    }

    public synchronized int lx() {
        this.mCmd[0] = 16;
        this.mCmd[1] = 4;
        this.mCmd[2] = 1;
        this.verifyWriteIO(this.mCmd, 0, 3, 2000);
        this.readIO(this.mCmd, 0, 4, 500);
        byte[] v = new byte[]{this.mCmd[0], this.mCmd[1], this.mCmd[2], this.mCmd[3]};
        return v[0] != 18 && v[0] != 22 ? 0 : 1;
    }

    public synchronized int openCashDrawer(int n, int t1, int t2) {
        this.mCmd[0] = 27;
        this.mCmd[1] = 112;
        this.mCmd[2] = (byte) n;
        this.mCmd[3] = (byte) t1;
        this.mCmd[4] = (byte) t2;
        return this.verifyWriteIO(this.mCmd, 0, 5, 2000);
    }

    public synchronized int printCode128(String a) {
        Log.i(TAG, "printCode128: " + a);
        byte[] data = a.getBytes();
        Log.i(TAG, "printCode128: " + data.length);
        this.mCmd[0] = 29;
        this.mCmd[1] = 107;
        this.mCmd[2] = 73;
        this.mCmd[3] = (byte) (data.length + 2);
        this.mCmd[4] = 123;
        this.mCmd[5] = 66;
        if (this.verifyWriteIO(this.mCmd, 0, 6, 2000) == 0) {
            this.verifyWriteIO(data, 0, data.length, 2000);
        }

        return 0;
    }

    public synchronized int changeBd(int bd) {
        this.mCmd[0] = 29;
        this.mCmd[1] = 40;
        this.mCmd[2] = 69;
        byte k;
        switch (bd) {
            case 9600:
                this.mCmd[3] = 8;
                this.mCmd[4] = 0;
                this.mCmd[5] = 11;
                this.mCmd[6] = 1;
                this.mCmd[7] = 34;
                this.mCmd[8] = 57;
                this.mCmd[9] = 54;
                this.mCmd[10] = 48;
                this.mCmd[11] = 48;
                this.mCmd[12] = 34;
                this.mCmd[13] = 10;
                k = 14;
                break;
            case 19200:
                this.mCmd[3] = 9;
                this.mCmd[4] = 0;
                this.mCmd[5] = 11;
                this.mCmd[6] = 1;
                this.mCmd[7] = 34;
                this.mCmd[8] = 49;
                this.mCmd[9] = 57;
                this.mCmd[10] = 50;
                this.mCmd[11] = 48;
                this.mCmd[12] = 48;
                this.mCmd[13] = 34;
                this.mCmd[13] = 10;
                k = 14;
                break;
            case 38400:
                this.mCmd[3] = 9;
                this.mCmd[4] = 0;
                this.mCmd[5] = 11;
                this.mCmd[6] = 1;
                this.mCmd[7] = 34;
                this.mCmd[8] = 51;
                this.mCmd[9] = 56;
                this.mCmd[10] = 52;
                this.mCmd[11] = 48;
                this.mCmd[12] = 48;
                this.mCmd[13] = 34;
                this.mCmd[14] = 10;
                k = 15;
                break;
            case 115200:
                this.mCmd[3] = 10;
                this.mCmd[4] = 0;
                this.mCmd[5] = 11;
                this.mCmd[6] = 1;
                this.mCmd[7] = 34;
                this.mCmd[8] = 49;
                this.mCmd[9] = 49;
                this.mCmd[10] = 53;
                this.mCmd[11] = 50;
                this.mCmd[12] = 48;
                this.mCmd[13] = 48;
                this.mCmd[14] = 34;
                this.mCmd[15] = 10;
                k = 16;
                break;
            default:
                return -1;
        }

        return this.verifyWriteIO(this.mCmd, 0, k, 500);
    }

    public boolean isConnect() {
        return this.mIO != null && this.mIO.openDevice() == 0;
    }

    private int parseStatus(byte[] mCmd) throws Exception {
        int nStatus = 0;
        if (mCmd[0] != 22 && mCmd[0] != 8) {
            if ((mCmd[0] & 8) > 0) {
                nStatus |= 1;
            }

            if ((mCmd[0] & 64) > 0) {
                nStatus |= 2;
            }
        }

        if (mCmd[1] != 18 && mCmd[1] != 0) {
            if ((mCmd[1] & 64) > 0) {
                nStatus |= 4;
            }

            if ((mCmd[1] & 32) > 0) {
                nStatus |= 8;
            }

            if ((mCmd[1] & 8) > 0) {
                nStatus |= 16;
            }

            if ((mCmd[1] & 4) > 0) {
                nStatus |= 32;
            }
        }

        if (mCmd[2] != 18 && mCmd[2] != 0) {
            if ((mCmd[2] & 64) > 0) {
                nStatus |= 64;
            }

            if ((mCmd[2] & 32) > 0) {
                nStatus |= 128;
            }

            if ((mCmd[2] & 4) > 0) {
                nStatus |= 256;
            }
        }

        if (mCmd[3] != 0 && mCmd[3] != 18) {
            if ((mCmd[3] & 96) > 0) {
                nStatus |= 1024;
            } else if ((mCmd[3] & 12) > 0) {
                nStatus |= 512;
            }
        }

        return nStatus;
    }

    private int parseStatusWith(byte[] mCmd) {
        int nStatus = 0;
        if ((mCmd[3] & 12) > 0) {
            nStatus |= 1024;
        } else if ((mCmd[3] & 3) > 0) {
            nStatus |= 512;
        }

        return nStatus;
    }

    public int downLoadNvBitmap(Bitmap[] bitmaps) {
        if (bitmaps.length <= 0) {
            return -1;
        } else {
            int total = 0;
            Bitmap[] var4 = bitmaps;
            int time = bitmaps.length;

            int var6;
            for (var6 = 0; var6 < time; ++var6) {
                Bitmap bitmap = var4[var6];
                total += bitmap.getWidth() * bitmap.getHeight();
            }

            byte[] data = new byte[16 + total];
            int k = 0;
            //k = k + 1;
            data[k] = 28;
            data[k++] = 113;
            data[k++] = (byte) bitmaps.length;
            Bitmap[] var27 = bitmaps;
            var6 = bitmaps.length;

            for (int var28 = 0; var28 < var6; ++var28) {
                Bitmap bitmap = var27[var28];
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int cols = (width + 7) / 8;
                int rows = (height + 7) / 8;
                int xl = cols % 256;
                int xh = cols / 256;
                int yl = rows % 256;
                int yh = rows / 256;
                Logger.i("ContentValues", new Object[]{"printNvBitmap: width" + width});
                Logger.i("ContentValues", new Object[]{"printNvBitmap: height" + height});
                Logger.i("ContentValues", new Object[]{"printNvBitmap: xl" + xl});
                Logger.i("ContentValues", new Object[]{"printNvBitmap: xh" + xh});
                Logger.i("ContentValues", new Object[]{"printNvBitmap: yl" + yl});
                Logger.i("ContentValues", new Object[]{"printNvBitmap: yj" + yh});
                data[k++] = (byte) xl;
                data[k++] = (byte) xh;
                data[k++] = (byte) yl;
                data[k++] = (byte) yh;
                int byteWidth = cols * 8;

                for (int w = 0; w < byteWidth; ++w) {
                    byte[] dot = new byte[8];

                    for (int h = 0; h < rows; ++h) {
                        int n;
                        for (n = 0; n < 8; ++n) {
                            int i = h * 8 + 7 - n;
                            if (i < height && w < width) {
                                int pixel = bitmap.getPixel(w, i);
                                if (~pixel > 0) {
                                    dot[n] = 1;
                                } else {
                                    dot[n] = 0;
                                }
                            } else {
                                dot[n] = 0;
                            }
                        }

                        n = changePointPx(dot);
                        data[k] = (byte) n;
                        ++k;
                    }
                }
            }

            time = k / 2;
            return this.verifyWriteIO(data, 0, k, time);
        }
    }

    private static int changePointPx(byte[] arry) {
        int v = 0;

        for (int j = 0; j < arry.length; ++j) {
            if (arry[j] == 1) {
                v |= 1 << j;
            }
        }

        return v;
    }

    public int getStatusWithASC() {
        byte[] cmd1 = new byte[]{29, 97, 8};
        this.verifyWriteIO(cmd1, 0, 3, 200);
        byte[] data = new byte[4];
        int size = this.readIO(data, 0, 4, 1);
        Logger.i("size" + size, new Object[0]);
        Logger.i(Arrays.toString(data), new Object[0]);
        int status = this.parseStatusWith(data);
        return status;
    }
    public boolean getStatusOverhead(){
        this.mCmd[0]=0x10;
        this.mCmd[1]=0x04;
        this.mCmd[2]=0x01;
        this.mCmd[3]=0x10;
        this.mCmd[4]=0x04;
        this.mCmd[5]=0x02;
        this.mCmd[6]=0x10;
        this.mCmd[7]=0x04;
        this.mCmd[8]=0x03;
        this.mCmd[9]=0x10;
        this.mCmd[10]=0x04;
        this.mCmd[11]=0x04;
        this.verifyWriteIO(this.mCmd,0,12,500);
        byte[] read=new byte[4];
        if(this.readIO(read,0,4,500)!=-1){
           /* Log.i(TAG, "getStatusOverhead: 数据获取正确");
            Log.i(TAG, "getStatusOverhead: "+read[0]+read[1]+read[2]+read[3]);
            Log.i(TAG, "getStatusOverhead: "+(read[1]==0x12&&read[2]==0x12));*/
            return read[1]==0x12&&read[2]==0x12;
        }else{
        /*    Log.i(TAG, "getStatusOverhead: "+read[0]+read[1]+read[2]+read[3]);
            Log.i(TAG, "getStatusOverhead: "+(read[1]==0x12&&read[2]==0x12));*/
            return true;
        }


    }
    public int getStatusForPrinting(int timeOut) {
        byte[] read = new byte[4];
        this.mCmd[0] = 16;
        this.mCmd[1] = 4;
        this.mCmd[2] = 4;
        if (this.verifyWriteIO(this.mCmd, 0, 3, 500) == 0 && this.readIO(read, 0, 1, 500) > 0) {
            if ((read[0] & 96) > 0) {
                return 2;
            } else {
                this.mCmd[0] = 29;
                this.mCmd[1] = 97;
                this.mCmd[2] = 8;
                this.verifyWriteIO(this.mCmd, 0, 3, 200);
                return this.readIO(read, 0, 4, timeOut) > 0 ? 1 : 0;
            }
        } else {
            return -1;
        }
    }
}
