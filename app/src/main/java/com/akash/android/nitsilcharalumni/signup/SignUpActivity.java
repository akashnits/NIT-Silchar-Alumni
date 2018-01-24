package com.akash.android.nitsilcharalumni.signup;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment.AlumniOrStudentSignUpFragment;
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
                    false, getString(R.string.signupFragment),R.anim.enter_from_right,
                    R.anim.exit_to_left );
        }
        mSignUpPresenter= new SignUpPresenter(signUpFragment);
    }

    public void showPlaceAutoCompleteFragment(User user, char[] password){
        PlaceAutoCompleteFragment placeAutoCompleteFragment= PlaceAutoCompleteFragment.newInstance(user, password);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                placeAutoCompleteFragment,
                R.id.signUpContainer,
                true, getString(R.string.placeAutoCompleteFragment), R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void showAlumniOrStudentSignUpFragment(User user, char[] password, boolean isAlumnus){
        AlumniOrStudentSignUpFragment alumniOrStudentSignUpFragment= AlumniOrStudentSignUpFragment
                .newInstance(user, password, isAlumnus);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                alumniOrStudentSignUpFragment,
                R.id.signUpContainer,
                true, getString(R.string.alumniOrStudentSignupFrag), R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

}
