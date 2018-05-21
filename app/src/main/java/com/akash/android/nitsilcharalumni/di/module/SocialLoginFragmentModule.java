package com.akash.android.nitsilcharalumni.di.module;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;


@Module
public class SocialLoginFragmentModule {

    private Fragment mFragment;

    public SocialLoginFragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    Fragment provideFragment(){
        return  mFragment;
    }
}
