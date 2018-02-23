package com.akash.android.nitsilcharalumni.di.component;


import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.CreateFeedFragmentModule;
import com.akash.android.nitsilcharalumni.ui.feed.CreateFeedFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = CreateFeedFragmentModule.class)
public interface CreateFeedFragmentComponent {

    void inject(CreateFeedFragment createFeedFragment);

}
