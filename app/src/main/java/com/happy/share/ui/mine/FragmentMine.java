package com.happy.share.ui.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.happy.share.R;
import com.happy.share.common.base.FragmentBaseBusiness;


/**
 * desc: 我的fragment <br/>
 * time: 2018/11/24 16:40 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class FragmentMine extends FragmentBaseBusiness {
    @Override
    public void initView(Bundle bundle) {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.fragment_mine;
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
