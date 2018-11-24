package com.happy.share.common.glide.strategy;


import android.content.Context;
import android.support.annotation.NonNull;

/**
 * desc: 图片加载框架缓存处理 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public interface ICacheStrategy {


    /**
     * 清理内存缓存 需在主线程调用
     */
    void clearMemoryCache(@NonNull Context context);

    /**
     * 清理硬盘缓存，请勿在UI线程调用
     */
    void clearDiskCache(@NonNull Context context);

    /**
     * 设置内存缓存
     */
    void setMemoryCache();

    /**
     * 设置硬盘缓存
     */
    void setDiskCache();

    /**
     * 低内存下的操作
     */
    void onLowMemory(@NonNull Context context);

    /**
     * 低内存下的操作
     */
    void onTrimMemory(@NonNull Context context, int level);

}
