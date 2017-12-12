package com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by akash on 19/11/17.
 */

public class AlumniOrStudentSignUpInteractor {

    public static final String TAG= AlumniOrStudentSignUpInteractor.class.getSimpleName();

    private AlumniOrStudentSignUpPresenter mAlumniOrStudentSignUpPresenter;

    private FirebaseAuth mAuth;


    public AlumniOrStudentSignUpInteractor(AlumniOrStudentSignUpPresenter mAlumniOrStudentSignUpPresenter) {
        this.mAlumniOrStudentSignUpPresenter = mAlumniOrStudentSignUpPresenter;

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
                            mAlumniOrStudentSignUpPresenter.setFirebaseUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mAlumniOrStudentSignUpPresenter.loadErrorMessage();
                        }
                    }
                });
    }
}
