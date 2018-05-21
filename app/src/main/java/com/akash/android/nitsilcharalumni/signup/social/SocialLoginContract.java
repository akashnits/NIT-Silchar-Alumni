package com.akash.android.nitsilcharalumni.signup.social;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;
import com.akash.android.nitsilcharalumni.model.User;


public class SocialLoginContract {
    public interface View extends BaseView<Presenter> {
        void showPlaceAutoCompleteFragment(User user);
        void showSpinnerDropdownUser(ArrayAdapter<CharSequence> adapter);
    }

    public interface Presenter extends BasePresenter {
        void loadSpinnerDropdown(Context context);
        boolean validateForm(String gender, String typeOfUser);
        void loadPlaceAutoCompleteFragment(User user);
        String loadTextOnButton(AdapterView<?> parent, android.view.View View, int position);
    }

}
