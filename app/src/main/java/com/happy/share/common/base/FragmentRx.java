package com.happy.share.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * desc: RxFragment <br/>
 * time: 2017/9/11 17:29 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public abstract class FragmentRx extends FragmentBase implements LifecycleProvider<FragmentEvent> {

	private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

	@Override
	@NonNull
	@CheckResult
	public final Observable<FragmentEvent> lifecycle() {
		return lifecycleSubject.hide();
	}

	@Override
	@NonNull
	@CheckResult
	public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
		return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
	}

	@Override
	@NonNull
	@CheckResult
	public final <T> LifecycleTransformer<T> bindToLifecycle() {
		return RxLifecycleAndroid.bindFragment(lifecycleSubject);
	}

	@Override
	@CallSuper
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		lifecycleSubject.onNext(FragmentEvent.ATTACH);
	}

	@Override
	@CallSuper
	public void onAttach(Context context) {
		super.onAttach(context);
		lifecycleSubject.onNext(FragmentEvent.ATTACH);
	}

	@Override
	@CallSuper
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lifecycleSubject.onNext(FragmentEvent.CREATE);
	}

	@Override
	@CallSuper
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// FragmentEvent中没有ACTIVITY_CREATED，使用CREATE_VIEW替代
		lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
	}

	@Override
	@CallSuper
	public void onStart() {
		super.onStart();
		lifecycleSubject.onNext(FragmentEvent.START);
	}

	@Override
	@CallSuper
	public void onResume() {
		super.onResume();
		lifecycleSubject.onNext(FragmentEvent.RESUME);
	}

	@Override
	@CallSuper
	public void onPause() {
		lifecycleSubject.onNext(FragmentEvent.PAUSE);
		super.onPause();
	}

	@Override
	@CallSuper
	public void onStop() {
		lifecycleSubject.onNext(FragmentEvent.STOP);
		super.onStop();
	}

	@Override
	@CallSuper
	public void onDestroyView() {
		lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
		super.onDestroyView();
	}

	@Override
	@CallSuper
	public void onDestroy() {
		lifecycleSubject.onNext(FragmentEvent.DESTROY);
		super.onDestroy();
	}

	@Override
	@CallSuper
	public void onDetach() {
		lifecycleSubject.onNext(FragmentEvent.DETACH);
		super.onDetach();
	}
}
