package com.happy.share.common.base;

import android.os.Bundle;

/**
 * desc: 视图基类 <br/>
 * time: 2018/11/24 14:37 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public interface IBaseView {
    void initView(Bundle bundle);

    void initAdapter();

    void initListener();

    void bindData();

    void bindNoNetData();

    int getContentViewResId();

    boolean isActive();
}
