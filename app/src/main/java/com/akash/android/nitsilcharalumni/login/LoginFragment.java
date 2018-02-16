package com.akash.android.nitsilcharalumni.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.DaggerLoginFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.LoginFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.LoginFragmentModule;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends Fragment implements LoginContract.View {


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

    private LoginContract.Presenter mLoginContractPresenter;

    private LoginFragmentComponent loginFragmentComponent;

    @Inject
    DataManager mDataManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginContractPresenter= new LoginPresenter(this);
        getLoginFragmentComponent().inject(this);
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


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginContractPresenter = presenter;
    }

    @OnClick({R.id.sign_in_button, R.id.login_button, R.id.btSignIn, R.id.tvSignUp})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sign_in_button:
                mLoginContractPresenter.signInUsingGoogle((LoginActivity) getActivity());
                break;
            case R.id.login_button:
                mLoginContractPresenter.signInUsingFacebook(loginButton, (LoginActivity) getActivity() );
                break;
            case R.id.btSignIn:
                boolean valid= mLoginContractPresenter.validateLoginForm(getActivity(),
                        username.getText(), username,
                        password.getText(), password);
                if(!valid) {
                    Snackbar.make(getView(), "Please enter details correctly",
                            BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
        }
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void showGoogleSignInErrorMessage() {
        Toast.makeText(getContext(), "Google sign in failed ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFacebookSignInErrorMessage() {
        Toast.makeText(getContext(), "Facebook sign in failed ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginContractPresenter.onActivityResultCallbackReceived(requestCode, resultCode, data,
                (LoginActivity) getActivity());
    }

    public LoginFragmentComponent getLoginFragmentComponent() {
        if (loginFragmentComponent == null) {
            loginFragmentComponent = DaggerLoginFragmentComponent.builder()
                    .loginFragmentModule(new LoginFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return loginFragmentComponent;
    }

    @Override
    public void saveLoggedInUsername(String name) {
        mDataManager.saveUserName(name);
    }
}
