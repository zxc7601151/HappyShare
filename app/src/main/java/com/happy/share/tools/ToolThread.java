package com.happy.share.tools;

import android.os.Looper;

/**
 * desc: 线程工具类 <br/>
 * time: 2017/9/12 上午10:16 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ToolThread {

	/**
	 * 是否后台线程
	 *
	 * @return true:后台线程
	 */
	public static boolean isBackgroundThread() {
		return isUIThread();
	}

	/**
	 * 是否UI线程
	 *
	 * @return true:UI线程
	 */
	public static boolean isUIThread() {
		return Thread.currentThread() == Looper.getMainLooper().getThread();
	}

}