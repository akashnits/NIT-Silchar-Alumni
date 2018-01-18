package com.akash.android.nitsilcharalumni.ui.drawer.profile;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {


    @BindView(R.id.backdropProfileImage)
    ImageView backdropProfileImage;
    @BindView(R.id.collapsingToolbarLayoutMyProfile)
    CollapsingToolbarLayout collapsingToolbarLayoutMyProfile;
    @BindView(R.id.appBarLayoutMyProfile)
    AppBarLayout appBarLayoutMyProfile;
    @BindView(R.id.tvAboutYouMyProfile)
    TextView tvAboutYouMyProfile;
    @BindView(R.id.tvAboutYouTextMyProfile)
    TextView tvAboutYouTextMyProfile;
    @BindView(R.id.tvClassOfMyProfile)
    TextView tvClassOfMyProfile;
    @BindView(R.id.tvClassofTextMyProfile)
    TextView tvClassofTextMyProfile;
    @BindView(R.id.tvLocationMyProfile)
    TextView tvLocationMyProfile;
    @BindView(R.id.tvLocationTextMyProfile)
    TextView tvLocationTextMyProfile;
    @BindView(R.id.tvContactMyProfile)
    TextView tvContactMyProfile;
    @BindView(R.id.tvContactTextMyProfile)
    TextView tvContactTextMyProfile;
    @BindView(R.id.tvEmailMyProfile)
    TextView tvEmailMyProfile;
    @BindView(R.id.tvEmailTextMyProfile)
    TextView tvEmailTextMyProfile;
    @BindView(R.id.tvOrganisationMyProfile)
    TextView tvOrganisationMyProfile;
    @BindView(R.id.tvOrganisationTextMyProfile)
    TextView tvOrganisationTextMyProfile;
    @BindView(R.id.tvDesignationMyProfile)
    TextView tvDesignationMyProfile;
    @BindView(R.id.tvDesignationTextMyProfile)
    TextView tvDesignationTextMyProfile;
    @BindView(R.id.tvSkillsMyProfile)
    TextView tvSkillsMyProfile;
    @BindView(R.id.tvSkillsTextMyProfile)
    TextView tvSkillsTextMyProfile;
    @BindView(R.id.coordinatorLayoutMyProfile)
    CoordinatorLayout coordinatorLayoutMyProfile;
    Unbinder unbinder;
    @BindView(R.id.toolbarMyProfile)
    Toolbar toolbarMyProfile;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();

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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarMyProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutMyProfile.setTitle("Akash Raj");
        collapsingToolbarLayoutMyProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutMyProfile.setCollapsedTitleTextColor(Color.WHITE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
