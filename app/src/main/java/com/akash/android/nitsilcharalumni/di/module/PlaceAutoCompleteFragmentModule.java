package com.akash.android.nitsilcharalumni.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.akash.android.nitsilcharalumni.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class PlaceAutoCompleteFragmentModule {

    private Fragment mFragment;

    public PlaceAutoCompleteFragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    Fragment provideFragment(){
        return  mFragment;
    }

}
