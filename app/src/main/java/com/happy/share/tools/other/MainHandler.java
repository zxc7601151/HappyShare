package com.happy.share.tools.other;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * desc: 主线程Handler <br/>
 * time: 2018/7/19 下午1:56 <br/>
 * author: Logan <br/>
 * since V 1.0 <br/>
 */
public class MainHandler {

    /**
     * 主线程Handler
     */
    @NonNull
    private Handler mHandler;


    private MainHandler() {
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取 {@link MainHandler}单例对象
     */
    @NonNull
    public static MainHandler getInstance() {
        return MainHandlerHolder.instance;
    }

    /**
     * 在主线程执行runnable
     *
     * @param runnable 主线程待执行的任务
     */
    public void post(@NonNull Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // 当前线程已经在主线程，立即执行
            runnable.run();
        } else {
            // 当前线程已经在后台线程，切换到主线程后执行
            mHandler.post(runnable);
        }
    }

    /**
     * 延迟delayMillis毫秒后，在主线程执行runnable
     *
     * @param runnable    主线程待执行的任务
     * @param delayMillis 延时X毫秒后执行任务
     */
    public void postDelayed(@NonNull Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 删除待执行的runnable
     *
     * @param runnable 待执行任务
     */
    public void removeCallbacks(@NonNull Runnable runnable) {
        mHandler.removeCallbacks(runnable);
    }

    /**
     * MainHandler单例
     */
    private static class MainHandlerHolder {
        private static MainHandler instance = new MainHandler();
    }

}