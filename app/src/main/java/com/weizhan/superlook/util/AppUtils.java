package com.weizhan.superlook.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.weizhan.superlook.App;

import java.io.File;
import java.util.Date;

public class AppUtils {
    public static boolean isExternalStorageAllowed = true;
    public static boolean ExistSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return true;
        } else {
            return false;
        }
    }

    public static File getAvailableCacheDir() {
            if (isExternalStorageAllowed && ExistSDCard())
                return App.getInstance().getExternalCacheDir();
            else
                return App.getInstance().getCacheDir();
    }

    public static File getInternalCacheDir() {
        return App.getInstance().getCacheDir();
    }

    public static File getExternalCacheDir() {
        return App.getInstance().getExternalCacheDir();
    }

    public static String getAvailableCacheDirPath() {
        String result = null;
        if (getAvailableCacheDir() == null) {
            result = getInternalCacheDir().getAbsolutePath();
        } else {
            result = getAvailableCacheDir().getAbsolutePath();
        }
        if (result==null || result.trim().equals("")) {
            result = App.getInstance().getCacheDir() + File.separator + "bitmap-cache";
        }
        return result;
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static int format(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if(timeStamp >= todayStartMillis) {
            return 1; //今天
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if(timeStamp >= yesterdayStartMilis) {
            return 0; //昨天
        }
        return -1; //更早
    }

}
