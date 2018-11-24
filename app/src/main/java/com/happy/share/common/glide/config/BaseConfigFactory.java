package com.happy.share.common.glide.config;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;


/**
 * desc: 图片的配置工厂基类 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public abstract class BaseConfigFactory<T extends LoaderConfig> {


    @NonNull
    private Pools.SynchronizedPool<T> mSyncPool;


    public BaseConfigFactory(int size) {
        if (mSyncPool == null) {
            mSyncPool = new Pools.SynchronizedPool<>(size);
        }
    }

    public Pools.SynchronizedPool<T> getSyncPool() {
        return mSyncPool;
    }

    /**
     * 获取config的实例
     */
    @NonNull
    public T obtain() {
        T t = mSyncPool.acquire();
        return t != null ? t : createNewInstance();
    }

    /**
     * 构建config的实例
     */
    @NonNull
    public abstract T createNewInstance();

    /**
     * 释放config的实例
     */
    public void release(@NonNull T t) {
        t.reset();
        mSyncPool.release(t);
    }
}
