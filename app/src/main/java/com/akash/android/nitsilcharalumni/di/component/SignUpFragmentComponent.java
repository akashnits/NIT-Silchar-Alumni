package com.akash.android.nitsilcharalumni.di.component;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.SignUpFragmentModule;
import com.akash.android.nitsilcharalumni.ui.fragments.SignUpFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by akash on 29/10/17.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = SignUpFragmentModule.class)
public interface SignUpFragmentComponent {

    void inject(SignUpFragment signUpFragment);
}
