package com.akash.android.nitsilcharalumni.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Job implements Parcelable {

    private String mAuthorName;
    private String mAuthorImageUrl;
    @ServerTimestamp
    private Date mTimestamp;
    private String mJobTitle;
    private String mJobLocation;
    private String mJobOrganisation;
    private String mJobDescription;
    private String mJobImageUrl;
    private Map<String, Boolean> mJobSearchKeywordsMap;

    public Job() {
    }

    public Job(String mAuthorName, String mAuthorImageUrl, Date mTimestamp, String mJobTitle,
               String mJobLocation, String mJobOrganisation, String mJobDescription, String mJobImageUrl,
               Map<String, Boolean> mJobSearchKeywordsMap) {
        this.mAuthorName = mAuthorName;
        this.mAuthorImageUrl = mAuthorImageUrl;
        this.mTimestamp = mTimestamp;
        this.mJobTitle = mJobTitle;
        this.mJobLocation = mJobLocation;
        this.mJobOrganisation = mJobOrganisation;
        this.mJobDescription = mJobDescription;
        this.mJobImageUrl = mJobImageUrl;
        this.mJobSearchKeywordsMap = mJobSearchKeywordsMap;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getmAuthorImageUrl() {
        return mAuthorImageUrl;
    }

    public void setmAuthorImageUrl(String mAuthorImageUrl) {
        this.mAuthorImageUrl = mAuthorImageUrl;
    }

    public Date getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getmJobTitle() {
        return mJobTitle;
    }

    public void setmJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getmJobLocation() {
        return mJobLocation;
    }

    public void setmJobLocation(String mJobLocation) {
        this.mJobLocation = mJobLocation;
    }

    public String getmJobOrganisation() {
        return mJobOrganisation;
    }

    public void setmJobOrganisation(String mJobOrganisation) {
        this.mJobOrganisation = mJobOrganisation;
    }

    public String getmJobDescription() {
        return mJobDescription;
    }

    public void setmJobDescription(String mJobDescription) {
        this.mJobDescription = mJobDescription;
    }

    public String getmJobImageUrl() {
        return mJobImageUrl;
    }

    public void setmJobImageUrl(String mJobImageUrl) {
        this.mJobImageUrl = mJobImageUrl;
    }

    public Map<String, Boolean> getmJobSearchKeywordsMap() {
        return mJobSearchKeywordsMap;
    }

    public void setmJobSearchKeywordsMap(Map<String, Boolean> mJobSearchKeywordsMap) {
        this.mJobSearchKeywordsMap = mJobSearchKeywordsMap;
    }

    protected Job(Parcel in) {
        mAuthorName = in.readString();
        mAuthorImageUrl = in.readString();
        long tmpMTimestamp = in.readLong();
        mTimestamp = tmpMTimestamp != -1 ? new Date(tmpMTimestamp) : null;
        mJobTitle = in.readString();
        mJobLocation = in.readString();
        mJobOrganisation = in.readString();
        mJobDescription = in.readString();
        mJobImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthorName);
        dest.writeString(mAuthorImageUrl);
        dest.writeLong(mTimestamp != null ? mTimestamp.getTime() : -1L);
        dest.writeString(mJobTitle);
        dest.writeString(mJobLocation);
        dest.writeString(mJobOrganisation);
        dest.writeString(mJobDescription);
        dest.writeString(mJobImageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}