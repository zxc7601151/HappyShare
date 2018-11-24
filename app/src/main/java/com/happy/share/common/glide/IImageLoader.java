package com.happy.share.common.glide;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.happy.share.common.glide.config.LoaderConfig;
import com.happy.share.common.glide.strategy.ICacheStrategy;
import com.happy.share.common.glide.strategy.IRequestStrategy;

import java.io.File;

/**
 * desc: 图片加载框架容器需实现的接口 <br/>
 * 也可添加一些额外接口，如{@link IRequestStrategy}，目前JC未用到，暂不添加 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public interface IImageLoader<T extends LoaderConfig> extends ICacheStrategy, IRequestStrategy {


    @NonNull
    T load(@NonNull String url);

    @NonNull
    T load(@NonNull Uri uri);

    @NonNull
    T load(@NonNull File file);

    @NonNull
    T load(@DrawableRes int res);
}
