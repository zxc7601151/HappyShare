package com.happy.share.common.base.cliker;

import android.support.annotation.NonNull;
import android.view.View;

import com.happy.share.common.base.ActivityBase;
import com.happy.share.common.base.IBaseView;

/**
 * desc: {@link View.OnClickListener} 点击事件拦截器 <br/>
 * time: 2017/9/8 下午3:03 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public interface IViewClickerInterceptor {

	/**
	 * Click点击前操作
	 *
	 * @param baseView  视图
	 * @param clickView 被点击View
	 * @return click 是否合法。true：合法
	 */
	boolean onClickBefore(@NonNull IBaseView baseView, @NonNull View clickView);

	/**
	 * Click点击后操作
	 *
	 * @param baseView  视图
	 * @param clickView 被点击View
	 */
	void onClickAfter(@NonNull IBaseView baseView, @NonNull View clickView);

}
