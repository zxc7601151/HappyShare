package com.happy.share.ui.home.bean;

import android.os.Parcel;

import com.happy.share.api.bean.BaseParcelableBean;

/**
 * desc: 新闻实体 <br/>
 * time: 2018/11/24 15:15 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class NewsBean extends BaseParcelableBean {
    private int newsId;
    private String newTitle;
    private String newsContent;
    private String fromName;
    private long viewCounts;
    private String newsUrl;
    private int newsType;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public long getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(long viewCounts) {
        this.viewCounts = viewCounts;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.newsId);
        dest.writeString(this.newTitle);
        dest.writeString(this.newsContent);
        dest.writeString(this.fromName);
        dest.writeLong(this.viewCounts);
        dest.writeString(this.newsUrl);
        dest.writeInt(this.newsType);
    }

    public NewsBean() {
    }

    protected NewsBean(Parcel in) {
        this.newsId = in.readInt();
        this.newTitle = in.readString();
        this.newsContent = in.readString();
        this.fromName = in.readString();
        this.viewCounts = in.readLong();
        this.newsUrl = in.readString();
        this.newsType = in.readInt();
    }

    public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}
