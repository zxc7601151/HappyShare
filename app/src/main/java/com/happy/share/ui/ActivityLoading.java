package com.happy.share.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.happy.share.ApplicationBase;
import com.happy.share.R;
import com.happy.share.common.base.ActivityBaseBusiness;
import com.happy.share.common.base.FragmentBaseBusiness;
import com.happy.share.ui.home.helper.BottomBarFragmentModel;
import com.happy.share.ui.home.helper.BottomBarModel;
import com.happy.share.ui.home.helper.MainActivityHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;

/**
 * desc: loading页 <br/>
 * time: 2018/11/24 10:36 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ActivityLoading extends ActivityBaseBusiness {

    @Override
    public void initView(Bundle bundle) {
        goToMainActivity();
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_loading;
    }

    /**
     * 去主页
     */
    private void goToMainActivity() {
        Observable.just(0)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Functions.actionConsumer(() -> {
                    Intent intent = new Intent(ActivityLoading.this,ActivityHome.class);
                    startActivity(intent);
                    finish();
                }));
    }
}
