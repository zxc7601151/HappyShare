package com.happy.share.tools;

import android.support.v4.app.Fragment;

import com.happy.share.common.base.ActivityRx;
import com.happy.share.common.base.FragmentRx;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc: rx工具类 <br/>
 * time: 2017/10/24 19:00 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 *
 */
public class ToolRx {

	/**
	 * 统一事件处理有具体返回数据
	 *
	 * @return 返回处理好的Observable
	 */
	public static <T> ObservableTransformer<T, T> processDefault(final ActivityRx activity) {
		return upstream -> {
            Observable<T> tObservable = upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            if (!ToolActivity.isActivityFinishing(activity)) {
                return tObservable
                        .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
            }
            return tObservable;
        };
	}

	/**
	 * 统一事件处理有具体返回数据
	 *
	 * @return 返回处理好的Observable
	 */
	public static <T> ObservableTransformer<T, T> processDefault(final FragmentRx fragment) {
		return upstream -> {
			Observable<T> tObservable = upstream
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
			if (!isFragmentDeprecated(fragment)) {
				return tObservable
						.compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
			}
			return tObservable;
		};
	}

	/**
	 * Fragment是否过期
	 *
	 * @param fragment fragment
	 * @return true:过期
	 */
	public static <T extends Fragment> boolean isFragmentDeprecated(T fragment) {
		return fragment == null || !fragment.isAdded()
				|| fragment.getActivity() == null || fragment.getActivity().isFinishing();

	}


	/**
	 * 统一事件处理有具体返回数据
	 *
	 * @return 返回处理好的Single
	 */
	public static <T> SingleTransformer<T, T> processSingleDefault(final ActivityRx activity) {
		return upstream -> {
			Single<T> tObservable = upstream
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
			if (!ToolActivity.isActivityFinishing(activity)) {
				return tObservable
						.compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
			}
			return tObservable;
		};
	}
}
