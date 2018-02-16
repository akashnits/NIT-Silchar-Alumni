package com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment;

import android.app.Activity;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;
import com.akash.android.nitsilcharalumni.model.User;
import com.google.firebase.auth.FirebaseUser;


public class AlumniOrStudentSignUpContract {

    public interface View extends BaseView<AlumniOrStudentSignUpContract.Presenter> {

        void showClassOfSpinner(ArrayAdapter<CharSequence> adapter);

        void showMainActivity();

        void showErrorMessage();

        void updateClassof(String classOf);

        void saveLoggedInUsername(String name);
    }

    public interface Presenter extends BasePresenter {

        void loadClassOfSpinner(Context context);

        void loadYearOnClassOfDropDown(AdapterView adapterView, android.view.View view, int position) ;

        void createAccountWithEmailAndPassword(Activity activity, User user, char[] password);

        void setFirebaseUser(FirebaseUser firebaseUser);

        void loadErrorMessage();

        boolean validateSpinnerItemSelected(Spinner classOf);

        void saveLoggedInUserName(String name);
    }
}
