package com.happy.share.ui;

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
import com.happy.share.tools.ToolToast;
import com.happy.share.ui.home.helper.BottomBarFragmentModel;
import com.happy.share.ui.home.helper.BottomBarModel;
import com.happy.share.ui.home.helper.MainActivityHelper;

import java.util.List;

/**
 * desc: 首页 <br/>
 * time: 2018/11/24 10:36 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ActivityHome extends ActivityBaseBusiness {
    /**
     * 当前显示的Fragment
     */
    private FragmentBaseBusiness mCurrentFragment;
    private BottomNavigationBar bottomNavigation;

    /**
     * 当前页帮助类，通过{@link #getHelper()}使用该对象。
     */
    private MainActivityHelper mMainHelper;
    /**
     * 底部栏数据
     */
    private SparseArray<BottomBarFragmentModel> mBottomBarFragments;
    private long mLatestTime4ExitTip;

    @Override
    public void initView(Bundle bundle) {
        mBottomBarFragments = getHelper().createBottomBarFragments();
        List<BottomBarModel> barModels = getHelper().createBottomBarData(this);
        getHelper().setBars2BarFragmentModel(mBottomBarFragments, barModels);

        /* 显示底部栏bar */
        bottomNavigation = findView(R.id.main_bnv_main_bar);
        bottomNavigation.setMode(BottomNavigationBar.MODE_FIXED);
        getHelper().showBottomBars(bottomNavigation, mBottomBarFragments);
        bottomNavigation.selectTab(0, false);

        /* 显示第一个Fragment */
        BottomBarFragmentModel launcherBarFragmentModel = mBottomBarFragments.get(0);
        FragmentBaseBusiness launcherFragment = createLauncherFragment(launcherBarFragmentModel);
        launcherBarFragmentModel.setFragment(launcherFragment);
        showLauncherFragment(launcherFragment);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initListener() {
        bottomNavigation.setTabSelectedListener(mOnTabSelectedListener);
    }

    @Override
    protected void onViewClick(@NonNull View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isActive() || (mCurrentFragment == null) || !mCurrentFragment.isActive()) {
            // 程序状态出现异常，重启App
            ApplicationBase.getInstance().restartApp(ActivityLoading.class, 500);
            return true;
        }

        switch (keyCode) {
            /* 物理键返回 */
            case KeyEvent.KEYCODE_BACK:
                return onBackKeyDown();
            default:
        }

        return false;
    }

    private boolean onBackKeyDown() {
            /* 退出 */
        if (mLatestTime4ExitTip <= 0 || System.currentTimeMillis() - mLatestTime4ExitTip > 3000) {
            mLatestTime4ExitTip = System.currentTimeMillis();
            ToolToast.showToast(getString(R.string.tip_exit_system));
        } else {
            ApplicationBase.getInstance().exit(500);
        }
        return true;
    }

    /**
     * 显示下一个Fragment
     *
     * @param currentFragment 当前显示的Fragment
     * @param nextFragment    即将显示的Fragment
     */
    private void showNextFragment(FragmentBaseBusiness currentFragment, FragmentBaseBusiness nextFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (nextFragment.isAdded()) {
            transaction.hide(currentFragment).show(nextFragment).commit();
        } else {
            transaction.hide(currentFragment).add(R.id.main_fl_main_container, nextFragment
                    , nextFragment.getClass().getSimpleName()).show(nextFragment).commit();
        }
        mCurrentFragment = nextFragment;
    }

    /**
     * 显示启动Fragment
     */
    private void showLauncherFragment(FragmentBaseBusiness firstFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl_main_container, firstFragment, (firstFragment.getClass().getSimpleName()));
        transaction.commit();
        mCurrentFragment = firstFragment;
    }

    /**
     * 创建启动Fragment
     */
    @NonNull
    private FragmentBaseBusiness createLauncherFragment(BottomBarFragmentModel launcherBarFragmentModel) {
        return launcherBarFragmentModel.getFragment();
    }

    @NonNull
    private MainActivityHelper getHelper() {
        if (mMainHelper == null) {
            mMainHelper = new MainActivityHelper(this);
        }
        return mMainHelper;
    }

    BottomNavigationBar.SimpleOnTabSelectedListener mOnTabSelectedListener = new BottomNavigationBar.SimpleOnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            if (getHelper().hasPositionData(mBottomBarFragments, position) && isActive()) {
                /* 懒加载未初始化的Fragment */
                BottomBarFragmentModel nextFragmentBar = mBottomBarFragments.get(position);
                FragmentBaseBusiness nextFragment = nextFragmentBar.getFragment();
                /* 显示点击的Tab Fragment */
                if (nextFragment != mCurrentFragment) {
                    /* 设置公共参数 */
                    getHelper().setArgumentsIfNull(nextFragment);

                    showNextFragment(mCurrentFragment, nextFragment);
                }
            }
        }

        @Override
        public void onTabReselected(int position) {
            super.onTabReselected(position);
        }
    };
}
