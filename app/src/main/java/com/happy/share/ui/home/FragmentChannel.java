package com.happy.share.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.happy.share.R;
import com.happy.share.common.base.FragmentBaseBusiness;
import com.happy.share.tools.ToolToast;
import com.happy.share.ui.home.adapter.AdapterNews;
import com.happy.share.ui.home.bean.NewsBean;
import com.happy.share.widget.divider.RecycleViewDivider;
import com.happy.share.widget.recyclerview.RecyclerViewLoadMore;
import com.happy.share.widget.refresh.CustomSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 首页频道fragment <br/>
 * time: 2018/11/24 11:42 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class FragmentChannel extends FragmentBaseBusiness {
    private RecyclerViewLoadMore rvNews;
    private CustomSwipeRefreshLayout csrlRefresh;
    private AdapterNews mAdapter;
    private ProgressBar pbLoading;
    @Override
    public void initView(Bundle bundle) {
        rvNews = findView(R.id.rv_news);
        pbLoading = findView(R.id.pb_loading);
        csrlRefresh = findView(R.id.csrl_refresh);
    }

    @Override
    public void initAdapter() {
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNews.addItemDecoration((new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                1, 0XEFEFEF)));
        mAdapter = new AdapterNews(getActivity());
        mAdapter.setList(new ArrayList<>());
        rvNews.setAdapter(mAdapter);
        rvNews.setLoadMoreEnable(false);
        mAdapter.setOnItemClickListener((v, position) -> clickRecyclerView(position, v));
    }

    @Override
    public void initListener() {
        csrlRefresh.setOnRefreshListener(() -> {
            ToolToast.showToast("下拉刷新");
            csrlRefresh.setRefreshing(true);
            requestChannelNews();
        });
    }

    @Override
    public void bindData() {
        requestChannelNews();
    }

    @Override
    public int getContentViewResId() {
        return R.layout.fragment_channel;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void onViewClick(@NonNull View view) {

    }

    private void clickRecyclerView(int position, View v) {
        if (v.getId() == R.id.rl_item) {
            ToolToast.showToast("进入详情");
        }
    }

    /**
     * 请求对应频道的新闻消息
     */
    private void requestChannelNews() {
        pbLoading.setVisibility(View.VISIBLE);
        List<NewsBean> newsBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setNewTitle("这是新闻标题" + i);
            newsBean.setViewCounts(10 + i);
            newsBean.setNewsUrl("http://n.sinaimg.cn/sinacn/w500h250/20180309/7f34-fxpwyhw3189069.png");
            newsBean.setFromName("网易新闻" + i);
            newsBeans.add(newsBean);
        }
        mAdapter.setList(newsBeans);
        mAdapter.notifyDataSetChanged();
        csrlRefresh.setRefreshing(false);
        pbLoading.setVisibility(View.GONE);
    }
}
