package com.akash.android.nitsilcharalumni.di.component;

import android.content.SharedPreferences;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.AppModule;
import com.akash.android.nitsilcharalumni.di.module.PlaceAutoCompleteFragmentModule;
import com.akash.android.nitsilcharalumni.ui.fragments.PlaceAutoCompleteFragment;

import javax.inject.Singleton;

import dagger.Component;


@PerFragment
@Component(dependencies = AppComponent.class, modules = PlaceAutoCompleteFragmentModule.class)
public interface PlaceAutoCompleteFragmentComponent {

    void inject(PlaceAutoCompleteFragment placeAutoCompleteFragment);

}
