package com.akash.android.nitsilcharalumni.services;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.akash.android.nitsilcharalumni.R;

import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_CLASS_OF;
import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_LOCATION;

public class OnClearFromRecentService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        String[] alumniLocationArray= getResources().getStringArray(R.array.location);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Delete the shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : alumniLocationArray) {
            editor.remove(String.format("%s_%s", ALUMNI_LOCATION, key));
            editor.apply();
        }
        String[] alumniClassOfArray= getResources().getStringArray(R.array.classOfFilter);
        for (String key : alumniClassOfArray) {
            editor.remove(String.format("%s_%s", ALUMNI_CLASS_OF, key));
            editor.apply();
        }
        stopSelf();
    }
}
