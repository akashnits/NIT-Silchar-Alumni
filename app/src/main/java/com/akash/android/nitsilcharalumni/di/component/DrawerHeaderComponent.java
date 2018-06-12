package com.akash.android.nitsilcharalumni.di.component;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.DrawerHeaderModule;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;

import dagger.Component;

/**
 * Created by akash on 13/06/18.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = DrawerHeaderModule.class)
public interface DrawerHeaderComponent {
    void inject(DrawerHeader drawerHeader);
}

