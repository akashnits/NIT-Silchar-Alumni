package com.akash.android.nitsilcharalumni.utils.imageUtils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoggedInUser {

    private static String mProfileImageUrl;

    public LoggedInUser() {
    }


    public static String getLoggedInUserProfileImageUrl() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseFirestore.getInstance().collection(Constants.USER_COLLECTION)
                .whereEqualTo("mEmail", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            User currentUser = null;
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                currentUser = documentSnapshot.toObject(User.class);
                            if (currentUser != null)
                                mProfileImageUrl = currentUser.getmProfileImageUrl();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
        return mProfileImageUrl;
    }
}
