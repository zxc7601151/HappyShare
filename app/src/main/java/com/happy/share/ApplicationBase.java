package com.happy.share;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.happy.share.common.base.MyActivityManager;
import com.happy.share.tools.ToolApp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * desc: Application <br/>
 * time: 2018/11/24 10:05 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ApplicationBase extends MultiDexApplication {

    /**
     * 单例
     */
    private static ApplicationBase mInstance;
    /**
     * 全局context 当{@link #mInstance}为空时使用
     */
    private static Context mGlobalContext;

    /**
     * 获取ApplicationBase单例对象
     */
    public static ApplicationBase getInstance() {
        return mInstance;
    }

    public static void setGlobalContext(@NonNull Context context) {
        mGlobalContext = context;
    }

    public static Context getContext() {
        return mInstance == null ? mGlobalContext : mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 非App进程启动拦截
        if (ToolApp.isMyAppProcess(this)) {
            mInstance = this;
        }
    }


    /**
     * 重启App
     *
     * @param loadingActivity 引导页面
     * @param delayTime       延迟启动时间 (毫秒数)
     */
    @SuppressLint("CheckResult")
    public void restartApp(final Class<?> loadingActivity, long delayTime) {
        Observable.timer(delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> {
                    // 设置重启闹钟
                    PendingIntent restartIntent = PendingIntent.getActivity(ApplicationBase.this, 0
                            , new Intent(ApplicationBase.this, loadingActivity), PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) ApplicationBase.this.getSystemService(Context.ALARM_SERVICE);
                    if (mgr != null) {
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 600, restartIntent);
                    }

                    // 延迟200毫秒彻底杀死app
                    exit(200);
                });
    }

    /**
     * 退出程序
     *
     * @param delayTime 延时退出时间
     */
    public void exit(long delayTime) {
        try {
            MyActivityManager.getInstance().finishAllActivity();
            ToolApp.killApp(delayTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
