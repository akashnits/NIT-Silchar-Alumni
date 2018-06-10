package com.akash.android.nitsilcharalumni.ui.job;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

    private int mJobLocationCheckedPosition;
    private int mJobOrganisationCheckedPoistion;
    private Context mContext;
    private MainActivity mMainActivity;

    public FilterJobFragment() {
        // Required empty public constructor
    }

    public static FilterJobFragment newInstance() {
        FilterJobFragment fragment = new FilterJobFragment();
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
        View view = inflater.inflate(R.layout.fragment_filter_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
        mMainActivity= (MainActivity) getActivity();
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
                        mJobLocationCheckedPosition = i;
                        break;
                    }
                }
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.white));
            }

            if (mMainActivity.isJobOrganisationPreferenceChecked()) {
                organisationConstraint = mMainActivity.getmJobOrganisationConstraint();
                mMainActivity.setJobOrganisationPreferenceChecked(true);
                //find the position of classOf constraint
                String[] organisations = getResources().getStringArray(R.array.organisation);
                for (int j = 0; j < organisations.length; j++) {
                    if (organisations[j].equals(organisationConstraint)) {
                        mJobOrganisationCheckedPoistion = j;
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
            boolean isJobOrganisationPrefChecked= savedInstanceState.getBoolean("isJobOrganisationChecked");
            mJobLocationCheckedPosition= savedInstanceState.getInt("jobLocationCheckedPosition");
            mJobOrganisationCheckedPoistion= savedInstanceState.getInt("jobOrganisationCheckedPosition");

            if(isJobLocationPrefChecked){
                btJobFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            if(isJobOrganisationPrefChecked){
                btJobFilterOrganisation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            if(isJobLocationPrefChecked  || isJobOrganisationPrefChecked)
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
                mMainActivity.setJobOrganisationPreferenceChecked(false);
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
                            (R.array.location)[mJobLocationCheckedPosition]);
                }else {
                    mMainActivity.setmJobLocationConstraint(null);
                }
                if(mMainActivity.isJobOrganisationPreferenceChecked()){
                    mMainActivity.setmJobOrganisationConstraint(getResources().getStringArray
                            (R.array.organisation)[mJobOrganisationCheckedPoistion]);
                }else {
                    mMainActivity.setmJobOrganisationConstraint(null);
                }
                if(mMainActivity.isJobLocationPreferenceChecked() ||
                        mMainActivity.isJobOrganisationPreferenceChecked())
                    JobFragment.isJobFilterApplied = true;
                else
                    JobFragment.isJobFilterApplied = false;
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }

    private void showJobLocationAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.choose_a_location);

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
                    if (!mMainActivity.isJobOrganisationPreferenceChecked())
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
        builder.setTitle(R.string.choose_a_company);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View content = inflater.inflate(R.layout.dialog_select_job_organisation, null);

        builder.setView(content);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isJobOrganisationPreferenceChecked()) {
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
                if (mMainActivity.isJobOrganisationPreferenceChecked())
                    mMainActivity.setJobOrganisationPreferenceChecked(false);
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

    public int getmJobLocationCheckedPosition() {
        return mJobLocationCheckedPosition;
    }

    public void setmJobLocationCheckedPosition(int mJobLocationCheckedPosition) {
        this.mJobLocationCheckedPosition = mJobLocationCheckedPosition;
    }

    public int getmJobOrganisationCheckedPoistion() {
        return mJobOrganisationCheckedPoistion;
    }

    public void setmJobOrganisationCheckedPoistion(int mJobOrganisationCheckedPoistion) {
        this.mJobOrganisationCheckedPoistion = mJobOrganisationCheckedPoistion;
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
        mMainActivity.setFilterJobFragRotated(true);
        outState.putBoolean("isJobLocationChecked", mMainActivity.isJobLocationPreferenceChecked());
        outState.putBoolean("isJobOrganisationChecked", mMainActivity.isJobOrganisationPreferenceChecked());
        outState.putInt("jobLocationCheckedPosition", mJobLocationCheckedPosition);
        outState.putInt("jobOrganisationCheckedPosition", mJobOrganisationCheckedPoistion);
    }
}
