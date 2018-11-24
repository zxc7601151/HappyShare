package com.happy.share.common.glide.glideimpl;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.happy.share.common.glide.ILoad;
import com.happy.share.common.glide.glideimpl.transform.RoundedCornersTransformation;

import static com.happy.share.common.glide.config.CommonLoaderConfig.RESOURCE_TYPE_FILE;
import static com.happy.share.common.glide.config.CommonLoaderConfig.RESOURCE_TYPE_RES_ID;
import static com.happy.share.common.glide.config.CommonLoaderConfig.RESOURCE_TYPE_URI;
import static com.happy.share.common.glide.config.CommonLoaderConfig.RESOURCE_TYPE_URL;


/**
 * desc: glide加载实现类 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class GlideLoaderImpl implements ILoad<GlideLoaderConfig> {


    @Override
    public void loadImage(@NonNull GlideLoaderConfig config) {
        RequestManager glideRequests = getGlideRequest(config);
        if (glideRequests != null) {
            RequestOptions requestOptions = getGlideRequestOptions(config);

            // bitmap型
            if (config.isBitmap) {
                if (config.resourceType == RESOURCE_TYPE_URL) {
                    intoBitmapTarget(glideRequests.asBitmap().load(config.url).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_URI) {
                    intoBitmapTarget(glideRequests.asBitmap().load(config.uri).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_FILE) {
                    intoBitmapTarget(glideRequests.asBitmap().load(config.file).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_RES_ID) {
                    intoBitmapTarget(glideRequests.asBitmap().load(config.drawableResId).apply(requestOptions), config);
                }
            } else {
                // drawable型
                if (config.resourceType == RESOURCE_TYPE_URL) {
                    intoDrawableTarget(glideRequests.load(config.url).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_URI) {
                    intoDrawableTarget(glideRequests.load(config.uri).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_FILE) {
                    intoDrawableTarget(glideRequests.load(config.file).apply(requestOptions), config);
                } else if (config.resourceType == RESOURCE_TYPE_RES_ID) {
                    intoDrawableTarget(glideRequests.load(config.drawableResId).apply(requestOptions), config);
                }
            }
        }
    }

    @Override
    public void clearMemoryCache(@NonNull Context context) {
        //需在主线程调用
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory();
        }
    }

    @Override
    public void clearDiskCache(@NonNull Context context) {
        //不能在主线程调用
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Glide.get(context).clearDiskCache();
        }
    }

    /**
     * {@link com.happy.share.common.glide.config.GlideCacheConfig}
     */
    @Override
    public void setMemoryCache() {
        // do nothing
    }

    /**
     * {@link com.happy.share.common.glide.config.GlideCacheConfig}
     */
    @Override
    public void setDiskCache() {
        // do nothing
    }

    @Override
    public void onLowMemory(@NonNull Context context) {
        Glide.get(context).onLowMemory();
    }

    @Override
    public void onTrimMemory(@NonNull Context context, int level) {
        Glide.get(context).onTrimMemory(level);
    }


    @NonNull
    private RequestOptions getGlideRequestOptions(GlideLoaderConfig config) {
        // 优先使用外部传入的RequestOptions
        if (config.requestOptions != null) {
            return config.requestOptions;
        }
        return setTransformation(config, setOverrideSize(config, setCenterStyle(config
                , setCacheStrategy(config, setPlaceholderAndError(config, new RequestOptions())))));
    }

    @Nullable
    private RequestManager getGlideRequest(@NonNull GlideLoaderConfig config) {
        RequestManager glideRequests = null;
        if (config.view != null) {
            glideRequests = Glide.with(config.view);
        } else if (config.fragmentActivity != null && !config.fragmentActivity.isDestroyed()) {
            glideRequests = Glide.with(config.fragmentActivity);
        } else if (config.activity != null && !config.activity.isDestroyed()) {
            glideRequests = Glide.with(config.activity);
        } else if (config.fragmentV4 != null && config.fragmentV4.getActivity() != null) {
            glideRequests = Glide.with(config.fragmentV4);
        } else if (config.fragmentApp != null && config.fragmentApp.getActivity() != null) {
            glideRequests = Glide.with(config.fragmentApp);
        } else if (config.context != null) {
            glideRequests = Glide.with(config.context);
        }
        return glideRequests;
    }

    @Override
    public void pauseRequests(@NonNull Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resumeRequests(@NonNull Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 处理图片样式
     */
    @NonNull
    private RequestOptions setTransformation(@NonNull GlideLoaderConfig config, @NonNull RequestOptions options) {
        if (config.isCircleCrop) {
            options = options.circleCrop();
        }
        //处理Transformation--圆角
        if (config.cornerAngle > 0 && config.isCenterCrop) {
            //glide4.0+ centerCrop与圆角同时设置需特殊处理
            options = options.transforms(new CenterCrop(), new RoundedCornersTransformation(
                    (int) config.cornerAngle, 0));
        } else if (config.cornerAngle > 0 && config.isCenterInside) {
            options = options.transforms(new CenterInside(), new RoundedCornersTransformation(
                    (int) config.cornerAngle, 0));
        } else if (config.cornerAngle > 0 && config.isFitCenter) {
            options = options.transforms(new FitCenter(), new RoundedCornersTransformation(
                    (int) config.cornerAngle, 0));
        } else if (config.cornerAngle > 0) {
            options = options.transform(new RoundedCornersTransformation((int) config.cornerAngle, 0));
        }

        if (config.transformations != null) {
            options = options.transforms(config.transformations);
        }

        if (config.transformation != null) {
            options = options.transform(config.transformation);
        }

        if (config.isDontAnimate) {
            options = options.dontAnimate();
        }
        return options;
    }

    /**
     * 设置大小
     */
    @NonNull
    private RequestOptions setOverrideSize(@NonNull GlideLoaderConfig config, @NonNull RequestOptions options) {
        //指定图片尺寸
        if (config.targetHeight > 0 && config.targetWidth > 0) {
            options = options.override(config.targetWidth, config.targetHeight);
        } else if (config.overrideSize > 0) {
            options = options.override(config.overrideSize);
        }
        return options;
    }

    /**
     * 处理居中样式
     */
    @NonNull
    private RequestOptions setCenterStyle(@NonNull GlideLoaderConfig config, @NonNull RequestOptions options) {
        //居中缩放样式
        //glide4.0+ centerCrop与圆角同时设置需特殊处理
        if (config.isCenterCrop && config.cornerAngle == 0) {
            options = options.centerCrop();
        } else if (config.isCenterInside && config.cornerAngle == 0) {
            options = options.centerInside();
        } else if (config.isFitCenter && config.cornerAngle == 0) {
            options = options.fitCenter();
        }
        return options;
    }

    /**
     * 处理内存/磁盘缓存策略
     */
    @NonNull
    private RequestOptions setCacheStrategy(@NonNull GlideLoaderConfig config, @NonNull RequestOptions options) {
        //是否跳过内存缓存
        if (config.skipMemoryCache) {
            options = options.skipMemoryCache(true);
        }

        //跳过本地缓存
        if (config.skipLocalCache) {
            options = options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        //设置硬盘缓存策略
        if (config.diskCacheStrategy != null) {
            options = options.diskCacheStrategy(config.diskCacheStrategy);
        }
        return options;
    }

    /**
     * 处理占位图和error图片
     */
    @NonNull
    private RequestOptions setPlaceholderAndError(@NonNull GlideLoaderConfig config, @NonNull RequestOptions options) {
        //设置占位图
        if (config.placeholderResId != 0) {
            options = options.placeholder(config.placeholderResId);
        } else if (config.placeholder != null) {
            options = options.placeholder(config.placeholder);
        }
        //设置加载error图片
        if (config.errorResId != 0) {
            options = options.error(config.errorResId);
        } else if (config.error != null) {
            options = options.error(config.error);
        }
        return options;
    }

    /**
     * config配置完成，调用glide into（）方法进行加载图片
     *
     * @param request glide的RequestBuilder
     * @param config  配置的GlideLoaderConfig
     */
    private void intoDrawableTarget(@NonNull RequestBuilder<Drawable> request, @NonNull GlideLoaderConfig config) {
        // 设置listener
        if (config.requestListener4Drawable != null) {
            request = request.listener(config.requestListener4Drawable);
        }
        // 设置缩略图
        if (config.thumbnail > 0) {
            request = request.thumbnail(config.thumbnail);
        } else if (config.thumbnails4Drawable != null) {
            request = request.thumbnail(config.thumbnails4Drawable);
        }

        // glide加载
        if (config.targetImageView != null) {
            request.into(config.targetImageView);
        } else if (config.targetView != null) {
            request.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
                    config.targetView.setBackground(resource);
                }
            });
        } else if (config.targetDrawable != null) {
            request.into(config.targetDrawable);
        }
    }

    /**
     * config配置完成，调用glide into（）方法进行加载图片
     *
     * @param request glide的RequestBuilder
     * @param config  配置的GlideLoaderConfig
     */
    private void intoBitmapTarget(@NonNull RequestBuilder<Bitmap> request, @NonNull GlideLoaderConfig config) {
        // 设置listener
        if (config.requestListener4Bitmap != null) {
            request = request.listener(config.requestListener4Bitmap);
        }
        // 设置缩略图
        if (config.thumbnail > 0) {
            request = request.thumbnail(config.thumbnail);
        } else if (config.thumbnails4Bitmap != null) {
            request = request.thumbnail(config.thumbnails4Bitmap);
        }

        // glide加载
        if (config.targetImageView != null) {
            request.into(config.targetImageView);
        } else if (config.targetView != null) {
            request.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                    config.targetView.setBackground(new BitmapDrawable(resource));
                }
            });
        } else if (config.targetBitmap != null) {
            request.into(config.targetBitmap);
        }
    }
}
