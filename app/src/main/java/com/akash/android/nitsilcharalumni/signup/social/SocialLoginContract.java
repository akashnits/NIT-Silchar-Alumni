package com.akash.android.nitsilcharalumni.signup.social;

import android.content.Context;
import android.widget.Adapter;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;
import com.akash.android.nitsilcharalumni.model.User;


public class SocialLoginContract {
    public interface View extends BaseView<Presenter> {
        void showPlaceAutoCompleteFragment(User user);
        void showSpinnerDropdownUser(Adapter adapter);
    }

    public interface Presenter extends BasePresenter {
        void loadSpinnerDropdown(Context context);
        boolean validateForm(String gender, String typeOfUser);
        void loadPlaceAutoCompleteFragment(User user);
    }

}
