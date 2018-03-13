package com.akash.android.nitsilcharalumni;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

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

       /* //For Xiomi devices, ask user to enable app to run in background
        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            startActivity(intent);
        }*/
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
