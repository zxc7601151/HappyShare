package com.happy.share.common.glide.config;

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

import java.io.File;


/**
 * desc: 图片参数配置方法，什么都不做，只申明，子类按需重写 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class LoaderConfig {


    /**
     * 调用into后 参数配置结束，开始调用图片加载框架进行加载
     */
    public void into(@NonNull ImageView targetView) {
    }

    public void into(@NonNull View view) {
    }

    public void intoDrawableTarget(@NonNull Target<Drawable> target) {
    }

    public void intoBitmapTarget(@NonNull Target<Bitmap> target) {
    }

    public LoaderConfig load(@Nullable Object object) {
        return this;
    }

    public LoaderConfig load(@Nullable String url) {
        return this;
    }

    public LoaderConfig load(@Nullable File file) {
        return this;
    }

    public LoaderConfig load(@DrawableRes int drawableResId) {
        return this;
    }

    public LoaderConfig load(@Nullable Uri uri) {
        return this;
    }

    /**
     * 设置占位图
     */
    @NonNull
    public LoaderConfig placeholder(@DrawableRes int placeholderResId) {
        return this;
    }

    @NonNull
    public LoaderConfig placeholder(@NonNull Drawable placeholder) {
        return this;
    }

    /**
     * 设置错误图
     */
    @NonNull
    public LoaderConfig error(@DrawableRes int errorResId) {
        return this;
    }

    @NonNull
    public LoaderConfig error(@NonNull Drawable errorDrawable) {
        return this;
    }

    /**
     * 居中样式
     */
    @NonNull
    public LoaderConfig centerCrop() {
        return this;
    }

    @NonNull
    public LoaderConfig centerInside() {
        return this;
    }

    @NonNull
    public LoaderConfig config(Bitmap.Config config) {
        return this;
    }

    /**
     * 指定加载的宽高
     */
    @NonNull
    public LoaderConfig override(int targetWidth, int targetHeight) {
        return this;
    }

    /**
     * 设置圆角
     *
     * @param cornerAngle 圆角大小
     */
    @NonNull
    public LoaderConfig cornerAngle(float cornerAngle) {
        return this;
    }

    /**
     * 跳过本地缓存
     */
    @NonNull
    public LoaderConfig skipLocalCache(boolean skipLocalCache) {
        return this;
    }

    /**
     * 跳过网络缓存
     */
    @NonNull
    public LoaderConfig skipNetCache(boolean skipNetCache) {
        return this;
    }

    /**
     * 跳过内存缓存
     */
    @NonNull
    public LoaderConfig skipMemoryCache(boolean skipMemoryCache) {
        return this;
    }

    /**
     * 旋转图片
     *
     * @param degrees 旋转角度
     */
    @NonNull
    public LoaderConfig rotate(float degrees) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull Fragment fragment) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull android.app.Fragment fragment) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull FragmentActivity activity) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull Activity activity) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull View view) {
        return this;
    }

    @NonNull
    public LoaderConfig with(@NonNull Context context) {
        return this;
    }

    /**
     * gif
     */
    @NonNull
    public LoaderConfig asGif() {
        return this;
    }

    /**
     * bitmap
     */
    @NonNull
    public LoaderConfig asBitmap() {
        return this;
    }

    /**
     * circle crop an image
     */
    @NonNull
    public LoaderConfig circleCrop() {
        return this;
    }

    /**
     * Glide缓存策略
     */
    @NonNull
    public LoaderConfig diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return this;
    }

    /**
     * glide的加载监听
     */
    @NonNull
    public LoaderConfig listener(@Nullable RequestListener<Drawable> listener) {
        return this;
    }

    @NonNull
    public LoaderConfig listener4Bitmap(@Nullable RequestListener<Bitmap> listener) {
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    public LoaderConfig transforms(@NonNull Transformation<Bitmap>... transformations) {
        return this;
    }

    @NonNull
    public LoaderConfig transform(@NonNull Transformation<Bitmap> transformation) {
        return this;
    }

    @NonNull
    public LoaderConfig thumbnail(@FloatRange(from=0.0, to=1.0) float thumbnail){
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    public LoaderConfig thumbnail4Drawable(@NonNull RequestBuilder<Drawable>... requestBuilders){
        return this;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @NonNull
    public LoaderConfig thumbnail4Bitmap(@NonNull RequestBuilder<Bitmap>... requestBuilders){
        return this;
    }

    /**
     * 不使用动画
     */
    public LoaderConfig dontAnimate() {
        return this;
    }

    /**
     * 传入外部配置项
     */
    public LoaderConfig setRequestOptions(RequestOptions options) {
        return this;
    }

    /**
     * 重置config的配置项，目的是复用该config对象
     */
    public void reset() {
    }
}
