package com.akash.android.nitsilcharalumni.ui.alumni;

import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.akash.android.nitsilcharalumni.R;

import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_LOCATION;

public class AlumniLocationPrefFragment extends PreferenceFragmentCompat {

    private String[] mAlumniLocationArray;

    public static AlumniLocationPrefFragment newInstance() {
        AlumniLocationPrefFragment fragment = new AlumniLocationPrefFragment();
        return fragment;
    }

    public AlumniLocationPrefFragment() {
    }

    @Override
    public void onCreatePreferences (Bundle savedInstanceState, String rootKey){
        addPreferencesFromResource(R.xml.alumni_location_prefs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlumniLocationArray = getResources().getStringArray(R.array.location);

        for (String key : mAlumniLocationArray) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference)
                    findPreference(String.format("%s_%s", ALUMNI_LOCATION, key));
            checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedPref=  preference.getKey();
                    for(String location: mAlumniLocationArray){
                        String key= String.format("%s_%s", ALUMNI_LOCATION, location);
                        CheckBoxPreference checkBoxPreference= (CheckBoxPreference)
                                findPreference(key);
                        if(!selectedPref.equals(key)){
                            checkBoxPreference.setChecked(false);
                        }else {
                            if(checkBoxPreference.isChecked())
                                checkBoxPreference.setChecked(false);
                            else
                                checkBoxPreference.setChecked(true);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
