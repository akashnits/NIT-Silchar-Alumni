package com.akash.android.nitsilcharalumni.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment.AlumniOrStudentSignUpFragment;
import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteFragment;
import com.akash.android.nitsilcharalumni.signup.social.SocialLoginFragment;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment= (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.loginFragment);
        if(loginFragment == null)
            loginFragment = LoginFragment.newInstance();

        if(savedInstanceState == null) {
            ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                    loginFragment,
                    R.id.loginContainer,
                    false, getString(R.string.loginFragment),R.anim.enter_from_right,
                    R.anim.exit_to_left );
        }
        mLoginPresenter= new LoginPresenter(loginFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LoginFragment loginFragment= (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.loginFragment);

        loginFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void showSocialLoginFragment(String email){
        SocialLoginFragment socialLoginFragment= SocialLoginFragment.newInstance(email);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                socialLoginFragment,
                R.id.loginContainer,
                true, getString(R.string.socialLoginFragment), R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void showPlaceAutoCompleteFragment(User user){
        PlaceAutoCompleteFragment placeAutoCompleteFragment= PlaceAutoCompleteFragment.newInstance(user);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                placeAutoCompleteFragment,
                R.id.loginContainer,
                true, getString(R.string.placeAutoCompleteFragment), R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void showAlumniOrStudentSignUpFragment(User user, boolean isAlumnus){
        AlumniOrStudentSignUpFragment alumniOrStudentSignUpFragment= AlumniOrStudentSignUpFragment
                .newInstance(user, isAlumnus);
        ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                alumniOrStudentSignUpFragment,
                R.id.loginContainer,
                true, getString(R.string.alumniOrStudentSignupFrag), R.anim.enter_from_right,
                R.anim.exit_to_left );
    }
}
