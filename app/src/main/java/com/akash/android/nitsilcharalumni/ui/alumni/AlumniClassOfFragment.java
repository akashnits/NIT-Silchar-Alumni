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
public class AlumniClassOfFragment extends Fragment {

    @BindView(R.id.toolbarAlumniClassOf)
    Toolbar toolbarAlumniClassOf;
    @BindView(R.id.alumni_classOf_pref_content)
    FrameLayout alumniClassOfPrefContent;
    Unbinder unbinder;
    private Context mContext;


    public AlumniClassOfFragment() {
        // Required empty public constructor
    }

    public static AlumniClassOfFragment newInstance() {
        AlumniClassOfFragment fragment = new AlumniClassOfFragment();
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
        View view = inflater.inflate(R.layout.fragment_alumni_class_of, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarAlumniClassOf);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getFragmentManager() != null)
            getFragmentManager().beginTransaction().replace(R.id.alumni_classOf_pref_content,
                    AlumniClassOfPrefFragment.newInstance()).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alumniclassof, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.alumniClassOf) {
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }
}
