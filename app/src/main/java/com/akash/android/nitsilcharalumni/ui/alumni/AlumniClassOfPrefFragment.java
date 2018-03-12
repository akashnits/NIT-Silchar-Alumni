package com.akash.android.nitsilcharalumni.ui.alumni;

import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.akash.android.nitsilcharalumni.R;

import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_CLASS_OF;


public class AlumniClassOfPrefFragment extends PreferenceFragmentCompat {

    private String[] mAlumniClassOfArray;


    public AlumniClassOfPrefFragment() {
    }

    public static AlumniClassOfPrefFragment newInstance() {
        AlumniClassOfPrefFragment fragment = new AlumniClassOfPrefFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAlumniClassOfArray = getResources().getStringArray(R.array.classOfFilter);

        for (String key : mAlumniClassOfArray) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference)
                    findPreference(String.format("%s_%s", ALUMNI_CLASS_OF, key));
            checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedPref = preference.getKey();
                    for (String classOfValue : mAlumniClassOfArray) {
                        String key = String.format("%s_%s", ALUMNI_CLASS_OF, classOfValue);
                        CheckBoxPreference checkBoxPreference = (CheckBoxPreference)
                                findPreference(key);
                        if (!selectedPref.equals(key)) {
                            checkBoxPreference.setChecked(false);
                        } else {
                            if (checkBoxPreference.isChecked())
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

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.classof_prefs);
    }
}
