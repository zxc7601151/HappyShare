package com.happy.share.ui.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.happy.share.R;
import com.happy.share.common.base.FragmentBaseBusiness;


/**
 * desc: 任务fragment <br/>
 * time: 2018/11/24 16:25 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class FragmentTask extends FragmentBaseBusiness {
    @Override
    public void initView(Bundle bundle) {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.fragment_task;
    }

    @Override
    public void initAdapter() {
    }

    @Override
    public void initListener() {
//        ToolView.setOnClickListener(this,flMore);
    }

    @Override
    public void bindData() {
    }

    @Override
    protected void onViewClick(@NonNull View view) {
        switch (view.getId()) {
        }
    }


}
