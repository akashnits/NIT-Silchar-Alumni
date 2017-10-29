package com.akash.android.nitsilcharalumni.dagger.module;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.akash.android.nitsilcharalumni.dagger.ApplicationContext;
import com.akash.android.nitsilcharalumni.ui.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class AppModule {

    private Application mApp;

    public AppModule(Application app) {
        this.mApp= app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApp;
    }

    @Provides
    Application provideApplication() {
        return mApp;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApp.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }


}
