package com.akash.android.nitsilcharalumni;

import android.app.Application;
import android.content.Context;

import com.akash.android.nitsilcharalumni.di.component.AppComponent;
import com.akash.android.nitsilcharalumni.di.component.DaggerAppComponent;
import com.akash.android.nitsilcharalumni.di.module.AppModule;


public class NITSilcharAlumniApp extends Application {

    protected AppComponent appComponent;

    public static NITSilcharAlumniApp get(Context context) {
        return (NITSilcharAlumniApp) context.getApplicationContext();
    }

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
