package com.akash.android.nitsilcharalumni.di.component;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.SocialLoginFragmentModule;
import com.akash.android.nitsilcharalumni.signup.social.SocialLoginFragment;

import dagger.Component;

/**
 * Created by akash on 06/05/18.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = SocialLoginFragmentModule.class)
public interface SocialLoginFragmentComponent {

    void inject(SocialLoginFragment socialLoginFragment);
}
