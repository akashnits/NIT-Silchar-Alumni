package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by akash on 19/11/17.
 */

public class PlaceAutoCompleteInteractor {

    public static final String TAG = PlaceAutoCompleteInteractor.class.getSimpleName();

    private FirebaseAuth mAuth;

    private PlaceAutoCompletePresenter mPlaceAutoCompletePresenter;

    private FirebaseFirestore mFirestoreDb;


    public PlaceAutoCompleteInteractor(PlaceAutoCompletePresenter mPlaceAutoCompletePresenter) {
        this.mPlaceAutoCompletePresenter = mPlaceAutoCompletePresenter;
        mAuth = FirebaseAuth.getInstance();
        mFirestoreDb = FirebaseFirestore.getInstance();
    }

    public void signUpWithEmailAndPassword(final SignUpActivity activity, final User user, char[] password) {
        mAuth.createUserWithEmailAndPassword(user.getmEmail(), String.valueOf(password))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            mPlaceAutoCompletePresenter.setFirebaseUser(fUser);
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }

                                @Override
                                protected Void doInBackground(Void... params) {
                                    mFirestoreDb.collection("users")
                                            .document(user.getmEmail())
                                            .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                }
                            }.execute();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mPlaceAutoCompletePresenter.loadErrorMessage();
                        }
                    }
                });
    }
}
