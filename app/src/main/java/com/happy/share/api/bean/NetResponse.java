package com.happy.share.api.bean;

import android.os.Parcel;

/**
 * desc: 服务器返回数据 <br/>
 * time: 2017/9/11 下午7:03 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class NetResponse extends BaseParcelableBean {

	/**
	 * 服务器返回的信息
	 */
	private String message;
	/**
	 * 通信结果
	 * 0:成功, 1:失败
	 */
	private int result;

	/**
	 * 服务器返回码
	 */
	private String messageCode;

	/**
	 * 服务器返回错误类型
     * 错误类型(0:默认,101:常规错误,102:登录错误...)
	 */
	private String messageType;


	public NetResponse() {

	}

	/* getter and setter */
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public boolean isResultOk() {
		return result == 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.message);
		dest.writeInt(this.result);
		dest.writeString(this.messageCode);
		dest.writeString(this.messageType);
	}

	protected NetResponse(Parcel in) {
		this.message = in.readString();
		this.result = in.readInt();
		this.messageCode = in.readString();
		this.messageType = in.readString();
	}

	public static final Creator<NetResponse> CREATOR = new Creator<NetResponse>() {
		@Override
		public NetResponse createFromParcel(Parcel source) {
			return new NetResponse(source);
		}

		@Override
		public NetResponse[] newArray(int size) {
			return new NetResponse[size];
		}
	};
}