package com.akash.android.nitsilcharalumni.ui.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.fragments.AlumniOrStudentSignUpFragment;
import com.akash.android.nitsilcharalumni.ui.fragments.PlaceAutoCompleteFragment;
import com.akash.android.nitsilcharalumni.ui.fragments.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(savedInstanceState == null) {
            SignUpFragment signUpFragment = SignUpFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.signUpContainer, signUpFragment)
                    .commit();
        }
    }

    public void showPlaceAutoCompleteFragment(){
        PlaceAutoCompleteFragment placeAutoCompleteFragment= PlaceAutoCompleteFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.signUpContainer, placeAutoCompleteFragment)
                .addToBackStack(null)
                .commit();
    }

    public void showAlumniOrStudentSignUpFragment(boolean isAlumnus){
        AlumniOrStudentSignUpFragment alumniOrStudentSignUpFragment= AlumniOrStudentSignUpFragment.newInstance(isAlumnus);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.signUpContainer, alumniOrStudentSignUpFragment)
                .addToBackStack(null)
                .commit();
    }

}
