package com.happy.share.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.happy.share.common.base.cliker.IViewClickerInterceptor;
import com.happy.share.common.base.cliker.ViewClickerInterceptor;
import com.happy.share.tools.ToolActivity;
import com.happy.share.tools.ToolNet;

/**
 * desc: activity业务基类  <br/>
 * time: 2018/11/24 10:59 <br/>
 * author: 钟宾 <br/>
 * since V 1.0 <br/>
 */
public abstract class ActivityBaseBusiness extends ActivityRx implements IBaseView {
    /**
     * {@link android.view.View.OnClickListener} 事件拦截器。<br/>
     * 子类请通过 {@link #getClickerInterceptor()} 调用。
     */
    protected IViewClickerInterceptor mClickerInterceptor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        initView(getIntent() != null ? getIntent().getExtras() : null);
        initAdapter();
        initListener();
        if (ToolNet.isAvailable(this)) {
            bindData();
        } else {
            bindNoNetData();
        }
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

    @Override
    public void bindNoNetData() {

    }

    //================= 点击事件 相关 ===================//
    @Override
    public final void onClick(View view) {
        if (getClickerInterceptor().onClickBefore(this, view)) {
            onViewClick(view);
            getClickerInterceptor().onClickAfter(this, view);
        }
    }

    protected void onViewClick(@NonNull View view) {
        // 子类实现
    }

    /**
     * {@link android.view.View.OnClickListener#onClick(View)}拦截器
     */
    @NonNull
    protected IViewClickerInterceptor getClickerInterceptor() {
        if (mClickerInterceptor == null) {
            mClickerInterceptor = new ViewClickerInterceptor();
        }
        return mClickerInterceptor;
    }

    @Override
    public boolean isActive() {
        return !ToolActivity.isActivityFinishing(this);
    }
}
