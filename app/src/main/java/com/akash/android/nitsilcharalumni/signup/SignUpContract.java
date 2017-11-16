package com.akash.android.nitsilcharalumni.signup;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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

        void showLoginActivity();
    }

    public interface Presenter extends BasePresenter {

        void loadSpinnerDropdownUser(Context context);

        String loadTextOnButton(AdapterView<?> adapterView, android.view.View view, int i);

        void loadPlaceAutoCompleteFragment();

        void loadLoginActivity();

        void validateName(Editable name, EditText editTextName);

        void validateEmail(Editable email, EditText editTextEmail);

        void validatePassword(Editable password, EditText editTextPassword);

        void validateRepassword(EditText editTextPassword, EditText editTextRePassword);

        boolean validateSignUpForm(Editable name, EditText editTextName,
                                          Editable email, EditText editTextEmail,
                                          Editable password, EditText editTextPassword,
                                          Editable rePassword, EditText editTextRePassword,
                                          String gender,
                                          String typeOfUser);
    }
}
