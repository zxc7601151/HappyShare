package com.happy.share.ui.home.bean;

import android.os.Parcel;

import com.happy.share.api.bean.BaseParcelableBean;

/**
 * desc: 频道实体 <br/>
 * time: 2018/11/24 14:27 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ChannelInfo  extends BaseParcelableBean {
    private int channelId;
    private String channelName;
    private int gmtCreated;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(int gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.channelId);
        dest.writeString(this.channelName);
        dest.writeInt(this.gmtCreated);
    }

    public ChannelInfo() {
    }

    protected ChannelInfo(Parcel in) {
        this.channelId = in.readInt();
        this.channelName = in.readString();
        this.gmtCreated = in.readInt();
    }

    public static final Creator<ChannelInfo> CREATOR = new Creator<ChannelInfo>() {
        @Override
        public ChannelInfo createFromParcel(Parcel source) {
            return new ChannelInfo(source);
        }

        @Override
        public ChannelInfo[] newArray(int size) {
            return new ChannelInfo[size];
        }
    };
}
