package com.happy.share.widget.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.happy.share.R;


/**
 * desc：刷新控件 <br/>
 * time: 2017/11/10 上午10:46 <br/>
 * author：rooky <br/>
 * since V 1.0 <br/>
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

	public CustomSwipeRefreshLayout(Context context) {
		super(context);
	}

	public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		this.setColorSchemeResources(R.color.c_FFBF0808);
	}

}