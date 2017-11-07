package com.akash.android.nitsilcharalumni.signup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        View.OnFocusChangeListener{


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
    @BindView(R.id.edit_text_choose_location)
    EditText editTextChooseLocation;
    @BindView(R.id.til_choose_location)
    TextInputLayout tilChooseLocation;
    @BindView(R.id.btn_create_account_final)
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

    private SignUpFragmentComponent signUpFragmentComponent;

    private boolean isAlumnus;

    private SignUpContract.Presenter mPresenter;

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

        editTextUsernameCreate.setOnFocusChangeListener(this);
        editTextEmailCreate.setOnFocusChangeListener(this);
        editTextPasswordCreate1.setOnFocusChangeListener(this);
        editTextPasswordCreate2.setOnFocusChangeListener(this);
        editTextChooseLocation.setOnFocusChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.edit_text_choose_location, R.id.tv_sign_in, R.id.btn_create_account_final})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_text_choose_location:
                    mPresenter.loadPlaceAutoCompleteFragment();
                break;
            case R.id.tv_sign_in:
                mPresenter.loadLoginActivity();
                break;
            case R.id.btn_create_account_final:
                mPresenter.loadAlumniOrStudentSignUpFragment();
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
    public void onResume() {
        super.onResume();
        String placeName = mDataManager.getCurrentLocation();

        if (placeName != null && !TextUtils.isEmpty(placeName)) {
            editTextChooseLocation.setText(placeName);
        }
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter= presenter;
    }

    @Override
    public void showSpinnerDropdownUser(ArrayAdapter<CharSequence> adapter) {
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.loadTextOnButton(adapterView,view,i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTextOnButton(String selected) {
        btnCreateAccountFinal.setText(selected);
    }

    @Override
    public void commitPlaceAutoCompleteFragment() {
        ((SignUpActivity) getActivity()).showPlaceAutoCompleteFragment();
    }

    @Override
    public void commitAlumniOrStudentSignUpFragment() {
        ((SignUpActivity) getActivity()).showAlumniOrStudentSignUpFragment(isAlumnus);
    }

    @Override
    public void showLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(!hasFocus){
            int id= view.getId();
            Editable editable= ((EditText) view).getText();
            switch (id){
                case R.id.edit_text_username_create:
                    if(!TextUtils.isEmpty(editable)){
                        String regx = "^[\\p{L} .'-]+$";
                        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(editable);
                        if(!matcher.find() && editTextUsernameCreate != null){
                            editTextUsernameCreate.setError("Invalid name");
                        }
                    }else if(editTextUsernameCreate != null)
                    {
                        editTextUsernameCreate.setError("Enter name");
                    }
                    break;
                case R.id.edit_text_email_create:
                     if(!TextUtils.isEmpty(editable)){
                            if(!Patterns.EMAIL_ADDRESS.matcher(editable).find() && editTextEmailCreate != null){
                                editTextEmailCreate.setError("Invalid email");
                            }
                        }else if(editTextEmailCreate != null){
                            editTextEmailCreate.setError("Enter email");
                     }
                        break;
                case R.id.edit_text_password_create_1:
                    if(!TextUtils.isEmpty(editable)){
                        if(editable.length() < 6 && editTextPasswordCreate1 !=  null){
                            editTextPasswordCreate1.setError("Password should be of 6 characters min ");
                        }
                    }else if(editTextPasswordCreate1 != null){
                        editTextPasswordCreate1.setError("Enter password");
                    }
                    break;
                case R.id.edit_text_password_create_2:
                        String pass1= editTextPasswordCreate1 != null? editTextPasswordCreate1.getText().toString(): null;
                        String pass2= editTextPasswordCreate2 != null? editTextPasswordCreate2.getText().toString(): null;
                        if(pass1 != null && pass2 != null && !pass1.equals(pass2) && editTextPasswordCreate2 != null){
                        editTextPasswordCreate2.setError("Password mismatch");
                }
                    break;
                default: break;
            }
        }
    }
}
