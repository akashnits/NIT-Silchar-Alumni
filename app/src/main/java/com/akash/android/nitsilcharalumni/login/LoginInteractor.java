package com.akash.android.nitsilcharalumni.login;


public class LoginInteractor {
    private ILoginPresenter mILoginPresenter;

    public LoginInteractor(ILoginPresenter mILoginPresenter) {
        this.mILoginPresenter = mILoginPresenter;
    }

    public boolean validateCredentials(){
        //code for authetication
        mILoginPresenter.validationComplete(true);
        return true;
    }
}
