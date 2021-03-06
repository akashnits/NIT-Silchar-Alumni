package com.akash.android.nitsilcharalumni.signup.placeAutoComplete;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.DaggerPlaceAutoCompleteFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.PlaceAutoCompleteFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.PlaceAutoCompleteFragmentModule;
import com.akash.android.nitsilcharalumni.login.LoginActivity;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.SignUpActivity;
import com.facebook.login.Login;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceAutoCompleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceAutoCompleteFragment extends Fragment implements PlaceAutoCompleteContract.View {

    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 454;

    private PlaceAutoCompleteContract.Presenter mPresenter;

    private PlaceAutoCompleteFragmentComponent placeAutoCompleteFragmentComponent;

    private boolean isSocialLogin= false;


    @Inject
    DataManager mDatamanager;

    @BindView(R.id.etChoosePlace)
    EditText etChoosePlace;
    @BindView(R.id.btSignUpOrNext)
    Button btSignUpOrNext;
    @BindView(R.id.chooseLocationLayout)
    CardView chooseLocationLayout;
    Unbinder unbinder;


    public PlaceAutoCompleteFragment() {
        // Required empty public constructor
    }

    public static PlaceAutoCompleteFragment newInstance(User user, char[] password) {
        PlaceAutoCompleteFragment placeAutoCompleteFragment= new PlaceAutoCompleteFragment();
        Bundle args= new Bundle();
        args.putParcelable("user", user);
        args.putCharArray("password", password);
        placeAutoCompleteFragment.setArguments(args);
        return placeAutoCompleteFragment;
    }

    public static PlaceAutoCompleteFragment newInstance(User user){
        PlaceAutoCompleteFragment placeAutoCompleteFragment= new PlaceAutoCompleteFragment();
        Bundle args= new Bundle();
        args.putParcelable("user", user);
        placeAutoCompleteFragment.setArguments(args);
        return placeAutoCompleteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getPlaceAutoCompleteFragmentComponent().inject(this);
        mPresenter = new PlaceAutoCompletePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_auto_complete, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments().get("password") == null)
            isSocialLogin= true;
        String typeOfUser = mDatamanager.getTypeOfUser();
        if (typeOfUser.equals("Faculty")) {
            btSignUpOrNext.setText("Sign up");
        }
    }


    public PlaceAutoCompleteFragmentComponent getPlaceAutoCompleteFragmentComponent() {
        if (placeAutoCompleteFragmentComponent == null) {
            placeAutoCompleteFragmentComponent = DaggerPlaceAutoCompleteFragmentComponent.builder()
                    .placeAutoCompleteFragmentModule(new PlaceAutoCompleteFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return placeAutoCompleteFragmentComponent;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.etChoosePlace, R.id.btSignUpOrNext})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.etChoosePlace:
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                break;
            case R.id.btSignUpOrNext:
                boolean flag = mPresenter.validateLocationEntered(etChoosePlace.getText());
                if(flag) {
                    Bundle b= getArguments();
                    User user= b.getParcelable("user");
                    if(user != null) {
                        user.setmLocation(etChoosePlace.getText().toString());
                    }
                    if (!mDatamanager.getTypeOfUser().equals("Faculty"))
                        if(isSocialLogin){
                            mPresenter.loadAlumniOrStudentSignUpFragment(user);
                        }else {
                            mPresenter.loadAlumniOrStudentSignUpFragment(user, b.getCharArray("password"));
                        }
                    else{
                        if(isSocialLogin){
                            mPresenter.writeLoggedInUser(user);
                        }else {
                            mPresenter.createAccountWithEmailAndPassword((SignUpActivity)
                                    getActivity(), user, b.getCharArray("password"));
                        }
                    }
                }else {
                    Snackbar.make(getView(), R.string.please_select_location, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                //Snackbar.make(findViewById(R.id.mainLayout), place.getName().toString() , Snackbar.LENGTH_SHORT).show();
                etChoosePlace.setText(place.getName().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Toast.makeText(getContext(),
                        status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void setPresenter(PlaceAutoCompleteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void commitAlumniOrStudentSignUpFragment(User user, char[] password) {
        boolean isAlumnus= mDatamanager.getTypeOfUser().equals("Alumni");
        ((SignUpActivity) getActivity()).showAlumniOrStudentSignUpFragment(user, password, isAlumnus);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), R.string.auth_failed,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        if(isSocialLogin){
            ((LoginActivity) getActivity()).finish();
        }else {
            ((SignUpActivity) getActivity()).finish();
        }
    }

    @Override
    public void saveLoggedInUsername(String name) {
        mDatamanager.saveUserName(name);
    }

    @Override
    public void commitAlumniOrStudentSignUpFragment(User user) {
        boolean isAlumnus= mDatamanager.getTypeOfUser().equals("Alumni");
        ((LoginActivity) getActivity()).showAlumniOrStudentSignUpFragment(user, isAlumnus);
    }
}
