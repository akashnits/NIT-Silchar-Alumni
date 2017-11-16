package com.akash.android.nitsilcharalumni.signup;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteFragment;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;

public class SignUpActivity extends AppCompatActivity {

    private SignUpPresenter mSignUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpFragment signUpFragment= (SignUpFragment) getSupportFragmentManager()
                .findFragmentById(R.id.signUpFragment);
        if(signUpFragment == null)
            signUpFragment = SignUpFragment.newInstance();

        if(savedInstanceState == null) {
            ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                    signUpFragment,
                    R.id.signUpContainer,
                    false);
        }
        mSignUpPresenter= new SignUpPresenter(signUpFragment);
    }

    public void showPlaceAutoCompleteFragment(){
        PlaceAutoCompleteFragment placeAutoCompleteFragment= PlaceAutoCompleteFragment.newInstance();
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                placeAutoCompleteFragment,
                R.id.signUpContainer,
                true);
    }

    public void showAlumniOrStudentSignUpFragment(boolean isAlumnus){
        AlumniOrStudentSignUpFragment alumniOrStudentSignUpFragment= AlumniOrStudentSignUpFragment.newInstance(isAlumnus);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                alumniOrStudentSignUpFragment,
                R.id.signUpContainer,
                true);
    }

}
