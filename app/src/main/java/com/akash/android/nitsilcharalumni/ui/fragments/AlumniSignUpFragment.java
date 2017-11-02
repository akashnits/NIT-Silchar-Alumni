package com.akash.android.nitsilcharalumni.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akash.android.nitsilcharalumni.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniSignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniSignUpFragment extends Fragment {

    public AlumniSignUpFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AlumniSignUpFragment newInstance(String param1, String param2) {
        AlumniSignUpFragment fragment = new AlumniSignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alumni_sign_up, container, false);
    }

}
