package com.akash.android.nitsilcharalumni.di.module;

import android.support.v4.app.Fragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class SignUpFragmentModule {

    private Fragment mFragment;

    public SignUpFragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    Fragment provideFragment(){
        return  mFragment;
    }
}
