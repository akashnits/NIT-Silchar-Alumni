package com.akash.android.nitsilcharalumni.signup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */

public class SignUpContract {

    public interface View extends BaseView<Presenter>{

        void showSpinnerDropdownUser(ArrayAdapter<CharSequence> adapter);

        void showTextOnButton(String selected);

        void commitPlaceAutoCompleteFragment();

        void commitAlumniOrStudentSignUpFragment();

        void showLoginActivity();
    }

    public interface Presenter extends BasePresenter{

        void loadSpinnerDropdownUser(Context context);

        void loadTextOnButton(AdapterView<?> adapterView, android.view.View view, int i);

        void loadPlaceAutoCompleteFragment();

        void loadAlumniOrStudentSignUpFragment();

        void loadLoginActivity();
    }
}
