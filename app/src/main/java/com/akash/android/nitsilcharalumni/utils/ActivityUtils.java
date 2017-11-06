package com.akash.android.nitsilcharalumni.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import static android.support.v4.util.Preconditions.checkNotNull;


public class ActivityUtils {

    public static void replaceFragmentOnActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment,
                                                 int frameId, boolean addToBackstack){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        if(addToBackstack) {
            fragmentManager.beginTransaction()
                    .replace(frameId, fragment)
                    .addToBackStack(null)
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(frameId, fragment)
                    .commit();
        }
    }
}
