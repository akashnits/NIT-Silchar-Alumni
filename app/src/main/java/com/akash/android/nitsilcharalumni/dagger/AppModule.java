package com.akash.android.nitsilcharalumni.dagger;


import android.content.Context;

import com.akash.android.nitsilcharalumni.ui.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class AppModule {

    private Context app;

    public AppModule(Context app) {
        this.app= app;
    }

    @Provides @Singleton Context providesAppContext(){
        return app;
    }

}
