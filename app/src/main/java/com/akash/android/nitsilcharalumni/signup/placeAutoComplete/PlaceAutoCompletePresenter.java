package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import android.text.Editable;
import android.text.TextUtils;

import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

/**
 * Created by 20125350 on 11/16/2017.
 */

public class PlaceAutoCompletePresenter implements PlaceAutoCompleteContract.Presenter {

    @Inject
    DataManager mDataManager;

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
    public void createAccountWithEmailAndPassword(SignUpActivity activity, User user, char[] password) {
        mPlaceAutoCompleteInteractor.signUpWithEmailAndPassword(activity, user, password);
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

    @Override
    public void saveLoggedInUserName(String name) {
        mPlaceAutoCompleteView.saveLoggedInUsername(name);
    }
}
