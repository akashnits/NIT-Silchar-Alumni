package com.akash.android.nitsilcharalumni.ui.alumni;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterAlumniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterAlumniFragment extends Fragment {

    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.tvFilters)
    TextView tvFilters;
    @BindView(R.id.btFilterName)
    Button btFilterName;
    @BindView(R.id.btFilterClassOf)
    Button btFilterClassOf;
    @BindView(R.id.btFilterLocation)
    Button btFilterLocation;
    @BindView(R.id.btFilterOrganisation)
    Button btFilterOrganisation;
    @BindView(R.id.btFilterApply)
    Button btFilterApply;
    @BindView(R.id.cvFilterAlumni)
    CardView cvFilterAlumni;
    Unbinder unbinder;

    public FilterAlumniFragment() {
        // Required empty public constructor
    }

    public static FilterAlumniFragment newInstance() {
        FilterAlumniFragment fragment = new FilterAlumniFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_alumni, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ivClose, R.id.btFilterName, R.id.btFilterClassOf, R.id.btFilterLocation,
            R.id.btFilterOrganisation, R.id.btFilterApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btFilterName:
                break;
            case R.id.btFilterClassOf:
                break;
            case R.id.btFilterLocation:
                break;
            case R.id.btFilterOrganisation:
                break;
            case R.id.btFilterApply:
                break;
        }
    }


}
