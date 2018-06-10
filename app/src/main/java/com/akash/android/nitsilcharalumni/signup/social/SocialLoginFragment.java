package com.akash.android.nitsilcharalumni.signup.social;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.akash.android.nitsilcharalumni.di.component.DaggerSocialLoginFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.SocialLoginFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.SocialLoginFragmentModule;
import com.akash.android.nitsilcharalumni.login.LoginActivity;
import com.akash.android.nitsilcharalumni.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialLoginFragment extends Fragment implements SocialLoginContract.View, AdapterView.OnItemSelectedListener,
        RadioGroup.OnCheckedChangeListener {


    @Inject
    DataManager mDataManager;

    @BindView(R.id.radioSocialButtonMale)
    RadioButton radioSocialButtonMale;
    @BindView(R.id.radioSocialButtonFemale)
    RadioButton radioSocialButtonFemale;
    @BindView(R.id.rgSocialMaleFemale)
    RadioGroup rgSocialMaleFemale;
    @BindView(R.id.tvSocialTypeOfUser)
    TextView tvSocialTypeOfUser;
    @BindView(R.id.socialSpinner)
    Spinner socialSpinner;
    @BindView(R.id.validateSocialForm)
    Button validateSocialForm;
    @BindView(R.id.linear_layout_social_login)
    LinearLayout linearLayoutSocialLogin;
    @BindView(R.id.socialLoginFragment)
    ScrollView socialLoginFragment;
    Unbinder unbinder;

    private SocialLoginContract.Presenter mPresenter;
    private SocialLoginFragmentComponent mSocialLoginFragmentComponent;
    private String mGender;
    private String mTypeOfUser;
    private String mEmail;


    public SocialLoginFragment() {
        // Required empty public constructor
    }


    public static SocialLoginFragment newInstance(String email) {
        SocialLoginFragment socialLoginFragment= new SocialLoginFragment();
        Bundle args= new Bundle();
        args.putString("email", email);
        socialLoginFragment.setArguments(args);
        return socialLoginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new SocialLoginPresenter(this);
        getSocialLoginFragmentComponent().inject(this);
        mEmail= getArguments().getString("email");
    }

    public SocialLoginFragmentComponent getSocialLoginFragmentComponent() {
        if (mSocialLoginFragmentComponent == null) {
            mSocialLoginFragmentComponent = DaggerSocialLoginFragmentComponent.builder()
                    .socialLoginFragmentModule(new SocialLoginFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return mSocialLoginFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_soical_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        socialSpinner.setOnItemSelectedListener(this);
        rgSocialMaleFemale.setOnCheckedChangeListener(this);

        mPresenter.loadSpinnerDropdown(getContext());
    }


    @Override
    public void setPresenter(SocialLoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTypeOfUser = mPresenter.loadTextOnButton(parent, view, position);
        mDataManager.saveTypeOfUser(mTypeOfUser);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), R.string.nothing_selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioSocialButtonMale:
                mGender = "male";
                break;
            case R.id.radioSocialButtonFemale:
                mGender = "female";
                break;
            default:
                mGender = null;
                break;
        }
    }

    @OnClick(R.id.validateSocialForm)
    public void onViewClicked() {
        if(mPresenter.validateForm(mGender, mTypeOfUser)){
            User user= new User();
            user.setmGender(mGender);
            user.setmTypeOfUser(mTypeOfUser);
            user.setmEmail(mEmail);
            user.setmName(mDataManager.getUserName());
            mPresenter.loadPlaceAutoCompleteFragment(user);
        }else{
            Toast.makeText(getContext(), R.string.please_complete_the_form, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPlaceAutoCompleteFragment(User user) {
        ((LoginActivity) getActivity()).showPlaceAutoCompleteFragment(user);
    }

    @Override
    public void showSpinnerDropdownUser(ArrayAdapter<CharSequence> adapter) {
        socialSpinner.setAdapter(adapter);
    }
}
