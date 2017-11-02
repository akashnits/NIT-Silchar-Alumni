package com.akash.android.nitsilcharalumni.di.component;

import android.app.Application;
import android.content.Context;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.ApplicationContext;
import com.akash.android.nitsilcharalumni.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton @Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(NITSilcharAlumniApp nitSilcharAlumniApp);

    DataManager getDataManager();
}
