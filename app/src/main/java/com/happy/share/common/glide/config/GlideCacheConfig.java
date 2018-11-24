package com.happy.share.common.glide.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

/**
 * desc: Glide缓存配置 <br/>
 * time: 2017-3-24  上午11:54:08  <br/>
 * author: 义仍  <br/>
 * since V6.0 <br/>
 */
@GlideModule
public class GlideCacheConfig extends LibraryGlideModule {


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}