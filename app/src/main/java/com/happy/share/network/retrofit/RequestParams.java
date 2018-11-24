package com.happy.share.network.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.happy.share.api.bean.BaseParcelableBean;

/**
 * desc: 网络请求参数基础bean <br/>
 * time: 2017/11/10 16:27 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */

public class RequestParams extends BaseParcelableBean {

    private String appKey;
    private String appTimestamp;
    private String userId;

    //终端手机类型 1代表安卓机
    private Integer terminalType;

    //版本号
    private String appVersion;
    //语言类型
    private Integer lang;
    //手机设备号
    private String registerId;
    //cookieId
    private String cookieId;

    /**
     * 4:topvid, 6:mello. 缺省值4
     */
    private String appId;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppTimestamp() {
        return appTimestamp;
    }

    public void setAppTimestamp(String appTimestamp) {
        this.appTimestamp = appTimestamp;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public RequestParams() {
    }


    @Override
    public String toString() {
        return "RequestParams{" +
                "userId=" + userId +
                ", terminalType='" + terminalType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appKey);
        dest.writeString(this.appTimestamp);
        dest.writeString(this.userId);
        dest.writeValue(this.terminalType);
        dest.writeString(this.appVersion);
        dest.writeValue(this.lang);
        dest.writeString(this.registerId);
        dest.writeString(this.cookieId);
        dest.writeString(this.appId);
    }

    protected RequestParams(Parcel in) {
        this.appKey = in.readString();
        this.appTimestamp = in.readString();
        this.userId = in.readString();
        this.terminalType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appVersion = in.readString();
        this.lang = (Integer) in.readValue(Integer.class.getClassLoader());
        this.registerId = in.readString();
        this.cookieId = in.readString();
        this.appId = in.readString();
    }

    public static final Parcelable.Creator<RequestParams> CREATOR = new Parcelable.Creator<RequestParams>() {
        @Override
        public RequestParams createFromParcel(Parcel source) {
            return new RequestParams(source);
        }

        @Override
        public RequestParams[] newArray(int size) {
            return new RequestParams[size];
        }
    };
}
