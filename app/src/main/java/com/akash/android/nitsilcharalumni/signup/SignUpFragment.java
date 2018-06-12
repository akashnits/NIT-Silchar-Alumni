package com.akash.android.nitsilcharalumni.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.DaggerSignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.SignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.SignUpFragmentModule;
import com.akash.android.nitsilcharalumni.login.LoginActivity;
import com.akash.android.nitsilcharalumni.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements SignUpContract.View, AdapterView.OnItemSelectedListener,
        View.OnFocusChangeListener, RadioGroup.OnCheckedChangeListener {


    public static final String TAG = SignUpFragment.class.getSimpleName();
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.edit_text_username_create)
    EditText editTextUsernameCreate;
    @BindView(R.id.til_username_create)
    TextInputLayout tilUsernameCreate;
    @BindView(R.id.edit_text_email_create)
    EditText editTextEmailCreate;
    @BindView(R.id.til_email_create)
    TextInputLayout tilEmailCreate;
    @BindView(R.id.edit_text_password_create_1)
    EditText editTextPasswordCreate1;
    @BindView(R.id.til_password_create)
    TextInputLayout tilPasswordCreate;
    @BindView(R.id.edit_text_password_create_2)
    EditText editTextPasswordCreate2;
    @BindView(R.id.til_password_recreate)
    TextInputLayout tilPasswordRecreate;
    @BindView(R.id.radioButtonMale)
    RadioButton radioButtonMale;
    @BindView(R.id.radioButtonFemale)
    RadioButton radioButtonFemale;
    @BindView(R.id.tvTypeofUser)
    TextView tvTypeofUser;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.validateSignUpForm)
    Button btnCreateAccountFinal;
    @BindView(R.id.tv_already_have_account)
    TextView tvAlreadyHaveAccount;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;
    @BindView(R.id.linear_layout_create_account_activity)
    LinearLayout linearLayoutCreateAccountActivity;
    @BindView(R.id.signUpFragment)
    ScrollView signUpFragment;
    Unbinder unbinder;
    @BindView(R.id.rgMaleFemale)
    RadioGroup rgMaleFemale;

    private SignUpFragmentComponent signUpFragmentComponent;

    private String mGender;

    private String mTypeOfUser;

    private SignUpContract.Presenter mPresenter;

    private User mUser;

    @Inject
    DataManager mDataManager;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        getSignUpFragmentComponent().inject(this);
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadSpinnerDropdownUser(getContext());
        spinner.setOnItemSelectedListener(this);
        rgMaleFemale.setOnCheckedChangeListener(this);

        editTextUsernameCreate.setOnFocusChangeListener(this);
        editTextEmailCreate.setOnFocusChangeListener(this);
        editTextPasswordCreate1.setOnFocusChangeListener(this);
        editTextPasswordCreate2.setOnFocusChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_sign_in, R.id.validateSignUpForm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in:
                mPresenter.loadLoginActivity();
                break;
            case R.id.validateSignUpForm:
                boolean isValid= mPresenter.validateSignUpForm(editTextUsernameCreate.getText(),
                        editTextUsernameCreate,
                        editTextEmailCreate.getText(), editTextEmailCreate,
                        editTextPasswordCreate1.getText(), editTextPasswordCreate1,
                        editTextPasswordCreate2.getText(), editTextPasswordCreate2,
                        mGender,
                        mTypeOfUser);
                if(isValid) {
                    mUser= new User(editTextUsernameCreate.getText().toString(),
                            editTextEmailCreate.getText().toString(),
                            mGender,
                            mTypeOfUser
                            );
                    char[] password= editTextPasswordCreate1.getText().toString().toCharArray();
                    mPresenter.loadPlaceAutoCompleteFragment(mUser, password);
                }
                else
                    Snackbar.make(getView(), R.string.please_enter_details_correctly,
                            BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    public SignUpFragmentComponent getSignUpFragmentComponent() {
        if (signUpFragmentComponent == null) {
            signUpFragmentComponent = DaggerSignUpFragmentComponent.builder()
                    .signUpFragmentModule(new SignUpFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return signUpFragmentComponent;
    }


    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSpinnerDropdownUser(ArrayAdapter<CharSequence> adapter) {
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mTypeOfUser = mPresenter.loadTextOnButton(adapterView, view, i);
        mDataManager.saveTypeOfUser(mTypeOfUser);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getContext(), R.string.nothing_selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTextOnButton(String selected) {
        btnCreateAccountFinal.setText(selected);
    }

    @Override
    public void commitPlaceAutoCompleteFragment(User user, char[] password) {
        ((SignUpActivity) getActivity()).showPlaceAutoCompleteFragment(user, password);
    }

    @Override
    public void showLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            int id = view.getId();
            Editable editable = ((EditText) view).getText();
            switch (id) {
                case R.id.edit_text_username_create:
                    mPresenter.validateName(editable, editTextUsernameCreate);
                    break;
                case R.id.edit_text_email_create:
                    mPresenter.validateEmail(editable, editTextEmailCreate);
                    break;
                case R.id.edit_text_password_create_1:
                    mPresenter.validatePassword(editable, editTextPasswordCreate1);
                    break;
                case R.id.edit_text_password_create_2:
                    mPresenter.validateRepassword(editTextPasswordCreate1, editTextPasswordCreate2);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId){
            case R.id.radioButtonMale:
                mGender= "male";
                break;
            case R.id.radioButtonFemale:
                mGender= "female";
                break;
            default:
                mGender= null;
                break;
        }
    }
}
