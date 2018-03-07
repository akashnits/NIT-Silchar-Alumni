package com.akash.android.nitsilcharalumni.ui.alumni;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.akash.android.nitsilcharalumni.R;

public class AlumniLocationPrefFragment extends PreferenceFragmentCompat {

    public static AlumniLocationPrefFragment newInstance() {
        AlumniLocationPrefFragment fragment = new AlumniLocationPrefFragment();
        return fragment;
    }

    public AlumniLocationPrefFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.alumni_location_prefs);
    }
}
