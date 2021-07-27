package com.example.landmarkapp.ui;

import android.app.Application;

import com.example.landmarkapp.utils.TPS780_Utils.UIUtils;
import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.log.AndroidLogAdapter;
import com.szsicod.print.log.Logger;
import com.szsicod.print.log.Utils;


/**
 * Created by crf on 2018/4/24.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //CrashHandler.getInstance().init(this);
        UIUtils.init(this);
        Utils.init(this);
        PrinterAPI.getInstance().setOutput(true);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}
