package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.akash.android.nitsilcharalumni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlumniLocationFragment extends Fragment {


    @BindView(R.id.toolbarAlumniLocation)
    Toolbar toolbarAlumniLocation;
    @BindView(R.id.alumni_location_pref_content)
    FrameLayout prefContent;
    Unbinder unbinder;

    private Context mContext;

    public AlumniLocationFragment() {
        // Required empty public constructor
    }

    public static AlumniLocationFragment newInstance() {
        AlumniLocationFragment fragment = new AlumniLocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumni_location, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarAlumniLocation);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getFragmentManager() != null)
            getFragmentManager().beginTransaction().replace(R.id.alumni_location_pref_content,
                AlumniLocationPrefFragment.newInstance()).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.alumnilocation, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.alumniLocation){
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }
}
