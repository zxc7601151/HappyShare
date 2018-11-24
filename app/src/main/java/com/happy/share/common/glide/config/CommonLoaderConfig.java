package com.happy.share.common.glide.config;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;

/**
 * desc: 公共的图片配置，不和任何框架 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class CommonLoaderConfig extends LoaderConfig {
    /**
     * 未指定的图片资源类型
     */
    private static final byte RESOURCE_TYPE_NONE = 0;
    /**
     * url型的图片资源类型
     */
    public static final byte RESOURCE_TYPE_URL = 1;
    /**
     * uri型的图片资源类型
     */
    public static final byte RESOURCE_TYPE_URI = 2;
    /**
     * file型的图片资源类型
     */
    public static final byte RESOURCE_TYPE_FILE = 3;
    /**
     * 资源id型的图片资源类型
     */
    public static final byte RESOURCE_TYPE_RES_ID = 4;

    @DrawableRes
    public int placeholderResId;
    public Drawable placeholder;

    @DrawableRes
    public int errorResId;
    public Drawable error;

    public boolean isCenterCrop;
    public boolean isFitCenter;
    public boolean isCenterInside;

    public boolean skipLocalCache; //是否缓存到本地
    public boolean skipNetCache;
    public boolean skipMemoryCache;

    public Bitmap.Config config = Bitmap.Config.RGB_565;

    public int targetWidth;
    public int targetHeight;

    public float cornerAngle; //圆角角度
    public float rotateDegrees; //旋转角度

    public String url;
    public File file;
    public Object object;

    @DrawableRes
    public int drawableResId;
    public Uri uri;

    //targetView展示图片
    public ImageView targetImageView;

    //加载的图片资源类型
    public byte resourceType;


    /**
     * load的参数可以很多，这里只提供部分，可在子类中按需添加
     * 不建议使用，稍微耗时
     */
    @NonNull
    @Override
    public CommonLoaderConfig load(@Nullable Object object) {
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
    @CallSuper
    @Override
    public CommonLoaderConfig load(@Nullable String url) {
        this.url = url;
        this.resourceType = RESOURCE_TYPE_URL;
        return this;
    }

    @NonNull
    @CallSuper
    @Override
    public CommonLoaderConfig load(@Nullable File file) {
        this.file = file;
        this.resourceType = RESOURCE_TYPE_FILE;
        return this;
    }

    @NonNull
    @CallSuper
    @Override
    public CommonLoaderConfig load(@DrawableRes int drawableResId) {
        this.drawableResId = drawableResId;
        this.resourceType = RESOURCE_TYPE_RES_ID;
        return this;
    }

    @NonNull
    @CallSuper
    @Override
    public CommonLoaderConfig load(@Nullable Uri uri) {
        this.uri = uri;
        this.resourceType = RESOURCE_TYPE_URI;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig placeholder(@NonNull Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig error(@NonNull Drawable errorDrawable) {
        this.error = errorDrawable;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig centerCrop() {
        isCenterCrop = true;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig centerInside() {
        isCenterInside = true;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig override(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     */
    @NonNull
    @Override
    public CommonLoaderConfig cornerAngle(float cornerAngle) {
        this.cornerAngle = cornerAngle;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig skipLocalCache(boolean skipLocalCache) {
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    @NonNull
    @Override
    public CommonLoaderConfig skipNetCache(boolean skipNetCache) {
        this.skipNetCache = skipNetCache;
        return this;
    }


    @NonNull
    @Override
    public CommonLoaderConfig skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }


    @NonNull
    @Override
    public CommonLoaderConfig rotate(float degrees) {
        this.rotateDegrees = degrees;
        return this;
    }

    @Override
    public void reset() {
        placeholderResId = 0;
        placeholder = null;
        errorResId = 0;
        error = null;
        isCenterCrop = false;
        isFitCenter = false;
        isCenterInside = false;
        skipLocalCache = false;
        skipNetCache = false;
        skipMemoryCache = false;
        config = Bitmap.Config.RGB_565;
        targetWidth = 0;
        targetHeight = 0;
        cornerAngle = 0;
        rotateDegrees = 0;
        url = null;
        file = null;
        object = null;
        drawableResId = 0;
        uri = null;
        targetImageView = null;
        resourceType = RESOURCE_TYPE_NONE;
    }

}
