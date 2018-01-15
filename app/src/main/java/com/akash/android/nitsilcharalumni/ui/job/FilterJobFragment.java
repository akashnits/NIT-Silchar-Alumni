package com.akash.android.nitsilcharalumni.ui.job;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
 * Use the {@link FilterJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterJobFragment extends Fragment {

    @BindView(R.id.ivJobClose)
    ImageView ivJobClose;
    @BindView(R.id.tvJobFilters)
    TextView tvJobFilters;
    @BindView(R.id.btJobFilterLocation)
    Button btJobFilterLocation;
    @BindView(R.id.btJobFilterOrganisation)
    Button btJobFilterOrganisation;
    @BindView(R.id.btJobFilterApply)
    Button btJobFilterApply;
    @BindView(R.id.cvFilterJob)
    CardView cvFilterJob;
    Unbinder unbinder;

    public FilterJobFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FilterJobFragment newInstance() {
        FilterJobFragment fragment = new FilterJobFragment();
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
        View view = inflater.inflate(R.layout.fragment_filter_job, container, false);
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

    @OnClick({R.id.ivJobClose, R.id.btJobFilterLocation, R.id.btJobFilterOrganisation, R.id.btJobFilterApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivJobClose:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btJobFilterLocation:
                break;
            case R.id.btJobFilterOrganisation:
                break;
            case R.id.btJobFilterApply:
                break;
        }
    }
}
