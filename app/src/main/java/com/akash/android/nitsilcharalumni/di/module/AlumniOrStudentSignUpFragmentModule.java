package com.akash.android.nitsilcharalumni.di.module;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class AlumniOrStudentSignUpFragmentModule {
    private Fragment mFragment;

    public AlumniOrStudentSignUpFragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    Fragment provideFragment(){
        return  mFragment;
    }
}
