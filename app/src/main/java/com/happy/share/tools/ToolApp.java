package com.happy.share.tools;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;

import com.happy.share.ApplicationBase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * desc: app相关工具类 <br/>
 * time: 2017/9/12 下午2:17 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ToolApp {

    /**
     * 是否 Android 4.4(包含)及以上版本。
     *
     * @return true:是
     */
    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 是否 Android 5.0或更高版本
     *
     * @return true：是
     */
    public static boolean isLollipopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否 M(6.0) 或更高版本
     *
     * @return true：是
     */
    public static boolean isMOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获取当前app版本信息
     *
     * @param context 上下文
     * @return 当前版本名称
     */
    public static String getVersionName(Context context) {
        String verName = "";
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo != null) {
            verName = packageInfo.versionName;
        }

        return verName;
    }

    /**
     * 获取当前app code 信息
     *
     * @param context 上下文
     * @return 当前版本号
     */
    public static int getCurrentVerCode(Context context) {
        int verCode = 0;
        PackageInfo packageInfo = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo != null) {
            verCode = packageInfo.versionCode;
        }

        return verCode;
    }

    /**
     * 获取配置的meta-data信息
     *
     * @param context 上下文
     * @param metaKey key
     * @return Meta value
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String value = null;

        if (context == null || metaKey == null) {
            return null;
        }

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName()
                    , PackageManager.GET_META_DATA);

            if (null != ai) {
                metaData = ai.metaData;
            }

            if (null != metaData) {
                value = metaData.getString(metaKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 获取App名称
     */
    public static String getApplicationLabel() {
        Context applicationCtx = ApplicationBase.getInstance().getApplicationContext();

        if ((applicationCtx != null) && (applicationCtx.getPackageManager() != null)) {
            try {
                CharSequence label = applicationCtx.getPackageManager()
                        .getApplicationLabel(applicationCtx.getApplicationInfo());
                return label.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "errors";
    }

    /**
     * 根据Pid获取进程名称
     *
     * @param pid 进程id
     */
    private static String getProcessName(Context cxt, int pid) {
        if (cxt == null) {
            return null;
        }

        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }

        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }

        for (int i = 0; i < runningApps.size(); i++) {
            ActivityManager.RunningAppProcessInfo procInfo = runningApps.get(i);
            if (procInfo != null && procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 是否App进程启动
     *
     * @param ctx 上下文
     * @return true:是
     */
    public static boolean isMyAppProcess(Context ctx) {
        if (ctx == null) {
            return false;
        }

        String appPackageName = ctx.getPackageName();

        if (ToolText.isEmptyOrNull(appPackageName)) {
            return true;
        } else {
            return appPackageName.equals(getProcessName(ctx, Process.myPid()));
        }
    }

    /**
     * 生成唯一code
     *
     * @return 唯一code
     */
    public static String getUniqueCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * 杀死App
     *
     * @param delayTime 延时时间，0表示立即杀死，无延时。
     */
    public static void killApp(long delayTime) {
        if (delayTime <= 0) {
            killAppNow();
        } else {
            beginKillAppDelayJob(delayTime);
        }
    }

    /**
     * 开启延时杀死app任务
     *
     * @param delayTime 延时杀死app等待时间，毫秒
     */
    @SuppressLint("CheckResult")
    private static void beginKillAppDelayJob(long delayTime) {
        Observable.timer(delayTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(number -> {
                    killAppNow();
                });
    }

    /**
     * 立刻杀死app
     */
    private static void killAppNow() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 判断软件是否安装
     */
    public static boolean isAPPInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pinfo = pm.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
