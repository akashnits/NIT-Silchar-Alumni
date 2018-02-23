package com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.AlumniOrStudentSignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.DaggerAlumniOrStudentSignUpFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.AlumniOrStudentSignUpFragmentModule;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniOrStudentSignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniOrStudentSignUpFragment extends Fragment implements AlumniOrStudentSignUpContract.View,
        AdapterView.OnItemSelectedListener {


    @BindView(R.id.tvClassof)
    TextView tvClassof;
    @BindView(R.id.spinnerClassOf)
    Spinner spinnerClassOf;
    @BindView(R.id.tvOrganisation)
    TextView tvOrganisation;
    @BindView(R.id.etOrganisation)
    EditText etOrganisation;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.btCreateAccount)
    Button btCreateAccount;
    Unbinder unbinder;

    @Inject
    DataManager mDataManager;

    private AlumniOrStudentSignUpContract.Presenter mPresenter;

    private String mClassOf;

    private AlumniOrStudentSignUpFragmentComponent alumniOrStudentSignUpFragmentComponent;

    public AlumniOrStudentSignUpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlumniOrStudentSignUpFragment newInstance(User user, char[] password, boolean isAlumnus) {
        AlumniOrStudentSignUpFragment fragment = new AlumniOrStudentSignUpFragment();
        Bundle b = new Bundle();
        b.putBoolean("alumnus", isAlumnus);
        b.putParcelable("user", user);
        b.putCharArray("password", password);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new AlumniOrStudentSignUpPresenter(this);
        getAlumniOrStudentSignUpFragmentComponent().inject(this);
    }

    public AlumniOrStudentSignUpFragmentComponent getAlumniOrStudentSignUpFragmentComponent(){
        if (alumniOrStudentSignUpFragmentComponent == null) {
            alumniOrStudentSignUpFragmentComponent = DaggerAlumniOrStudentSignUpFragmentComponent.builder()
                    .alumniOrStudentSignUpFragmentModule(new AlumniOrStudentSignUpFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return alumniOrStudentSignUpFragmentComponent;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumni_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            boolean isAlumnus = getArguments().getBoolean("alumnus");
            if (isAlumnus) {
                textInputLayout2.setVisibility(View.VISIBLE);
                tvOrganisation.setVisibility(View.VISIBLE);
            } else {
                textInputLayout2.setVisibility(View.GONE);
                tvOrganisation.setVisibility(View.GONE);
            }
        }
        mPresenter.loadClassOfSpinner(getContext());
        spinnerClassOf.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(AlumniOrStudentSignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showClassOfSpinner(ArrayAdapter<CharSequence> adapter) {
        spinnerClassOf.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mPresenter.loadYearOnClassOfDropDown(adapterView, view, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.btCreateAccount)
    public void onViewClicked() {

        if(mPresenter.validateSpinnerItemSelected(spinnerClassOf)) {
            Snackbar.make(getView(), "Please choose class of ", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Bundle b= getArguments();
        User user= b.getParcelable("user");
        //TODO: insert classof and organisaton in user object
        switch(user.getmTypeOfUser()){
            case "Student":
                user.setmClassOf(mClassOf);
                break;
            case "Alumni":
                user.setmClassOf(mClassOf);
                user.setmOrganisation(etOrganisation.getText().toString());
                break;
            default:break;
        }

        char[] password= b.getCharArray("password");
        mPresenter.createAccountWithEmailAndPassword(getActivity(), user, password);
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        ((SignUpActivity) getActivity()).finish();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), "Authentication failed: You're probably signed up already",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateClassof(String classOf) {
        mClassOf= classOf;
    }

    @Override
    public void saveLoggedInUsername(String name) {
        mDataManager.saveUserName(name);
    }
}
