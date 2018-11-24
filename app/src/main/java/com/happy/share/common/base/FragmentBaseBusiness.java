package com.happy.share.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happy.share.ApplicationBase;
import com.happy.share.common.base.cliker.IViewClickerInterceptor;
import com.happy.share.common.base.cliker.ViewClickerInterceptor;
import com.happy.share.tools.ToolNet;
import com.happy.share.tools.ToolRx;

/**
 * desc: activity业务基类  <br/>
 * time: 2018/11/24 10:59 <br/>
 * author: 钟宾 <br/>
 * since V 1.0 <br/>
 */
public abstract class FragmentBaseBusiness extends FragmentRx implements IBaseView {
    private View mFragmentView;
    /**
     * {@link android.view.View.OnClickListener} 事件拦截器。<br/>
     * 子类请通过 {@link #getClickerInterceptor()} 调用。
     */
    protected IViewClickerInterceptor mClickerInterceptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(getContentViewResId(), container, false);
        initView(getArguments());
        initAdapter();
        initListener();
        if (ToolNet.isAvailable(ApplicationBase.getContext())) {
            bindData();
        } else {
            bindNoNetData();
        }
        return mFragmentView;
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

    /**
     * 获取View句柄
     */
    protected final <T extends View> T findView(@IdRes int resId) {
        if (mFragmentView == null) {
            return null;
        }
        return mFragmentView.findViewById(resId);
    }

    public boolean isActive() {
        return !ToolRx.isFragmentDeprecated(this);
    }
}
