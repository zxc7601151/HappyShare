package com.happy.share.ui.home.helper;


import com.happy.share.common.base.FragmentBaseBusiness;

/**
 * desc: {@link com.ashokvarma.bottomnavigation.BottomNavigationBar}中单个底部栏显示容器 <br/>
 * time: 2017/11/1 下午1:42 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class BottomBarFragmentModel {

	/**
	 * 底部栏Bar位置
	 */
	private int tabPosition;
	/**
	 * Bottom bar
	 */
	private BottomBarModel bottomBarModel;
	/**
	 * 真正展示的视图
	 */
	private FragmentBaseBusiness fragment;
	/**
	 * 前一个Tab位置
	 */
	private int previousTabPosition;


	public BottomBarFragmentModel(int tabPosition) {
		this.tabPosition = tabPosition;
	}

	/**
	 * ==============getter and setter
	 */
	public int getTabPosition() {
		return tabPosition;
	}

	public void setTabPosition(int tabPosition) {
		this.tabPosition = tabPosition;
	}

	public BottomBarModel getBottomBarModel() {
		return bottomBarModel;
	}

	public void setBottomBarModel(BottomBarModel bottomBarModel) {
		this.bottomBarModel = bottomBarModel;
	}
	public FragmentBaseBusiness getFragment() {
		return fragment;
	}

	public void setFragment(FragmentBaseBusiness fragment) {
		this.fragment = fragment;
	}

	public int getPreviousTabPosition() {
		return previousTabPosition;
	}

	public void setPreviousTabPosition(int previousTabPosition) {
		this.previousTabPosition = previousTabPosition;
	}
}