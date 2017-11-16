package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteContract;

/**
 * Created by 20125350 on 11/16/2017.
 */

public class PlaceAutoCompletePresenter implements PlaceAutoCompleteContract.Presenter {

    private PlaceAutoCompleteContract.View mPlaceAutoCompleteView;

    public PlaceAutoCompletePresenter(PlaceAutoCompleteContract.View mPlaceAutoCompleteView) {
        this.mPlaceAutoCompleteView = mPlaceAutoCompleteView;

        mPlaceAutoCompleteView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadAlumniOrStudentSignUpFragment() {
        mPlaceAutoCompleteView.commitAlumniOrStudentSignUpFragment();
    }
}
