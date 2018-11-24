package com.happy.share.common.base.cliker;

import android.support.annotation.NonNull;
import android.view.View;

import com.happy.share.R;
import com.happy.share.common.base.IBaseView;

/**
 * desc: {@link View.OnClickListener#onClick(View)} 拦截器基类 <br/>
 * time: 2017/9/29 下午2:30 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
abstract class BaseClickerInterceptor implements IViewClickerInterceptor {

	/**
	 * 最大点击间隔时间，单位：毫秒
	 */
	private static final short CLICK_MAX_INTERVAL_TIME_MS = 800;
	/**
	 * 最后点击view Id
	 */
	private int mLastClickId;
	/**
	 * 最后点击时间
	 */
	private long mLastClickTime;


	@Override
	public boolean onClickBefore(@NonNull IBaseView baseView, @NonNull View clickView) {
		return baseView.isActive() && (!isFastClick(clickView));
	}

	@Override
	public void onClickAfter(@NonNull IBaseView baseView, @NonNull View clickView) {

	}

	/**
	 * 是否快速点击事件
	 *
	 * @param clickView 被点击view
	 * @return true:是快速点击；false:非快速点击
	 */
	public boolean isFastClick(@NonNull View clickView) {
		if (clickView.getId() != mLastClickId) {
			saveClickSuccessRecord(clickView.getId());
			return false;
		}

		Object customMaxInterval = clickView.getTag(R.id.click_max_interval); //自定义间隔毫秒数

		if (customMaxInterval instanceof Integer) { // 设置了最大点击间隔时间
			if ((System.currentTimeMillis() - mLastClickTime) > ((int) customMaxInterval)) {
				saveClickSuccessRecord(clickView.getId());
				return false;
			}

			return true;
		} else if ((System.currentTimeMillis() - mLastClickTime) > CLICK_MAX_INTERVAL_TIME_MS) { //使用默认间隔时间
			saveClickSuccessRecord(clickView.getId());
			return false;
		} else { //快速点击
			return true;
		}
	}

	/**
	 * 保存成功点击按钮事件值
	 *
	 * @param currClickId View Click Id
	 */
	private void saveClickSuccessRecord(int currClickId) {
		mLastClickId = currClickId;
		mLastClickTime = System.currentTimeMillis();
	}

}