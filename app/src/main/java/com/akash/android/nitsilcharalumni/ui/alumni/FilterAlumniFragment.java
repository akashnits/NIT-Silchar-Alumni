package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    public static final String ALUMNI_LOCATION = "alumni_location";
    public static final String ALUMNI_CLASS_OF = "alumni_classOf";

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String[] mAlumniLocationArray;
    private String[] mAlumniClassOfArray;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlumniLocationArray = getResources().getStringArray(R.array.location);
        mAlumniClassOfArray = getResources().getStringArray(R.array.classOfFilter);

        btFilterApply.setEnabled(false);
        btFilterApply.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_unpressed));
        btFilterLocation.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_unpressed));
        btFilterClassOf.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_unpressed));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        for (int i = 0; i < mAlumniLocationArray.length; i++) {
            if (mSharedPreferences.getBoolean(String.format("%s_%s", ALUMNI_LOCATION,
                    mAlumniLocationArray[i]), false)) {

                btFilterApply.setEnabled(true);
                btFilterApply.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_pressed));
                btFilterApply.setTextColor(getResources().getColor(android.R.color.white));

                btFilterLocation.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_pressed));
                btFilterLocation.setTextColor(getResources().getColor(android.R.color.white));
                break;
            }
        }
        for(int j=0; j< mAlumniClassOfArray.length; j++){
            if(mSharedPreferences.getBoolean(String.format("%s_%s", ALUMNI_CLASS_OF,
                    mAlumniClassOfArray[j]), false)){

                if(!btFilterApply.isEnabled()){
                    btFilterApply.setEnabled(true);
                    btFilterApply.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_pressed));
                    btFilterApply.setTextColor(getResources().getColor(android.R.color.white));
                }
                btFilterClassOf.setBackground(getResources().getDrawable(R.drawable.filter_btn_backgrnd_pressed));
                btFilterClassOf.setTextColor(getResources().getColor(android.R.color.white));
                break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ivClose, R.id.btFilterClassOf, R.id.btFilterLocation, R.id.btFilterApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                for (String key : mAlumniLocationArray) {
                    editor.remove(String.format("%s_%s", ALUMNI_LOCATION, key));
                    editor.apply();
                }
                for(String key : mAlumniClassOfArray){
                    editor.remove(String.format("%s_%s", ALUMNI_CLASS_OF, key));
                    editor.apply();
                }
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btFilterClassOf:
                ((MainActivity)getActivity()).commitAlumniClassOfFragment();
                break;
            case R.id.btFilterLocation:
                ((MainActivity) getActivity()).commitAlumniLocationFragment();
                break;
            case R.id.btFilterApply:
                AlumniFragment.setFilterApplied(true);
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }
}
