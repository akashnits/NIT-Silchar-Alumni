package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;

import com.akash.android.nitsilcharalumni.model.User;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 20125350 on 11/16/2017.
 */

public class PlaceAutoCompletePresenter implements PlaceAutoCompleteContract.Presenter {

    private PlaceAutoCompleteContract.View mPlaceAutoCompleteView;

    private PlaceAutoCompleteInteractor mPlaceAutoCompleteInteractor;

    private FirebaseUser mFirebaseUser;

    public PlaceAutoCompletePresenter(PlaceAutoCompleteContract.View mPlaceAutoCompleteView) {
        this.mPlaceAutoCompleteView = mPlaceAutoCompleteView;

        mPlaceAutoCompleteView.setPresenter(this);
        mPlaceAutoCompleteInteractor= new PlaceAutoCompleteInteractor(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadAlumniOrStudentSignUpFragment(User user, char[] password) {
        mPlaceAutoCompleteView.commitAlumniOrStudentSignUpFragment(user, password);
    }

    @Override
    public boolean validateLocationEntered(Editable location) {
        return !TextUtils.isEmpty(location);
    }

    @Override
    public void createAccountWithEmailAndPassword(Activity activity, String email, char[] password) {
        mPlaceAutoCompleteInteractor.signUpWithEmailAndPassword(activity, email, password);
    }

    @Override
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        mFirebaseUser= firebaseUser;
        mPlaceAutoCompleteView.showMainActivity();
    }

    @Override
    public void loadErrorMessage() {
        mPlaceAutoCompleteView.showErrorMessage();
    }
}
