package com.akash.android.nitsilcharalumni.login;



public interface ILoginPresenter {
    void attemptLogin(String user, String password);
    void validationComplete(boolean auth);
}
