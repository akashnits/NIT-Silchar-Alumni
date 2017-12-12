package com.akash.android.nitsilcharalumni.di.component;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.PlaceAutoCompleteFragmentModule;
import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = AppComponent.class, modules = PlaceAutoCompleteFragmentModule.class)
public interface PlaceAutoCompleteFragmentComponent {

    void inject(PlaceAutoCompleteFragment placeAutoCompleteFragment);

}
