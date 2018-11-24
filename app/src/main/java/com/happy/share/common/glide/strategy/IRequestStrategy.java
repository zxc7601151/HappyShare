package com.happy.share.common.glide.strategy;


import android.content.Context;
import android.support.annotation.NonNull;

/**
 * desc: 对request的管理策略 <br/>
 * time: 2018-7-13 <br/>
 * author: 杨斌才 <br/>
 * since: V 1.0 <br/>
 */
public interface IRequestStrategy {


    /**
     * 暂停请求
     */
    void pauseRequests(@NonNull Context context);

    /**
     * 恢复请求
     */
    void resumeRequests(@NonNull Context context);

}
