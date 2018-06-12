package com.akash.android.nitsilcharalumni.ui.alumni;


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
import com.akash.android.nitsilcharalumni.adapter.AlumniClassOfAdapter;
import com.akash.android.nitsilcharalumni.adapter.AlumniLocationAdapter;
import com.akash.android.nitsilcharalumni.ui.MainActivity;

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
    @BindView(R.id.btFilterClassOf)
    Button btFilterClassOf;
    @BindView(R.id.btFilterLocation)
    Button btFilterLocation;
    @BindView(R.id.btFilterApply)
    Button btFilterApply;
    @BindView(R.id.cvFilterAlumni)
    CardView cvFilterAlumni;
    Unbinder unbinder;

    private int mLocationCheckedPosition;
    private int mAlumniClassOfCheckedPoistion;
    private Context mContext;
    private MainActivity mMainActivity;

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
        if(savedInstanceState == null) {
            String locationConstraint= null;
            String classOfConstraint= null;
            if (mMainActivity.isLocationPreferenceChecked()) {
                locationConstraint = mMainActivity.getmAlumniLocationConstraint();
                mMainActivity.setLocationPreferenceChecked(true);
                //find the position of location constraint
                String[] locations = getResources().getStringArray(R.array.location);
                for (int i = 0; i < locations.length; i++) {
                    if (locations[i].equals(locationConstraint)) {
                        mLocationCheckedPosition = i;
                        break;
                    }
                }
                btFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btFilterLocation.setBackgroundColor(getResources().getColor(R.color.white));
            }

            if (mMainActivity.isClassOfPreferenceChecked()) {
                classOfConstraint = mMainActivity.getmAlumniClassOfConstraint();
                mMainActivity.setClassOfPreferenceChecked(true);
                //find the position of classOf constraint
                String[] classOf = getResources().getStringArray(R.array.alumniClassOf);
                for (int j = 0; j < classOf.length; j++) {
                    if (classOf[j].equals(classOfConstraint)) {
                        mAlumniClassOfCheckedPoistion = j;
                        break;
                    }
                }
                btFilterClassOf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btFilterClassOf.setBackgroundColor(getResources().getColor(R.color.white));
            }
            if (locationConstraint != null || classOfConstraint != null)
                btFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            else
                btFilterApply.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            boolean isLocationPrefChecked= savedInstanceState.getBoolean("isLocationChecked");
            boolean isClassOfPrefChecked= savedInstanceState.getBoolean("isClassOfChecked");
            mLocationCheckedPosition= savedInstanceState.getInt("alumniLocationCheckedPosition");
            mAlumniClassOfCheckedPoistion= savedInstanceState.getInt("alumniClassOfCheckedPosition");

            if(isLocationPrefChecked){
                btFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            if(isClassOfPrefChecked){
                btFilterClassOf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            if(isLocationPrefChecked  || isClassOfPrefChecked)
                btFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mMainActivity= (MainActivity) getActivity();
    }


    @OnClick({R.id.ivClose, R.id.btFilterClassOf, R.id.btFilterLocation,
            R.id.btFilterApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                AlumniFragment.isAlumniFilterApplied = false;
                mMainActivity.setClassOfPreferenceChecked(false);
                mMainActivity.setLocationPreferenceChecked(false);
                mMainActivity.setmAlumniLocationConstraint(null);
                mMainActivity.setmAlumniClassOfConstraint(null);
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btFilterClassOf:
                showClassOfAlertDialog();
                break;
            case R.id.btFilterLocation:
                showAlumniLocationAlertDialog();
                break;
            case R.id.btFilterApply:
                if (mMainActivity.isLocationPreferenceChecked()) {
                    mMainActivity.setmAlumniLocationConstraint(getResources().getStringArray
                            (R.array.location)[mLocationCheckedPosition]);
                }else {
                    mMainActivity.setmAlumniLocationConstraint(null);
                }
                if(mMainActivity.isClassOfPreferenceChecked()){
                    mMainActivity.setmAlumniClassOfConstraint(getResources().getStringArray
                            (R.array.alumniClassOf)[mAlumniClassOfCheckedPoistion]);
                }else {
                    mMainActivity.setmAlumniClassOfConstraint(null);
                }
                if(mMainActivity.isLocationPreferenceChecked() || mMainActivity.isClassOfPreferenceChecked())
                    AlumniFragment.isAlumniFilterApplied = true;
                else
                    AlumniFragment.isAlumniFilterApplied = false;
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }


    private void showAlumniLocationAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Choose a location");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View content = inflater.inflate(R.layout.dialog_select_alumni_location, null);
        builder.setView(content);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isLocationPreferenceChecked()) {
                    btFilterLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btFilterLocation.setBackgroundColor(getResources().getColor(android.R.color.white));
                    if (!mMainActivity.isClassOfPreferenceChecked())
                        btFilterApply.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isLocationPreferenceChecked())
                    mMainActivity.setLocationPreferenceChecked(false);
                btFilterLocation.setBackgroundColor(getResources().getColor(R.color.white));
                dialog.dismiss();
            }
        });

        RecyclerView rvAlumniLocation = content.findViewById(R.id.rvAlumniLocation);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAlumniLocation.setLayoutManager(lm);
        rvAlumniLocation.hasFixedSize();
        AlumniLocationAdapter alumniAdapter = new AlumniLocationAdapter(mContext,
                getFragmentManager(), rvAlumniLocation);
        rvAlumniLocation.setAdapter(alumniAdapter);
        builder.show();
    }

    private void showClassOfAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.choose_a_graduation_year);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View content = inflater.inflate(R.layout.dialog_select_alumni_classof, null);

        builder.setView(content);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isClassOfPreferenceChecked()) {
                    btFilterClassOf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btFilterApply.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btFilterClassOf.setBackgroundColor(getResources().getColor(android.R.color.white));
                    if (!mMainActivity.isLocationPreferenceChecked())
                        btFilterApply.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mMainActivity.isClassOfPreferenceChecked())
                    mMainActivity.setClassOfPreferenceChecked(false);
                btFilterClassOf.setBackgroundColor(getResources().getColor(R.color.white));
                dialog.dismiss();
            }
        });

        RecyclerView rvAlumniClassOf = content.findViewById(R.id.rvAlumniClassOf);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAlumniClassOf.setLayoutManager(lm);
        rvAlumniClassOf.hasFixedSize();
        AlumniClassOfAdapter alumniClassOfAdapter = new AlumniClassOfAdapter(mContext,
                getFragmentManager(), rvAlumniClassOf);
        rvAlumniClassOf.setAdapter(alumniClassOfAdapter);
        builder.show();
    }


    public int getmLocationCheckedPosition() {
        return mLocationCheckedPosition;
    }

    public void setmLocationCheckedPosition(int mLocationCheckedPosition) {
        this.mLocationCheckedPosition = mLocationCheckedPosition;
    }

    public void setmAlumniClassOfCheckedPoistion(int mAlumniClassOfCheckedPoistion) {
        this.mAlumniClassOfCheckedPoistion = mAlumniClassOfCheckedPoistion;
    }

    public int getmAlumniClassOfCheckedPoistion() {
        return mAlumniClassOfCheckedPoistion;
    }

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMainActivity.setFilterAlumniFragRotated(true);
        outState.putBoolean("isLocationChecked", mMainActivity.isLocationPreferenceChecked());
        outState.putBoolean("isClassOfChecked", mMainActivity.isClassOfPreferenceChecked());
        outState.putInt("alumniClassOfCheckedPosition", mAlumniClassOfCheckedPoistion);
        outState.putInt("alumniLocationCheckedPosition", mLocationCheckedPosition);
    }
}
