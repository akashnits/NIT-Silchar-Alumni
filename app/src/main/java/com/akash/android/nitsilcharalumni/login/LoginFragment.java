package com.akash.android.nitsilcharalumni.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends Fragment {


    @BindView(R.id.tvLoginTitle)
    TextView tvLoginTitle;
    @BindView(R.id.tvSignInWithSocial)
    TextView tvSignInWithSocial;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btSignIn)
    Button btSignIn;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.tvDont)
    TextView tvDont;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    Unbinder unbinder;

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.tvSignUp)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), SignUpActivity.class));
    }
}
