package com.akash.android.nitsilcharalumni.signup.social;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;


public class SocialLoginPresenter implements SocialLoginContract.Presenter {


    private SocialLoginContract.View mSocialLoginView;
    private SocialLoginInteractor mSocialLoginInteractor;

    public SocialLoginPresenter(SocialLoginContract.View mSocialLoginView) {
        this.mSocialLoginView = mSocialLoginView;

        mSocialLoginView.setPresenter(this);
        mSocialLoginInteractor= new SocialLoginInteractor(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadSpinnerDropdown(Context context) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.typeOfUser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSocialLoginView.showSpinnerDropdownUser(adapter);
    }

    @Override
    public boolean validateForm(String gender, String typeOfUser) {
        if (gender == null || typeOfUser == null) {
            return false;
        }
        return true;
    }

    @Override
    public void loadPlaceAutoCompleteFragment(User user) {
        mSocialLoginView.showPlaceAutoCompleteFragment(user);
    }
}
