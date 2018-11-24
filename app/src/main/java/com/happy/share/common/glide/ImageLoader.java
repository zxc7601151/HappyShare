package com.happy.share.common.glide;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.happy.share.common.glide.config.LoaderConfig;
import com.happy.share.common.glide.glideimpl.GlideImageLoader;
import com.happy.share.common.glide.strategy.ICacheStrategy;
import com.happy.share.common.glide.strategy.IRequestStrategy;

import java.io.File;

/**
 * desc: 图片加载框架的容器代理类，可切换不同图片框架,如{@link GlideImageLoader} <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class ImageLoader implements IImageLoader<LoaderConfig>, ICacheStrategy, IRequestStrategy {


    private IImageLoader<? extends LoaderConfig> mLoader;


    private ImageLoader() {

    }

    /**
     * 静态内部类实现单例
     */
    public static ImageLoader getInstance() {
        return SingleInstance.sImageLoader;
    }

    /**
     * 手动设置图片加载框架
     *
     * @param imageLoader 外部传入的图片加载框架
     */
    public void setImageLoader(@NonNull IImageLoader<? extends LoaderConfig> imageLoader) {
        //不能传自己，防止调用load时死循环;
        if (imageLoader == ImageLoader.getInstance()) {
            return;
        }
        this.mLoader = imageLoader;
    }

    @NonNull
    public IImageLoader<? extends LoaderConfig> getLoader() {
        if (mLoader == null) {
            //默认使用glide
            mLoader = GlideImageLoader.getInstance();
        }
        return mLoader;
    }

    /**
     * 图片加载入口函数
     */
    @NonNull
    @Override
    public LoaderConfig load(@NonNull String url) {
        return getLoader().load(url);
    }

    @NonNull
    @Override
    public LoaderConfig load(@NonNull Uri uri) {
        return getLoader().load(uri);
    }

    @NonNull
    @Override
    public LoaderConfig load(@NonNull File file) {
        return getLoader().load(file);
    }

    @NonNull
    @Override
    public LoaderConfig load(@DrawableRes int res) {
        return getLoader().load(res);
    }


    /**
     * 清理内存缓存 需在主线程调用，在子线程调用无效（内部已做线程判断，不抛异常）
     */
    @Override
    public void clearMemoryCache(@NonNull Context context) {
        getLoader().clearMemoryCache(context);
    }

    /**
     * 清除磁盘缓存 需在子线程调用，在主线程调用无效（内部已做线程判断，不抛异常）
     */
    @Override
    public void clearDiskCache(@NonNull Context context) {
        getLoader().clearDiskCache(context);
    }

    /**
     * 设置内存缓存
     */
    @Override
    public void setMemoryCache() {
        getLoader().setMemoryCache();
    }

    /**
     * 设置磁盘缓存
     */
    @Override
    public void setDiskCache() {
        getLoader().setDiskCache();
    }

    /**
     * 低内存下的处理
     */
    @Override
    public void onLowMemory(@NonNull Context context) {
        getLoader().onLowMemory(context);
    }

    /**
     * 不同内存级别下的操作
     *
     * @param level {@link android.content.ComponentCallbacks2}
     */
    @Override
    public void onTrimMemory(@NonNull Context context, int level) {
        getLoader().onTrimMemory(context, level);
    }

    /**
     * 暂停图片加载
     */
    @Override
    public void pauseRequests(@NonNull Context context) {
        getLoader().pauseRequests(context);
    }

    /**
     * 恢复图片加载
     */
    @Override
    public void resumeRequests(@NonNull Context context) {
        getLoader().resumeRequests(context);
    }

    private static class SingleInstance {
        private static ImageLoader sImageLoader = new ImageLoader();
    }
}
