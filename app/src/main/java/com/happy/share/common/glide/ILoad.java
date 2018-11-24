package com.happy.share.common.glide;

import com.happy.share.common.glide.config.LoaderConfig;
import com.happy.share.common.glide.strategy.ICacheStrategy;
import com.happy.share.common.glide.strategy.ILoadStrategy;
import com.happy.share.common.glide.strategy.IRequestStrategy;

/**
 * desc: 封装三方图片框架的实现类，正在调用三方图片框架去加载，除了ICacheStrategy, ILoadStrategy <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public interface ILoad<T extends LoaderConfig> extends ICacheStrategy, ILoadStrategy<T>, IRequestStrategy {

}
