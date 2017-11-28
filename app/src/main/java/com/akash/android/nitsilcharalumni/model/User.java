package com.akash.android.nitsilcharalumni.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;



public class User implements Parcelable {

    private String mName;
    private String mEmail;
    private String mGender;
    private String mTypeOfUser;
    private String mLocation;
    private int mClassOf;
    private String mOrganisation;


    public User(String mName, String mEmail, String mGender, String mTypeOfUser) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mGender = mGender;
        this.mTypeOfUser = mTypeOfUser;

    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmTypeOfUser() {
        return mTypeOfUser;
    }

    public String getmLocation() {
        return mLocation;
    }

    public int getmClassOf() {
        return mClassOf;
    }

    public String getmOrganisation() {
        return mOrganisation;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public void setmTypeOfUser(String mTypeOfUser) {
        this.mTypeOfUser = mTypeOfUser;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmClassOf(int mClassOf) {
        this.mClassOf = mClassOf;
    }

    public void setmOrganisation(String mOrganisation) {
        this.mOrganisation = mOrganisation;
    }

    public HashMap<String, Object> toMap()
    {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", mName);
        userMap.put("email", mEmail);
        userMap.put("gender", mGender);
        userMap.put("typeOfUser", mTypeOfUser);
        userMap.put("location", mLocation);
        userMap.put("classOf", mClassOf);
        userMap.put("organisation", mOrganisation);
        return userMap;
    }

    protected User(Parcel in) {
        mName = in.readString();
        mEmail = in.readString();
        mGender = in.readString();
        mTypeOfUser = in.readString();
        mLocation = in.readString();
        mClassOf = in.readInt();
        mOrganisation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mGender);
        dest.writeString(mTypeOfUser);
        dest.writeString(mLocation);
        dest.writeInt(mClassOf);
        dest.writeString(mOrganisation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}