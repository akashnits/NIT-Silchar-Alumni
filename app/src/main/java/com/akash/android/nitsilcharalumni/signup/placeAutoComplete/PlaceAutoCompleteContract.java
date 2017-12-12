package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;
import com.akash.android.nitsilcharalumni.model.User;
import com.google.firebase.auth.FirebaseUser;


public class PlaceAutoCompleteContract {
    public interface View extends BaseView<Presenter> {

        void commitAlumniOrStudentSignUpFragment(User user, char[] password);

        void showErrorMessage();

        void showMainActivity();
    }

    public interface Presenter extends BasePresenter {

        void loadAlumniOrStudentSignUpFragment(User user, char[] password);

        boolean validateLocationEntered(Editable location);

        void createAccountWithEmailAndPassword(Activity activity, String email, char[] password);

        void setFirebaseUser(FirebaseUser firebaseUser);

        void loadErrorMessage();
    }
}