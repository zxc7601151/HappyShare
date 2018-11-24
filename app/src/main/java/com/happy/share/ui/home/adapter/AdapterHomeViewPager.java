package com.happy.share.ui.home.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.happy.share.common.consts.IntentConst;
import com.happy.share.tools.ToolList;
import com.happy.share.ui.home.FragmentChannel;
import com.happy.share.ui.home.bean.ChannelInfo;

import java.util.List;


/**
 * desc:  ViewPager的Adapter <br/>
 * auther: 周峰 <br/>
 * date: 2017/11/30 <br/>
 * since v0.0 <br>
 */
public class AdapterHomeViewPager extends PagerAdapter {


    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private FragmentChannel mCurrentPrimaryItem = null;

    private List<ChannelInfo> data;

    /**
     * fragments 集合
     */
    private SparseArray<FragmentChannel> mFragments = new SparseArray<>();


    public AdapterHomeViewPager(FragmentManager fm) {
        mFragmentManager = fm;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @SuppressWarnings("ReferenceEquality")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        final long itemId = getItemId(position);

        // Do we already have this fragment?
        String name = makeFragmentName(container.getId(), itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurTransaction.attach(fragment);
        } else {
            fragment = mFragments.get(data.get(position).getChannelId());
            if (fragment == null) {
                fragment = getItem(position);
                mCurTransaction.add(container.getId(), fragment,
                        makeFragmentName(container.getId(), itemId));
            } else {
                mCurTransaction.attach(fragment);
            }
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment) object);
    }

    @SuppressWarnings("ReferenceEquality")
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        FragmentChannel fragment = (FragmentChannel) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    private Fragment getItem(int position) {
        ChannelInfo channelInfo = data.get(position);
        FragmentChannel fragmentVideoChannel = new FragmentChannel();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConst.KEY_CHANNEL_ID, channelInfo.getChannelId());
        bundle.putString(IntentConst.KEY_CHANNEL_NAME, channelInfo.getChannelName());
        fragmentVideoChannel.setArguments(bundle);
        mFragments.put(channelInfo.getChannelId(), fragmentVideoChannel);
        return fragmentVideoChannel;
    }

    private long getItemId(int position) {
        return data.get(position).getChannelId();
    }

    @Override
    public int getCount() {
        return ToolList.getSize(data);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getChannelName();
    }

    public void setData(List<ChannelInfo> data) {
        this.data = data;
        SparseArray<FragmentChannel> tempFragments = new SparseArray<>();
        for (int i = 0; i < data.size(); i++) {
            ChannelInfo channelInfo = data.get(i);
            FragmentChannel fragmentChannel = mFragments.get(channelInfo.getChannelId());
            if (fragmentChannel != null && fragmentChannel.isActive()) {
                tempFragments.put(channelInfo.getChannelId(), fragmentChannel);
            }
        }
        mFragments.clear();
        mFragments = tempFragments;
    }

    public void onHiddenChanged(boolean hidden) {
        if (mCurrentPrimaryItem != null && mCurrentPrimaryItem.isActive()) {
            mCurrentPrimaryItem.onHiddenChanged(hidden);
        }
    }
}
