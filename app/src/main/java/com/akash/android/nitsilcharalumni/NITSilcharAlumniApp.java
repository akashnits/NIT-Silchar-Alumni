package com.akash.android.nitsilcharalumni;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.akash.android.nitsilcharalumni.di.component.AppComponent;
import com.akash.android.nitsilcharalumni.di.component.DaggerAppComponent;
import com.akash.android.nitsilcharalumni.di.module.AppModule;
import com.squareup.leakcanary.LeakCanary;


public class NITSilcharAlumniApp extends MultiDexApplication {

    protected AppComponent appComponent;

    public static NITSilcharAlumniApp get(Context context) {
        return (NITSilcharAlumniApp) context.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        appComponent= DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}


