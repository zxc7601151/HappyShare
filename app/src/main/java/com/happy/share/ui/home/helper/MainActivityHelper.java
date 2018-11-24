package com.happy.share.ui.home.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.happy.share.R;
import com.happy.share.common.base.ActivityBaseBusiness;
import com.happy.share.common.base.FragmentBaseBusiness;
import com.happy.share.ui.ActivityHome;
import com.happy.share.ui.advertising.FragmentAdvertising;
import com.happy.share.ui.home.FragmentHome;
import com.happy.share.ui.mine.FragmentMine;
import com.happy.share.ui.task.FragmentTask;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: ActivityMain 帮助类 <br/>
 * time: 2017/10/19 下午4:51 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class MainActivityHelper {
    private ActivityHome mActivity;

    public MainActivityHelper(ActivityBaseBusiness activity) {
        mActivity = (ActivityHome) activity;
    }

    /**
     * 获取指定fragment的BottomBarFragmentModel对象
     */
    @Nullable
    public BottomBarFragmentModel getBarFragmentModel(SparseArray<BottomBarFragmentModel> bottomBarFragments
            , FragmentBaseBusiness fragment) {
        if (bottomBarFragments != null && fragment != null) {
            for (int i = 0; i < bottomBarFragments.size(); i++) {
                BottomBarFragmentModel barFragment = bottomBarFragments.get(i);

                if ((barFragment != null) && (barFragment.getFragment() == fragment)) {
                    return barFragment;
                }
            }
        }

        return null;
    }

    /**
     * 指定位置数据是否存在
     *
     * @return true:存在
     */
    public boolean hasPositionData(SparseArray<BottomBarFragmentModel> mBottomBarFragments, int position) {
        if ((mBottomBarFragments == null) || (mBottomBarFragments.get(position) == null)) {
            return false;
        }
        return true;
    }

    /**
     * 如果Fragment bundle为空，初始化Arguments参数
     */
    public void setArgumentsIfNull(FragmentBaseBusiness fragment) {
        if (fragment != null && fragment.getArguments() == null) {
            fragment.setArguments(new Bundle());
        }
    }

    /**
     * 指定tag是否存在{@link FragmentManager}中
     *
     * @return true:存在，说明该Manager已经存在FragmentManager中
     */
    public boolean isTagInFragmentManager(FragmentManager fragmentManager, FragmentBaseBusiness fragment) {
        return (fragmentManager != null) && (fragment != null);
    }

    /**
     * 设置底部栏数据(barModels)到bottomBarFragments中
     */
    public void setBars2BarFragmentModel(@NonNull SparseArray<BottomBarFragmentModel> bottomBarFragments, List<BottomBarModel> barModels) {
        if (barModels != null) {
            for (int i = 0; i < barModels.size(); i++) {
                BottomBarModel bar = barModels.get(i);
                BottomBarFragmentModel barFragment = bottomBarFragments.get(i);

                if (barFragment != null) {
                    barFragment.setBottomBarModel(bar);
                }
            }
        }
    }

    /**
     * 创建底部栏对应的Fragment数据
     */
    @NonNull
    public SparseArray<BottomBarFragmentModel> createBottomBarFragments() {
        SparseArray<BottomBarFragmentModel> allBarFragments = new SparseArray<>(5);
        putBottomBarFragment(allBarFragments, 0, new FragmentHome());
        putBottomBarFragment(allBarFragments, 1, new FragmentTask());
        putBottomBarFragment(allBarFragments, 2, new FragmentHome());
//        putBottomBarFragment(allBarFragments, 3, FragmentFollow.A_FOLLOE_DETAIL);
        putBottomBarFragment(allBarFragments, 3, new FragmentAdvertising());
        putBottomBarFragment(allBarFragments, 4, new FragmentMine());
        return allBarFragments;
    }


    private void putBottomBarFragment(@NonNull SparseArray<BottomBarFragmentModel> allBarFragments
            , int tabPosition, @NonNull FragmentBaseBusiness fragment) {
        BottomBarFragmentModel bottomBarItem = new BottomBarFragmentModel(tabPosition);
        bottomBarItem.setFragment(fragment);
        allBarFragments.put(tabPosition, bottomBarItem);
    }

    /**
     * 显示底部栏
     */
    public void showBottomBars(@NonNull BottomNavigationBar bottomNavigation, SparseArray<BottomBarFragmentModel> bottomBarFragments) {
        if (bottomBarFragments != null && (bottomBarFragments.size() > 0)) {
            for (int i = 0; i < bottomBarFragments.size(); i++) {
                if (bottomBarFragments.get(i) != null) {
                    BottomBarModel bar = bottomBarFragments.get(i).getBottomBarModel();
                    BottomNavigationItem barItem = new BottomNavigationItem(bar.getIconResource(), bar.getBarTitle());
                    barItem.setInactiveIconResource(bar.getInActiveIconResource());
                    barItem.setActiveColorResource(bar.getActiveColor()).setInActiveColorResource(bar.getInActiveColor());
                    bottomNavigation.addItem(barItem);
                }
            }

            bottomNavigation.initialise();
        }
    }

    /**
     * 创建底部栏数据
     */
    @NonNull
    public List<BottomBarModel> createBottomBarData(Context ctx) {
        List<BottomBarModel> barModelList = new ArrayList<>(4);
        /* tab0 - 首页 */
        BottomBarModel home = new BottomBarModel(R.drawable.ic_home_checked, ctx.getString(R.string.home));
        home.setActiveColor(R.color.c_FFBF0808);
        home.setInActiveIconResource(R.drawable.ic_home_normal);
        home.setInActiveColor(R.color.black_3333333);
        barModelList.add(home);

		/* tab1 - 任务 */
        BottomBarModel task = new BottomBarModel(R.drawable.ic_find_checked, ctx.getString(R.string.task));
        task.setActiveColor(R.color.c_FFBF0808);
        task.setInActiveIconResource(R.drawable.ic_find_normal);
        task.setInActiveColor(R.color.black_3333333);
        barModelList.add(task);

		/* tab2 - 一键植入 */
        BottomBarModel implant = new BottomBarModel(R.drawable.ic_implant, ctx.getString(R.string.one_key_implant));
        implant.setActiveColor(R.color.c_FFBF0808);
        implant.setInActiveIconResource(R.drawable.ic_implant);
        implant.setInActiveColor(R.color.black_3333333);
        barModelList.add(implant);

		/* tab3 - 推广 */
        BottomBarModel advertising = new BottomBarModel(R.drawable.ic_journey_checked, ctx.getString(R.string.advertising));
        advertising.setActiveColor(R.color.c_FFBF0808);
        advertising.setInActiveIconResource(R.drawable.ic_journey_normal);
        advertising.setInActiveColor(R.color.black_3333333);
        barModelList.add(advertising);

		/* tab4 - me */
        BottomBarModel me = new BottomBarModel(R.drawable.ic_mine_checked, ctx.getString(R.string.mine));
        me.setActiveColor(R.color.c_FFBF0808);
        me.setInActiveIconResource(R.drawable.ic_mine_normal);
        me.setInActiveColor(R.color.black_3333333);
        barModelList.add(me);

        return barModelList;
    }
}