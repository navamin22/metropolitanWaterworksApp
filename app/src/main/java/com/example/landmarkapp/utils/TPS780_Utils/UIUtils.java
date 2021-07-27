package com.example.landmarkapp.utils.TPS780_Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.landmarkapp.R;
import com.example.landmarkapp.ui.App;

import java.lang.reflect.Field;


public class UIUtils {

    private static final String TAG = "UIUtils";
    private static Toast mToast;
    private static int screenWidth;
    private static int screenHeight;
    private static int screenMin;
    private static int screenMax;
    private static float density;
    private static float scaleDensity;
    private static App context;

    public static float getDensity() {
        return density;
    }

    public static void setDensity(float density) {
        UIUtils.density = density;
    }

    public static float getScaleDensity() {
        return scaleDensity;
    }

    public static void setScaleDensity(float scaleDensity) {
        UIUtils.scaleDensity = scaleDensity;
    }

    public static float getXdpi() {
        return xdpi;
    }

    public static void setXdpi(float xdpi) {
        UIUtils.xdpi = xdpi;
    }

    public static float getYdpi() {
        return ydpi;
    }

    public static void setYdpi(float ydpi) {
        UIUtils.ydpi = ydpi;
    }

    public static int getDensityDpi() {
        return densityDpi;
    }

    public static void setDensityDpi(int densityDpi) {
        UIUtils.densityDpi = densityDpi;
    }

    private static float xdpi;
    private static float ydpi;
    private static int densityDpi;
    private static int statusBarHeight;
    private static int navBarHeight;

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int duration) {
        if (mToast == null) {
            try {
                mToast = Toast.makeText(getContext(), "", duration);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void customGravityToast(String msg, int duration) {
        if (mToast == null) {
            try {
                mToast = Toast.makeText(getContext(), "", duration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setText(msg);
        mToast.show();

    }




    public static Context getContext() throws Exception {
        if (null==context){throw new Exception("没有初始化");}
       return context;
    }
    public static void init(App context){
        UIUtils.context=context;
    }

    public static Resources getResource() {
        try {
            return getContext().getResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    public static int[] getIntegerArr(int resId) {
        TypedArray typedArray = getResource().obtainTypedArray(resId);

        int indexCount = typedArray.length();
        Log.e(TAG, "initData: indexCount" + indexCount);
        int arr[] = new int[indexCount];
        for (int i = 0; i < indexCount; i++) {
            arr[i] = typedArray.getResourceId(i, R.drawable.ic_launcher_background);
        }
        return arr;
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }


    public static String getPackageName() {
        try {
            return getContext().getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVersion() {
        String version = "0";
        PackageManager packageManager = null;
        try {
            packageManager = getContext().getPackageManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return version = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return version;
        }

    }
    public static View getView(int viewId) {
        View view = null;
        try {
            view = View.inflate(getContext(), viewId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public static View getView(int viewId, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);
        return view;
    }

    /**
     * dip-->px
     */
    public static int dip2Px(int dip) {

        float density = getResource().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }

    /**
     * px-->dip
     */
    public static int px2dip(int px) {

        float density = getResource().getDisplayMetrics().density;
        int dip = (int) (px / density + 0.5f);
        return dip;
    }

    /**
     * sp-->px
     */
    public static int sp2px(int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResource().getDisplayMetrics()) + 0.5f);
    }


    public static int getDisplayWidth() {
        if (screenWidth == 0) {
            try {
                GetInfo(UIUtils.getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return screenWidth;
    }

    public static int getDisplayHeight() {
        if (screenHeight == 0) {
            try {
                GetInfo(UIUtils.getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return screenHeight;
    }

    public static void GetInfo(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
        statusBarHeight = getStatusBarHeight(context);
        navBarHeight = getNavBarHeight(context);
        Log.e(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getNavBarHeight(Context context) {
        return getBarHeight(context, "navigation_bar_height");
    }

    private static int getBarHeight(Context context, String navigation_bar_height) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(navigation_bar_height, "dimen",
                "android");
        if (resourceId > 0) {
            try {
                return resources.getDimensionPixelSize(resourceId);
            } catch (Resources.NotFoundException e) {

            }
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        return getBarHeight(context, "status_bar_height");
    }

    public static void showToast(int username_empty) {
        String message = getString(username_empty);
        if (message == null) {
            return;
        }
        showToast(message);

    }

    public static int getInt(int alpha_10) {
        Integer alpha = 0;
        try {

            alpha = getResource().getInteger(alpha_10);
        } catch (Resources.NotFoundException e) {
            return alpha;
        }
        return alpha;
    }


    public static int getDrawableIdByname(String img, String packName) {
        try {
            Class<?> aClass = Class.forName(packName);
            Field declaredField = aClass.getDeclaredField(img);
            int anInt = declaredField.getInt(aClass);

            return anInt;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return 0;
    }
}