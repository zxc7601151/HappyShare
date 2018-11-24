package com.happy.share.common.glide.glideimpl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.happy.share.common.glide.config.CommonLoaderConfig;

import java.io.File;

/**
 * desc: glide加载图片时的配置 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class GlideLoaderConfig extends CommonLoaderConfig {


    public Fragment fragmentV4;
    public android.app.Fragment fragmentApp;
    public FragmentActivity fragmentActivity;
    public Activity activity;
    public Context context;
    public View view;
    public int overrideSize;//指定图片尺寸
    public boolean isGif;//gif
    public boolean isBitmap;//Bitmap
    public boolean isCircleCrop;//circle crop an image
    public RequestListener<Drawable> requestListener4Drawable;//加载监听
    public RequestListener<Bitmap> requestListener4Bitmap;//加载监听
    public DiskCacheStrategy diskCacheStrategy;//磁盘缓存策略
    public Transformation<Bitmap>[] transformations;//多种形状变幻
    public Transformation<Bitmap> transformation;//变幻形状
    public View targetView;//最后加载到view中
    public Target<Drawable> targetDrawable;//最后加载到target中
    public Target<Bitmap> targetBitmap;//最后加载到target中
    public float thumbnail;//缩略图
    public RequestBuilder<Bitmap>[] thumbnails4Bitmap;
    public RequestBuilder<Drawable>[] thumbnails4Drawable;
    public boolean isDontAnimate;//不使用动画
    public RequestOptions requestOptions;//外部传入的配置项


    /**
     * into方法作为config配置结束方法，内部需调用相应imageloader的loadOptions方法
     */
    @Override
    public void into(@NonNull ImageView targetView) {
        this.targetImageView = targetView;
        GlideImageLoader.getInstance().loadOptions(this);
    }

    /**
     * into方法作为config配置结束方法，内部需调用相应imageloader的loadOptions方法
     */
    @Override
    public void into(@NonNull View view) {
        this.targetView = view;
        GlideImageLoader.getInstance().loadOptions(this);
    }

    @Override
    public void intoDrawableTarget(@NonNull Target<Drawable> target) {
        this.targetDrawable = target;
        GlideImageLoader.getInstance().loadOptions(this);
    }

    @Override
    public void intoBitmapTarget(@NonNull Target<Bitmap> target) {
        this.targetBitmap = target;
        GlideImageLoader.getInstance().loadOptions(this);
    }

    /**
     * load的参数可以很多，这里只提供部分，可在子类中按需添加
     * 不建议直接使用object，轻微耗时
     */
    @NonNull
    @Override
    public GlideLoaderConfig load(@Nullable Object object) {
        if (object instanceof String) {
            return load((String) object);
        } else if (object instanceof Uri) {
            return load((Uri) object);
        } else if (object instanceof File) {
            return load((File) object);
        } else if (object instanceof Integer) {
            return load((int) object);
        }
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@Nullable String url) {
        super.load(url);
        this.url = url;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@Nullable File file) {
        super.load(file);
        this.file = file;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@DrawableRes int drawableResId) {
        super.load(drawableResId);
        this.drawableResId = drawableResId;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@Nullable Uri uri) {
        super.load(uri);
        this.uri = uri;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull Fragment fragment) {
        this.fragmentV4 = fragment;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull android.app.Fragment fragment) {
        this.fragmentApp = fragment;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull FragmentActivity activity) {
        this.fragmentActivity = activity;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull Activity activity) {
        this.activity = activity;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull View view) {
        this.view = view;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig with(@NonNull Context context) {
        this.context = context;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig asGif() {
        isGif = true;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig asBitmap() {
        isBitmap = true;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig circleCrop() {
        isCircleCrop = true;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig listener(@Nullable RequestListener<Drawable> listener) {
        requestListener4Drawable = listener;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig listener4Bitmap(@Nullable RequestListener<Bitmap> listener) {
        requestListener4Bitmap = listener;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    @Override
    public GlideLoaderConfig transforms(@NonNull Transformation<Bitmap>... transformations) {
        this.transformations = transformations;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig transform(@NonNull Transformation<Bitmap> transformation) {
        this.transformation = transformation;
        return this;
    }

    @NonNull
    @Override
    public GlideLoaderConfig thumbnail(@FloatRange(from=0.0, to=1.0) float thumbnail){
        this.thumbnail = thumbnail;
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    @Override
    public GlideLoaderConfig thumbnail4Drawable(@NonNull RequestBuilder<Drawable>... requestBuilders){
        this.thumbnails4Drawable = requestBuilders;
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    @Override
    public GlideLoaderConfig thumbnail4Bitmap(@NonNull RequestBuilder<Bitmap>... requestBuilders){
        this.thumbnails4Bitmap = requestBuilders;
        return this;
    }

    @Override
    public GlideLoaderConfig dontAnimate() {
        this.isDontAnimate = true;
        return this;
    }

    @Override
    public GlideLoaderConfig setRequestOptions(RequestOptions options) {
        this.requestOptions = options;
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        fragmentV4 = null;
        fragmentApp = null;
        fragmentActivity = null;
        activity = null;
        view = null;
        overrideSize = 0;
        isGif = false;
        isBitmap = false;
        context = null;
        diskCacheStrategy = null;
        transformations = null;
        transformation = null;
        targetView = null;
        targetDrawable = null;
        targetBitmap = null;
        requestListener4Drawable = null;
        requestListener4Bitmap = null;
        isCircleCrop = false;
        thumbnail = 0;
        thumbnails4Drawable = null;
        thumbnails4Bitmap = null;
        isDontAnimate = false;
        requestOptions = null;
    }

}
