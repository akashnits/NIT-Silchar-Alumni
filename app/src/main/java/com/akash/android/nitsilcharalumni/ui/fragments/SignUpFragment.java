package com.akash.android.nitsilcharalumni.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.DaggerSignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.SignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.SignUpFragmentModule;
import com.akash.android.nitsilcharalumni.ui.activities.LoginActivity;
import com.akash.android.nitsilcharalumni.ui.activities.SignUpActivity;

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
public class SignUpFragment extends Fragment {


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.typeOfUser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView != null && adapterView.getChildCount() != 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(14);
                    String selected = adapterView.getItemAtPosition(i).toString();
                    isAlumnus= selected.equals("Alumni");
                    if (selected.equals("Student") || selected.equals("Alumni")) {
                        btnCreateAccountFinal.setText("Next");
                    } else {
                        btnCreateAccountFinal.setText("Create Account");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adapter);
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
                ((SignUpActivity) getActivity()).showPlaceAutoCompleteFragment();
                break;
            case R.id.tv_sign_in:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.btn_create_account_final:
                ((SignUpActivity) getActivity()).showAlumniOrStudentSignUpFragment(isAlumnus);
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
}
