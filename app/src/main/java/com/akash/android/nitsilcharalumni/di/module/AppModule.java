package com.akash.android.nitsilcharalumni.di.module;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.ApplicationContext;

import javax.inject.Inject;

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
