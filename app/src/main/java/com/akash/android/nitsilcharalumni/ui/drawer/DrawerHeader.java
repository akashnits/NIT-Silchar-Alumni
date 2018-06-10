package com.akash.android.nitsilcharalumni.ui.drawer;

import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @View(R.id.myProfileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @Resolve
    private void onResolved() {
        nameTxt.setText(mAuth.getCurrentUser().getDisplayName());
    }
}