package com.akash.android.nitsilcharalumni.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.widget.EditText;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG= LoginPresenter.class.getSimpleName();

    private LoginContract.View mLoginView;

    private LoginInteractor mLoginInteractor;

    public LoginPresenter(LoginContract.View mLoginView) {
        this.mLoginView = mLoginView;

        mLoginView.setPresenter(this);
        mLoginInteractor= new LoginInteractor(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean validateLoginForm(Activity activity, Editable email, EditText editTextEmail, Editable password, EditText editTextPassword) {
        if(ActivityUtils.validateEmail(email, editTextEmail) && ActivityUtils
                .validatePassword(password, editTextPassword)){
            mLoginInteractor.signInWithEmailAndPassword(activity, email.toString(), password.toString().toCharArray());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void loadErrorMessage() {
        mLoginView.showErrorMessage();
    }

    @Override
    public void loadMainActivity() {
        mLoginView.showMainActivity();
    }

    public void signInUsingGoogle(Activity context) {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mLoginInteractor.signInWithGoogleCreds(GoogleSignIn.getClient(context, gso), context);
    }

    @Override
    public void loadGoogleSignInErrorMessage() {
        mLoginView.showGoogleSignInErrorMessage();
    }

    @Override
    public void signInUsingFacebook(LoginButton fbLoginButton, Activity context) {
        mLoginInteractor.signInWithFacebookCredentials(fbLoginButton,context);
    }

    @Override
    public void loadFacebookSignInErrorMessage() {
        mLoginView.showFacebookSignInErrorMessage();
    }

    @Override
    public void onActivityResultCallbackReceived(int requestCode, int resultCode, Intent data, Activity context) {
        mLoginInteractor.onActivityResult(requestCode, resultCode, data, context);
    }
}
