package com.akash.android.nitsilcharalumni.dagger.component;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton @Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(NITSilcharAlumniApp nitSilcharAlumniApp);
}
