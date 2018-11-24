package com.happy.share.common.glide.strategy;


import android.support.annotation.NonNull;

/**
 * desc: 图片加载框架需实现的功能 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public interface ILoadStrategy<T> {


    /**
     * 调用三方图片加载框架的入口，根据config获取需要的加载参数
     *
     * @param config 图片配置参数
     */
    void loadImage(@NonNull T config);

}
