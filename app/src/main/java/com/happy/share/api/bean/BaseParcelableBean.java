package com.happy.share.api.bean;

import android.os.Parcelable;

/**
 * desc: Parcelable基类 <br/>
 * time: 2017/9/11 下午7:02 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public abstract class BaseParcelableBean implements Parcelable {

	@Override
	public int describeContents() {
		return 0;
	}

}
