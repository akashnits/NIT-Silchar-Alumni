package com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.akash.android.nitsilcharalumni.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class AlumniOrStudentSignUpInteractor {

    public static final String TAG= AlumniOrStudentSignUpInteractor.class.getSimpleName();
    private final FirebaseFirestore instance;

    private AlumniOrStudentSignUpPresenter mAlumniOrStudentSignUpPresenter;

    private FirebaseAuth mAuth;

    private FirebaseFirestore mFirestoreDb;


    public AlumniOrStudentSignUpInteractor(AlumniOrStudentSignUpPresenter mAlumniOrStudentSignUpPresenter) {
        this.mAlumniOrStudentSignUpPresenter = mAlumniOrStudentSignUpPresenter;

        mAuth= FirebaseAuth.getInstance();
        instance = FirebaseFirestore.getInstance();
        mFirestoreDb= instance;
    }

    public void signUpWithEmailAndPassword(final Activity activity, final User user, char[] password) {
        mAuth.createUserWithEmailAndPassword(user.getmEmail(), String.valueOf(password))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser fUser = mAuth.getCurrentUser();
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
                                            mAlumniOrStudentSignUpPresenter.saveLoggedInUserName
                                                    (user.getmName());
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
                                    mAlumniOrStudentSignUpPresenter.setFirebaseUser(fUser);
                                }
                            }.execute();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mAlumniOrStudentSignUpPresenter.loadErrorMessage();
                        }
                    }
                });
    }

    public void writeLoggedInUser(final User user){
        mFirestoreDb.collection("users")
                .document(user.getmEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        mAlumniOrStudentSignUpPresenter.saveLoggedInUserName(user.getmName());
                        mAlumniOrStudentSignUpPresenter.setFirebaseUser(mAuth.getCurrentUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
