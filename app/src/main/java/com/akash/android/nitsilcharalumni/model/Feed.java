package com.akash.android.nitsilcharalumni.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keep
public class Feed implements Parcelable {
    private String mAuthorImageUrl;
    private String mAuthorName;
    @ServerTimestamp
    private Date mTimestamp;
    private String mFeedImageUrl;
    private String mFeedDescription;
    private Map<String, Boolean> mFeedSearchKeywordsMap;
    private String mAuthorEmail;

    public Feed() {
    }

    public Feed(String mAuthorImageUrl, String mAuthorName, Date time, String mFeedImageUrl,
                String mFeedDescription, Map<String, Boolean> mFeedSearchKeywordsMap, String mAuthorEmail) {
        this.mAuthorImageUrl = mAuthorImageUrl;
        this.mAuthorName = mAuthorName;
        this.mTimestamp = time;
        this.mFeedImageUrl = mFeedImageUrl;
        this.mFeedDescription = mFeedDescription;
        this.mFeedSearchKeywordsMap = mFeedSearchKeywordsMap;
        this.mAuthorEmail = mAuthorEmail;
    }

    public String getmAuthorImageUrl() {
        return mAuthorImageUrl;
    }

    public void setmAuthorImageUrl(String mAuthorImageUrl) {
        this.mAuthorImageUrl = mAuthorImageUrl;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public Date getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getmFeedImageUrl() {
        return mFeedImageUrl;
    }

    public void setmFeedImageUrl(String mFeedImageUrl) {
        this.mFeedImageUrl = mFeedImageUrl;
    }

    public String getmFeedDescription() {
        return mFeedDescription;
    }

    public void setmFeedDescription(String mFeedDescription) {
        this.mFeedDescription = mFeedDescription;
    }

    public Map<String, Boolean> getmFeedSearchKeywordsMap() {
        return mFeedSearchKeywordsMap;
    }

    public void setmFeedSearchKeywordsMap(Map<String, Boolean> mFeedSearchKeywordsMap) {
        this.mFeedSearchKeywordsMap = mFeedSearchKeywordsMap;
    }

    public String getmAuthorEmail() {
        return mAuthorEmail;
    }

    public void setmAuthorEmail(String mAuthorEmail) {
        this.mAuthorEmail = mAuthorEmail;
    }

    protected Feed(Parcel in) {
        mAuthorImageUrl = in.readString();
        mAuthorName = in.readString();
        long tmpMTimestamp = in.readLong();
        mTimestamp = tmpMTimestamp != -1 ? new Date(tmpMTimestamp) : null;
        mFeedImageUrl = in.readString();
        mFeedDescription = in.readString();
        mAuthorEmail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthorImageUrl);
        dest.writeString(mAuthorName);
        dest.writeLong(mTimestamp != null ? mTimestamp.getTime() : -1L);
        dest.writeString(mFeedImageUrl);
        dest.writeString(mFeedDescription);
        dest.writeString(mAuthorEmail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}