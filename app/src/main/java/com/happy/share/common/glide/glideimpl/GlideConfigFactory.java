package com.happy.share.common.glide.glideimpl;

import android.support.annotation.NonNull;

import com.happy.share.common.glide.config.BaseConfigFactory;

/**
 * desc: glide加载图片时的配置工厂 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public class GlideConfigFactory extends BaseConfigFactory<GlideLoaderConfig> {


    /**
     * 如果glide都是在主线程调用的  size = 1即能满足需求，但不确定都是在主线程调用但
     */
    private static final int DEFAULT_POOL_SIZE = 10;


    private GlideConfigFactory() {
        super(DEFAULT_POOL_SIZE);
    }

    @NonNull
    @Override
    public GlideLoaderConfig createNewInstance() {
        return new GlideLoaderConfig();
    }

    public static GlideConfigFactory getInstance() {
        return SingleInstanceHolder.sGlideConfigFactory;
    }

    private static class SingleInstanceHolder {
        private static GlideConfigFactory sGlideConfigFactory = new GlideConfigFactory();
    }
}
