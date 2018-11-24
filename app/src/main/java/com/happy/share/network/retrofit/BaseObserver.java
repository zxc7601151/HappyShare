package com.happy.share.network.retrofit;


import android.util.Log;

import com.happy.share.api.bean.NetResponse;
import com.happy.share.network.ApiException;
import com.happy.share.network.ApiExceptionCode;
import com.happy.share.tools.ToolNumber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * desc: Observer基类 <br/>
 * time: 2017/9/7 15:48 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public abstract class BaseObserver<T> implements Observer<T> {
	private Disposable mDisposable;

	protected abstract void onResponse(boolean isSuccess, T t);

	@Override
	public void onSubscribe(@NonNull Disposable d) {
		mDisposable = d;
	}

	@Override
	public void onNext(@NonNull T t) {
		//如果不是接口请求响应
		if (!isNetResponse(t)) {
			onResponse(true, t);
		}else{
			doNetResponse(t);
		}
	}

	@Override
	public void onError(@NonNull Throwable e) {
		onResponse(false, null);
		if (e instanceof ApiException){
			((ApiException) e).apiExceptionCode(((ApiException) e).getCode());
		} else {
			Log.e(BaseObserver.class.getSimpleName(), "BaseObserver catch error ", e);
		}
		disposed();
	}

	public void disposed() {
		if (mDisposable != null && !mDisposable.isDisposed()) {
			mDisposable.dispose();
		}
	}

	@Override
	public void onComplete() {
		disposed();
	}

	/**
	 * 处理接口请求响应
	 */
	private void doNetResponse(@NonNull T t) {
		if (isOk(t)){
			onResponse(true, t);
		}else{
			checkIsNeedThrowException(t);
		}
	}

	/**
	 * 接口是否响应成功
	 */
	private boolean isOk(@NonNull T t) {
		return ((NetResponse) t).getMessageCode().equals("0");
	}

	/**
	 * 是否需要抛出自定义异常
	 */
	private void checkIsNeedThrowException(T t) {
		NetResponse response = ((NetResponse) t);
		if (isExceptionCode(ToolNumber.toInt(response.getMessageCode()))){
			throw new ApiException(ToolNumber.toInt(response.getMessageCode()),response.getMessage());
		}else{
			onResponse(true, t);
		}
	}

	/**
	 * 返回是否是NetResponse实体
	 */
	private boolean isNetResponse(@NonNull T t) {
		return t instanceof NetResponse;
	}

	/**
	 * 错误码是否需要处理
	 * @param exceptionCode 错误码
	 */
	private boolean isExceptionCode(int exceptionCode){
		for (ApiExceptionCode code:
				ApiExceptionCode.values()) {
			if (code.getExceptionCode() == exceptionCode){
				return true;
			}
		}
		return false;
	}
}