package com.akash.android.nitsilcharalumni.di.component;


import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.LoginFragmentModule;
import com.akash.android.nitsilcharalumni.login.LoginFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = LoginFragmentModule.class)
public interface LoginFragmentComponent {

    void inject(LoginFragment loginFragment);
}
