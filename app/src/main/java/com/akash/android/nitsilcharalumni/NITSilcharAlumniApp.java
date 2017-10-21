package com.akash.android.nitsilcharalumni;

import android.app.Application;

import com.akash.android.nitsilcharalumni.dagger.AppComponent;
import com.akash.android.nitsilcharalumni.dagger.AppModule;
import com.akash.android.nitsilcharalumni.dagger.DaggerAppComponent;


public class NITSilcharAlumniApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent= DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
