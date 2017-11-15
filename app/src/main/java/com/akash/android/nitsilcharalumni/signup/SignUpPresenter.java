package com.akash.android.nitsilcharalumni.signup;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mSignUpView;


    public SignUpPresenter(SignUpContract.View signUpView) {
        this.mSignUpView = signUpView;

        mSignUpView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadSpinnerDropdownUser(Context context) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.typeOfUser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSignUpView.showSpinnerDropdownUser(adapter);
    }

    @Override
    public String loadTextOnButton(AdapterView<?> adapterView, View view, int i) {
        if (adapterView != null && adapterView.getChildCount() != 0) {
            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) adapterView.getChildAt(0)).setTextSize(14);
            return adapterView.getItemAtPosition(i).toString();
        }
        return null;
    }

    @Override
    public void loadPlaceAutoCompleteFragment() {
        mSignUpView.commitPlaceAutoCompleteFragment();
    }

    @Override
    public void loadAlumniOrStudentSignUpFragment() {
        mSignUpView.commitAlumniOrStudentSignUpFragment();
    }

    @Override
    public void loadLoginActivity() {
        mSignUpView.showLoginActivity();
    }

    @Override
    public boolean validateSignUpForm(Editable name, EditText editTextName,
                                      Editable email, EditText editTextEmail,
                                      Editable password, EditText editTextPassword,
                                      Editable rePassword, EditText editTextRePassword,
                                      String gender,
                                      String typeOfUser) {
        if(!ActivityUtils.validateName(name, editTextName)){
            return false;
        }else if(!ActivityUtils.validateEmail(email, editTextEmail)){
            return false;
        }else if(!ActivityUtils.validatePassword(password, editTextPassword)){
            return false;
        }else if(!ActivityUtils.validateRePassword(editTextPassword, editTextRePassword)){
            return false;
        }else if(gender == null){
            return false;
        }else if(typeOfUser == null){
            return false;
        }
        return true;
    }

    @Override
    public void validateName(Editable name, EditText editTextName) {
        ActivityUtils.validateName(name, editTextName);
    }

    @Override
    public void validateEmail(Editable email, EditText editTextEmail) {
        ActivityUtils.validateEmail(email, editTextEmail);
    }

    @Override
    public void validatePassword(Editable password, EditText editTextPassword) {
        ActivityUtils.validatePassword(password, editTextPassword);
    }

    @Override
    public void validateRepassword(EditText editTextPassword, EditText editTextRePassword) {
        ActivityUtils.validateRePassword(editTextPassword, editTextRePassword);
    }
}
