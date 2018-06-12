package com.akash.android.nitsilcharalumni.ui.drawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.DaggerDrawerHeaderComponent;
import com.akash.android.nitsilcharalumni.di.component.DrawerHeaderComponent;
import com.akash.android.nitsilcharalumni.di.module.DrawerHeaderModule;
import com.google.firebase.auth.FirebaseAuth;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import javax.inject.Inject;

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    private FirebaseAuth mAuth;
    private DrawerHeaderComponent drawerHeaderComponent;
    private Context mContext;

    @Inject
    DataManager mDataManager;

    public DrawerHeader(Context context) {
        mContext= context;
        getDrawerHeaderComponent().inject(this);
        mAuth= FirebaseAuth.getInstance();
    }


    @View(R.id.myProfileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @Resolve
    private void onResolved() {
        nameTxt.setText(mDataManager.getUserName());
    }

    public DrawerHeaderComponent getDrawerHeaderComponent() {
        if (drawerHeaderComponent == null) {
            drawerHeaderComponent = DaggerDrawerHeaderComponent.builder()
                    .drawerHeaderModule(new DrawerHeaderModule(this))
                    .appComponent(NITSilcharAlumniApp.get(mContext).getAppComponent())
                    .build();
        }
        return drawerHeaderComponent;
    }
}