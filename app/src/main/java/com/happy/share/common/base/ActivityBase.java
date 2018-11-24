package com.happy.share.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * desc: 所有Activity基类 <br/>
 * time: 2017/9/8 下午2:21 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public abstract class ActivityBase extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstance().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 获取View句柄
     */
    protected final <T extends View> T findView(@IdRes int resId) {
        return super.findViewById(resId);
    }

}