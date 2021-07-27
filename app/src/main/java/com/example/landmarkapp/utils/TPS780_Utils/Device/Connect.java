package com.example.landmarkapp.utils.TPS780_Utils.Device;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.landmarkapp.ui.Activity.QueueUserActivity;
import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.escpos.PrinterAPI;
import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.io.InterfaceAPI;
import com.example.landmarkapp.utils.TPS780_Utils.PrinterAPI.io.USBAPI;

import java.lang.ref.WeakReference;

public class Connect {
    public PrinterAPI mPrinter = PrinterAPI.getInstance();
    private Context context;
    private Runnable runnable;
    private static final int STATUS = 3;
    private final static int NO_CONNECT = 10;

    Handler handler = new NoLeakHandler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PrinterAPI.ERR_PARAM:
                    Log.v("Printer Error", "Parameter error");
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, "Parameter error", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterAPI.NOSUPPROT:
                    Log.v("Printer Error", "Strings are not supported");
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, "Strings are not supported", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterAPI.FAIL:
                    Log.v("Printer Error", "Fail");
                    //progressDialog.dismiss();// 销毁对话框
                    break;
                case PrinterAPI.SUCCESS:
                    Log.v("Printer Res", "Success");
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, R.string.func_success, Toast.LENGTH_SHORT).show();
                    //  mPrinter.printAndFeedLine(5);
                    break;
                case PrinterAPI.STRINGLONG:
                    Log.v("Printer Res", "Length is not supported please re-enter");
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, "Length is not supported please re-enter", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Log.v("Printer Res", "func_failed");
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, R.string.func_failed, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Log.v("Printer Res", "socket connect fail");
                    //Toast.makeText(context, "socket connect fail", Toast.LENGTH_SHORT).show();
                    break;
                case STATUS:
                    Log.v("Printer Res", "The current state is:\" " + (String) msg.obj);
                    //progressDialog.dismiss();// 销毁对话框
                    //Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    //etReceiver.setText("");
                    //etReceiver.setText("The current state is:" + (String) msg.obj);
                    break;
                case 0x100:
                    Log.v("Printer Res", "");
                    //etReceiver.setText("");
                    break;
                case NO_CONNECT:
                    Log.v("Printer Res", "Current device isn't connected");
                    //etReceiver.setText("");
                    //etReceiver.setText("Current device isn't connected");
                    //Toast.makeText(context, "Current device isn't connected", Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private class NoLeakHandler extends Handler {
        WeakReference<QueueUserActivity> wf = null;

    }

    public void Connect_Devices(QueueUserActivity activity, final Context context) {
        runnable = new Runnable() {
            @Override
            public void run() {
                InterfaceAPI io = null;
                io = new USBAPI(context);
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        };
        new Thread(runnable).start();
    }

    public boolean isConnect() {
        boolean isConnect = true;
        if (!mPrinter.isConnect()) {
            handler.sendEmptyMessage(NO_CONNECT);
            isConnect = false;
        }
        return isConnect;
    }

    public void disconnectDevice() {
        if (!isConnect()) return;
        if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }
    }
}
