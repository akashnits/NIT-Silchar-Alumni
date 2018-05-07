package com.akash.android.nitsilcharalumni.signup.social;

import android.content.Context;



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

    }
}
