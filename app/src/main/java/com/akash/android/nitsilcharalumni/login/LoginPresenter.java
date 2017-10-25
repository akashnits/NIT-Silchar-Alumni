package com.akash.android.nitsilcharalumni.login;



public class LoginPresenter implements ILoginPresenter {

    private ILoginView mILoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenter(ILoginView mLoginView) {
        this.mILoginView = mLoginView;
        this.mLoginInteractor= new LoginInteractor(this);
    }

    @Override
    public void attemptLogin(String user, String password) {
        mLoginInteractor.validateCredentials();
    }

    @Override
    public void validationComplete(boolean auth) {

    }
}
