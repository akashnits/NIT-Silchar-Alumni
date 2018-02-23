package com.akash.android.nitsilcharalumni.di.component;


import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.CreateJobFragmentModule;
import com.akash.android.nitsilcharalumni.ui.job.CreateJobFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = CreateJobFragmentModule.class)
public interface CreateJobFragmentComponent {

    void inject(CreateJobFragment createJobFragment);
}
