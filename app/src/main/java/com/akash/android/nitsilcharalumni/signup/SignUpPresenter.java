package com.akash.android.nitsilcharalumni.signup;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mSignUpView;

    private boolean isAlumnus;


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
    public void loadTextOnButton(AdapterView<?> adapterView, View view, int i) {
        if (adapterView != null && adapterView.getChildCount() != 0) {
            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) adapterView.getChildAt(0)).setTextSize(14);
            String selected = adapterView.getItemAtPosition(i).toString();
            isAlumnus = selected.equals("Alumni");
            if (selected.equals("Student") || selected.equals("Alumni")) {
                mSignUpView.showTextOnButton("Next");
            } else {
                mSignUpView.showTextOnButton("Create Account");
            }
        }
    }

    @Override
    public void loadPlaceAutoCompleteFragment() {
        mSignUpView.commitAlumniOrStudentSignUpFragment();
    }

    @Override
    public void loadAlumniOrStudentSignUpFragment() {
        mSignUpView.commitAlumniOrStudentSignUpFragment();
    }

    @Override
    public void loadLoginActivity() {
        mSignUpView.showLoginActivity();
    }
}
