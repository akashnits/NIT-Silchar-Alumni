package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;



public class PlaceAutoCompleteContract {
    public interface View extends BaseView<Presenter> {

        void commitAlumniOrStudentSignUpFragment();

    }

    public interface Presenter extends BasePresenter {

        void loadAlumniOrStudentSignUpFragment();
    }
}
