package com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.google.firebase.auth.FirebaseUser;

public class AlumniOrStudentSignUpPresenter implements AlumniOrStudentSignUpContract.Presenter {

    private AlumniOrStudentSignUpContract.View mAlumniOrStudentSignUpView;

    private AlumniOrStudentSignUpInteractor mAlumniOrStudentSignUpInteractor;

    private FirebaseUser mFirebaseUser;

    public AlumniOrStudentSignUpPresenter(AlumniOrStudentSignUpContract.View mAlumniOrStudentSignUpView) {
        this.mAlumniOrStudentSignUpView = mAlumniOrStudentSignUpView;

        mAlumniOrStudentSignUpView.setPresenter(this);
        mAlumniOrStudentSignUpInteractor= new AlumniOrStudentSignUpInteractor(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadClassOfSpinner(Context context) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.classOf, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAlumniOrStudentSignUpView.showClassOfSpinner(adapter);
    }

    @Override
    public void loadYearOnClassOfDropDown(AdapterView adapterView, View view, int position) {
        if (adapterView != null && adapterView.getChildCount() != 0) {
            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            ((TextView) adapterView.getChildAt(0)).setTextSize(20);
            if(position != 0)
                mAlumniOrStudentSignUpView.updateClassof(
                    ((TextView)adapterView.getChildAt(0)).getText().toString());
        }
    }

    @Override
    public void createAccountWithEmailAndPassword(Activity activity, User user, char[] password) {
        mAlumniOrStudentSignUpInteractor.signUpWithEmailAndPassword(activity, user, password);
    }

    @Override
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        mFirebaseUser= firebaseUser;
        mAlumniOrStudentSignUpView.showMainActivity();
    }

    @Override
    public void loadErrorMessage() {
        mAlumniOrStudentSignUpView.showErrorMessage();
    }

    @Override
    public boolean validateSpinnerItemSelected(Spinner classOf) {
        return classOf.getSelectedItem().toString().equals("Select");
    }

    @Override
    public void saveLoggedInUserName(String name) {
        mAlumniOrStudentSignUpView.saveLoggedInUsername(name);
    }
}
