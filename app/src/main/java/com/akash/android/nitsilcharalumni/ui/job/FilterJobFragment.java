package com.akash.android.nitsilcharalumni.ui.job;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.JobLocationAdapter;
import com.akash.android.nitsilcharalumni.adapter.JobOrganisationAdapter;
import com.akash.android.nitsilcharalumni.ui.MainActivity;

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

    private int mLocationCheckedPosition;
    private int mOrganisationCheckedPoistion;
    private Context mContext;
    private MainActivity mMainActivity;

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

        if(savedInstanceState == null) {
            String locationConstraint= null;
            String organisationConstraint= null;
            if (mMainActivity.isJobLocationPreferenceChecked()) {
                locationConstraint = mMainActivity.getmJobLocationConstraint();
                mMainActivity.setJobLocationPreferenceChecked(true);
                //find the position of location constraint
                String[] locations = getResources().getStringArray(R.array.location);
                for (int i = 0; i < locations.length; i++) {
                    if (locations[i].equals(locationConstraint)) {
                        mLocationCheckedPosition = i;
                        break;
                    }
                }
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.white));
            }

            if (mMainActivity.isOrganisationPreferenceChecked()) {
                organisationConstraint = mMainActivity.getmJobOrganisationConstraint();
                mMainActivity.setOrganisationPreferenceChecked(true);
                //find the position of classOf constraint
                String[] organisations = getResources().getStringArray(R.array.organisation);
                for (int j = 0; j < organisations.length; j++) {
                    if (organisations[j].equals(organisationConstraint)) {
                        mOrganisationCheckedPoistion = j;
                        break;
                    }
                }
                btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.white));
            }
            if (locationConstraint != null || organisationConstraint != null)
                btJobFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            else
                btJobFilterApply.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            boolean isJobLocationPrefChecked= savedInstanceState.getBoolean("isJobLocationChecked");
            boolean isOrganisationPrefChecked= savedInstanceState.getBoolean("isOrganisationChecked");

            if(isJobLocationPrefChecked){
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            if(isOrganisationPrefChecked){
                btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            if(isJobLocationPrefChecked  || isOrganisationPrefChecked)
                btJobFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
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
                JobFragment.isJobFilterApplied = false;
                mMainActivity.setOrganisationPreferenceChecked(false);
                mMainActivity.setJobLocationPreferenceChecked(false);
                mMainActivity.setmJobLocationConstraint(null);
                mMainActivity.setmJobOrganisationConstraint(null);
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btJobFilterLocation:
                showJobLocationAlertDialog();
                break;
            case R.id.btJobFilterOrganisation:
                showJobOrganisationAlertDialog();
                break;
            case R.id.btJobFilterApply:
                if (mMainActivity.isJobLocationPreferenceChecked()) {
                    mMainActivity.setmJobLocationConstraint(getResources().getStringArray
                            (R.array.location)[mLocationCheckedPosition]);
                }
                if(mMainActivity.isOrganisationPreferenceChecked()){
                    mMainActivity.setmJobOrganisationConstraint(getResources().getStringArray
                            (R.array.organisation)[mOrganisationCheckedPoistion]);
                }
                if(mMainActivity.isLocationPreferenceChecked() || mMainActivity.isClassOfPreferenceChecked())
                    JobFragment.isJobFilterApplied = true;
                else
                    JobFragment.isJobFilterApplied = false;
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }

    private void showJobLocationAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Choose a location");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View content = inflater.inflate(R.layout.dialog_select_job_location, null);
        builder.setView(content);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isJobLocationPreferenceChecked()) {
                    btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btJobFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btJobFilterLocation.setBackgroundColor(getResources().getColor(android.R.color.white));
                    if (!mMainActivity.isOrganisationPreferenceChecked())
                        btJobFilterApply.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isJobLocationPreferenceChecked())
                    mMainActivity.setJobLocationPreferenceChecked(false);
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.white));
                dialog.dismiss();
            }
        });

        RecyclerView rvJobLocation = content.findViewById(R.id.rvJobLocation);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvJobLocation.setLayoutManager(lm);
        rvJobLocation.hasFixedSize();
        JobLocationAdapter jobLocationAdapter = new JobLocationAdapter(mContext,
                getFragmentManager(), rvJobLocation );
        rvJobLocation.setAdapter(jobLocationAdapter);
        builder.show();
    }

    private void showJobOrganisationAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Choose a graduation year");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View content = inflater.inflate(R.layout.dialog_select_job_organisation, null);

        builder.setView(content);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isOrganisationPreferenceChecked()) {
                    btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btJobFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btJobFilterOrganisation.setBackgroundColor(getResources().getColor(android.R.color.white));
                    if (!mMainActivity.isJobLocationPreferenceChecked())
                        btJobFilterApply.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isOrganisationPreferenceChecked())
                    mMainActivity.setOrganisationPreferenceChecked(false);
                btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.white));
                dialog.dismiss();
            }
        });

        RecyclerView rvJobOrganisation = content.findViewById(R.id.rvJobOrganisation);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvJobOrganisation.setLayoutManager(lm);
        rvJobOrganisation.hasFixedSize();
        JobOrganisationAdapter jobOrganisationAdapter = new JobOrganisationAdapter(mContext,
                getFragmentManager(), rvJobOrganisation);
        rvJobOrganisation.setAdapter(jobOrganisationAdapter);
        builder.show();
    }

    public int getmLocationCheckedPosition() {
        return mLocationCheckedPosition;
    }

    public void setmLocationCheckedPosition(int mLocationCheckedPosition) {
        this.mLocationCheckedPosition = mLocationCheckedPosition;
    }

    public int getmOrganisationCheckedPoistion() {
        return mOrganisationCheckedPoistion;
    }

    public void setmOrganisationCheckedPoistion(int mOrganisationCheckedPoistion) {
        this.mOrganisationCheckedPoistion = mOrganisationCheckedPoistion;
    }

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isJobLocationChecked", mMainActivity.isJobLocationPreferenceChecked());
        outState.putBoolean("isOrganisationChecked", mMainActivity.isOrganisationPreferenceChecked());
    }
}
