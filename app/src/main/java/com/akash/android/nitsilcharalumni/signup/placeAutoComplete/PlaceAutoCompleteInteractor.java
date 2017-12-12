package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by akash on 19/11/17.
 */

public class PlaceAutoCompleteInteractor {

    public static final String TAG= PlaceAutoCompleteInteractor.class.getSimpleName();

    private FirebaseAuth mAuth;

    private PlaceAutoCompletePresenter mPlaceAutoCompletePresenter;

    public PlaceAutoCompleteInteractor(PlaceAutoCompletePresenter mPlaceAutoCompletePresenter) {
        this.mPlaceAutoCompletePresenter = mPlaceAutoCompletePresenter;
        mAuth= FirebaseAuth.getInstance();
    }

    public void signUpWithEmailAndPassword(final Activity activity, String email, char[] password) {
        mAuth.createUserWithEmailAndPassword(email, String.valueOf(password))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mPlaceAutoCompletePresenter.setFirebaseUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mPlaceAutoCompletePresenter.loadErrorMessage();
                        }
                    }
                });
    }
}
