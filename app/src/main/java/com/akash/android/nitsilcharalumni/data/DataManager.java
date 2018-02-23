package com.akash.android.nitsilcharalumni.data;

import android.content.Context;

import com.akash.android.nitsilcharalumni.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private Context mContext;
    private SharedPrefsHelper mSharedPrefsHelper;

    @Inject
    public DataManager(@ApplicationContext Context mContext, SharedPrefsHelper mSharedPrefsHelper) {
        this.mContext = mContext;
        this.mSharedPrefsHelper = mSharedPrefsHelper;
    }

    public void saveCurrentLocation(String location){
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_LOCATION, location);
    }

    public String getCurrentLocation(){
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_LOCATION, null);
    }

    public void saveTypeOfUser(String typeOfUser){
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_USERTYPE, typeOfUser);
    }

    public String getTypeOfUser(){
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_USERTYPE, null);
    }

    public void saveUserName(String name){
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_USERNAME, name);
    }

    public String getUserName(){
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_USERNAME, null);
    }
}
