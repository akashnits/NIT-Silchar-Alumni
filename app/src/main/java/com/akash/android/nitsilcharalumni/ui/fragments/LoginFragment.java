package com.akash.android.nitsilcharalumni.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.dagger.DaggerAppComponent;
import com.akash.android.nitsilcharalumni.login.ILoginView;
import com.akash.android.nitsilcharalumni.login.LoginPresenter;
import com.akash.android.nitsilcharalumni.ui.activities.SignUpActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends Fragment implements ILoginView {


    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    Unbinder unbinder;
    @BindView(R.id.tvNewUser)
    TextView tvNewUser;
    @BindView(R.id.tvCreateAccount)
    TextView tvCreateAccount;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        LoginPresenter loginPresenter= new LoginPresenter(this);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginError() {
        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvCreateAccount)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), SignUpActivity.class));
    }
}
