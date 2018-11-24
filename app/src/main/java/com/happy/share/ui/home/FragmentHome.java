package com.happy.share.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.happy.share.R;
import com.happy.share.common.base.FragmentBaseBusiness;
import com.happy.share.tools.ToolToast;
import com.happy.share.tools.ToolView;
import com.happy.share.ui.home.adapter.AdapterHomeViewPager;
import com.happy.share.ui.home.bean.ChannelInfo;
import com.happy.share.widget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 首页fragment <br/>
 * time: 2018/11/24 11:42 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class FragmentHome extends FragmentBaseBusiness {
    private ViewPager rvpHome;
    private FrameLayout flMore;
    private AdapterHomeViewPager mAdapter;
    private MagicIndicator magicIndicator;
    @Override
    public void initView(Bundle bundle) {
        rvpHome = findView(R.id.rvp_home);
        flMore = findView(R.id.fl_more);
        magicIndicator = findView(R.id.magic_indicator);

    }

    @Override
    public int getContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initAdapter() {
        mAdapter = new AdapterHomeViewPager(getChildFragmentManager());
        rvpHome.setOffscreenPageLimit(1);
    }

    @Override
    public void initListener() {
        ToolView.setOnClickListener(this,flMore);
    }

    @Override
    public void bindData() {
        requestChannelLists();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mAdapter != null) {
            mAdapter.onHiddenChanged(hidden);
        }
    }

    @Override
    protected void onViewClick(@NonNull View view) {
        switch (view.getId()){
            case R.id.fl_more:
                ToolToast.showToast("频道编辑");
                break;
        }
    }

    private void requestChannelLists(){
        List<ChannelInfo> channelInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.setChannelId(i+1);
            channelInfo.setChannelName("测试" + i);
            channelInfos.add(channelInfo);
        }
        mAdapter.setData(channelInfos);
        rvpHome.setAdapter(mAdapter);
        initMagicIndicator(channelInfos);
        rvpHome.setCurrentItem(0);
    }

    /**
     * 初始化指示器
     */
    private void initMagicIndicator(List<ChannelInfo> channelInfoList) {
        magicIndicator.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white_ffffff));
        CommonNavigator commonNavigator;
        commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.15f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return channelInfoList == null ? 0 : channelInfoList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(channelInfoList.get(index).getChannelName());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(getActivity(), R.color.grey_666666));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getActivity(), R.color.black_3333333));
                simplePagerTitleView.setOnClickListener(v -> rvpHome.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 0));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(getActivity(), R.color.c_FFBF0808));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, rvpHome);
    }
}
