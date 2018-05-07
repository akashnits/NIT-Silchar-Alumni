package com.akash.android.nitsilcharalumni.signup.social;

import android.content.Context;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;


public class SocialLoginContract {
    public interface View extends BaseView<Presenter> {


    }

    public interface Presenter extends BasePresenter {
        void loadSpinnerDropdown(Context context);
    }

}
