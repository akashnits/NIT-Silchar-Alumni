package com.akash.android.nitsilcharalumni.di.module;

import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akash on 13/06/18.
 */

@Module
public class DrawerHeaderModule {
    private DrawerHeader mDrawerHeader;

    public DrawerHeaderModule(DrawerHeader drawerHeader) {
        this.mDrawerHeader = drawerHeader;
    }

    @Provides
    DrawerHeader provideDrawerHeader(){
        return  mDrawerHeader;
    }
}
