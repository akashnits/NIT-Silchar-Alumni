package com.akash.android.nitsilcharalumni.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.SignUpContract;
import com.facebook.login.widget.LoginButton;


public class LoginContract {
    public interface View extends BaseView<LoginContract.Presenter> {

        void showErrorMessage();

        void showMainActivity();

        void showGoogleSignInErrorMessage();

        void showFacebookSignInErrorMessage();

        void saveLoggedInUsername(String name);
    }

    public interface Presenter extends BasePresenter {
        boolean validateLoginForm(Activity activity, Editable email, EditText editTextEmail,
                                  Editable password, EditText editTextPassword);

        void loadErrorMessage();

        void loadMainActivity();

        void onActivityResultCallbackReceived(int requestCode, int resultCode, Intent data, Activity context);

        void signInUsingGoogle(Activity context);

        void loadGoogleSignInErrorMessage();

        void signInUsingFacebook(LoginButton fbLoginButton, Activity context);

        void loadFacebookSignInErrorMessage();

        void saveLoggedInUserData(User user);

    }
}
