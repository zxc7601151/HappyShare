package com.happy.share.ui.home.helper;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * desc: 底部栏 <br/>
 * time: 2017/10/19 下午4:11 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class BottomBarModel {

	/**
	 * bar标题
	 */
	private String barTitle;
	/**
	 * 选中标题图片资源
	 */
	private int iconResource;
	/**
	 * 未选中标题图片资源
	 */
	private int inActiveIconResource;
	/**
	 * 选中文案颜色
	 */
	private int activeColor;
	/**
	 * 未选中文案颜色
	 */
	private int inActiveColor;


	public BottomBarModel(@DrawableRes int iconResource, String barTitle) {
		this.iconResource = iconResource;
		this.barTitle = barTitle;
	}

	public String getBarTitle() {
		return barTitle;
	}

	public void setBarTitle(@NonNull String barTitle) {
		this.barTitle = barTitle;
	}

	public int getIconResource() {
		return iconResource;
	}

	public void setIconResource(@DrawableRes int iconResource) {
		this.iconResource = iconResource;
	}

	public int getInActiveIconResource() {
		return inActiveIconResource;
	}

	public void setInActiveIconResource(@DrawableRes int inActiveIconResource) {
		this.inActiveIconResource = inActiveIconResource;
	}

	public int getActiveColor() {
		return activeColor;
	}

	public void setActiveColor(@ColorRes int activeColor) {
		this.activeColor = activeColor;
	}

	public int getInActiveColor() {
		return inActiveColor;
	}

	public void setInActiveColor(@ColorRes int inActiveColor) {
		this.inActiveColor = inActiveColor;
	}

}
