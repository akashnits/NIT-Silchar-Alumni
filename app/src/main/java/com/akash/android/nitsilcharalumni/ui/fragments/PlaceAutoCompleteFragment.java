package com.akash.android.nitsilcharalumni.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.di.component.DaggerPlaceAutoCompleteFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.PlaceAutoCompleteFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.PlaceAutoCompleteFragmentModule;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.ui.activities.SignUpActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceAutoCompleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceAutoCompleteFragment extends Fragment implements PlaceSelectionListener{


    private PlaceAutoCompleteFragmentComponent placeAutoCompleteFragmentComponent;

    @Inject
    DataManager mDatamanager;

    public   PlaceAutoCompleteFragment() {
        // Required empty public constructor
    }

    public static PlaceAutoCompleteFragment newInstance() {
        return new PlaceAutoCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getPlaceAutoCompleteFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_auto_complete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            final EditText etPlaceAutoComplete = view.findViewById(R.id.place_autocomplete_search_input);
            etPlaceAutoComplete.setHint("Start typing city name");
            PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                    getActivity().getFragmentManager()
                            .findFragmentById(R.id.place_autocomplete_fragment);
            autocompleteFragment.setOnPlaceSelectedListener(this);
    }


    @Override
    public void onPlaceSelected(Place place) {
        mDatamanager.saveCurrentLocation(place.getName().toString());
        Toast.makeText(getContext(), mDatamanager.getCurrentLocation(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Status status) {

    }

    public PlaceAutoCompleteFragmentComponent getPlaceAutoCompleteFragmentComponent(){
        if(placeAutoCompleteFragmentComponent == null){
            placeAutoCompleteFragmentComponent= DaggerPlaceAutoCompleteFragmentComponent.builder()
                    .placeAutoCompleteFragmentModule(new PlaceAutoCompleteFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return placeAutoCompleteFragmentComponent;
    }

}
