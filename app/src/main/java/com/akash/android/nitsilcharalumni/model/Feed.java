package com.akash.android.nitsilcharalumni.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Feed implements Parcelable {
    private String mAuthorImageUrl;
    private String mAuthorName;
    private HashMap<String, Object> mTimestampFeedCreated;
    private String mFeedImageUrl;
    private String mFeedDescription;
    private List<String> mFeedSearchKeywordsList;
    private String mAuthorEmail;

    public Feed() {
    }

    public Feed(String mAuthorImageUrl, String mAuthorName, HashMap<String, Object> mTimestampFeedCreated, String mFeedImageUrl, String mFeedDescription, List<String> mFeedSearchKeywordsList, String mAuthorEmail) {
        this.mAuthorImageUrl = mAuthorImageUrl;
        this.mAuthorName = mAuthorName;
        this.mTimestampFeedCreated = mTimestampFeedCreated;
        this.mFeedImageUrl = mFeedImageUrl;
        this.mFeedDescription = mFeedDescription;
        this.mFeedSearchKeywordsList = mFeedSearchKeywordsList;
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

    public HashMap<String, Object> getmTimestampFeedCreated() {
        if (mTimestampFeedCreated != null) {
            return mTimestampFeedCreated;
        }
        //Otherwise make a new object set to ServerValue.TIMESTAMP
        HashMap<String, Object> timeCreatedFeed = new HashMap<String, Object>();
        timeCreatedFeed.put("timestamp", FieldValue.serverTimestamp());
        return timeCreatedFeed;
    }

    public void setmTimestampFeedCreated(HashMap<String, Object> mTimestampFeedCreated) {
        this.mTimestampFeedCreated = mTimestampFeedCreated;
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

    public List<String> getmFeedSearchKeywordsList() {
        return mFeedSearchKeywordsList;
    }

    public void setmFeedSearchKeywordsList(List<String> mFeedSearchKeywordsList) {
        this.mFeedSearchKeywordsList = mFeedSearchKeywordsList;
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
        mTimestampFeedCreated = (HashMap) in.readValue(HashMap.class.getClassLoader());
        mFeedImageUrl = in.readString();
        mFeedDescription = in.readString();
        if (in.readByte() == 0x01) {
            mFeedSearchKeywordsList = new ArrayList<>();
            in.readList(mFeedSearchKeywordsList, String.class.getClassLoader());
        } else {
            mFeedSearchKeywordsList = null;
        }
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
        dest.writeValue(mTimestampFeedCreated);
        dest.writeString(mFeedImageUrl);
        dest.writeString(mFeedDescription);
        if (mFeedSearchKeywordsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mFeedSearchKeywordsList);
        }
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