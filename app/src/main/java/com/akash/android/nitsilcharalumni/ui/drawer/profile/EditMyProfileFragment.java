package com.akash.android.nitsilcharalumni.ui.drawer.profile;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Use the {@link EditMyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMyProfileFragment extends Fragment {


    @BindView(R.id.backdropEditProfileImage)
    ImageView backdropEditProfileImage;
    @BindView(R.id.ivEditProfileImage)
    ImageView ivEditProfileImage;
    @BindView(R.id.toolbarEditMyProfile)
    Toolbar toolbarEditMyProfile;
    @BindView(R.id.collapsingToolbarLayoutEditMyProfile)
    CollapsingToolbarLayout collapsingToolbarLayoutEditMyProfile;
    @BindView(R.id.appBarLayoutEditMyProfile)
    AppBarLayout appBarLayoutEditMyProfile;
    @BindView(R.id.tvAboutYouEditMyProfile)
    TextView tvAboutYouEditMyProfile;
    @BindView(R.id.etEditAboutYouMyProfile)
    TextInputEditText etEditAboutYouMyProfile;
    @BindView(R.id.tilEditAboutYouMyProfile)
    TextInputLayout tilEditAboutYouMyProfile;
    @BindView(R.id.tvClassOfEditMyProfile)
    TextView tvClassOfEditMyProfile;
    @BindView(R.id.etEditClassOfMyProfile)
    TextInputEditText etEditClassOfMyProfile;
    @BindView(R.id.tilEditClassOfMyProfile)
    TextInputLayout tilEditClassOfMyProfile;
    @BindView(R.id.tvLocationEditMyProfile)
    TextView tvLocationEditMyProfile;
    @BindView(R.id.etEditLocationMyProfile)
    TextInputEditText etEditLocationMyProfile;
    @BindView(R.id.tilEditLocationMyProfile)
    TextInputLayout tilEditLocationMyProfile;
    @BindView(R.id.tvContactEditMyProfile)
    TextView tvContactEditMyProfile;
    @BindView(R.id.etContactMyProfile)
    TextInputEditText etContactMyProfile;
    @BindView(R.id.tilEditContactMyProfile)
    TextInputLayout tilEditContactMyProfile;
    @BindView(R.id.tvEmailEditMyProfile)
    TextView tvEmailEditMyProfile;
    @BindView(R.id.etEmailMyProfile)
    TextInputEditText etEmailMyProfile;
    @BindView(R.id.tilEditEmailMyProfile)
    TextInputLayout tilEditEmailMyProfile;
    @BindView(R.id.tvOrganisationEditMyProfile)
    TextView tvOrganisationEditMyProfile;
    @BindView(R.id.etOrganisationMyProfile)
    TextInputEditText etOrganisationMyProfile;
    @BindView(R.id.tilEditOrganisationMyProfile)
    TextInputLayout tilEditOrganisationMyProfile;
    @BindView(R.id.tvDesignationEditMyProfile)
    TextView tvDesignationEditMyProfile;
    @BindView(R.id.etDesignationMyProfile)
    TextInputEditText etDesignationMyProfile;
    @BindView(R.id.tilEditDesignationMyProfile)
    TextInputLayout tilEditDesignationMyProfile;
    @BindView(R.id.tvSkillsEditMyProfile)
    TextView tvSkillsEditMyProfile;
    @BindView(R.id.etSkillsMyProfile)
    TextInputEditText etSkillsMyProfile;
    @BindView(R.id.tilEditSkillsMyProfile)
    TextInputLayout tilEditSkillsMyProfile;
    @BindView(R.id.coordinatorLayoutEditMyProfile)
    CoordinatorLayout coordinatorLayoutEditMyProfile;
    Unbinder unbinder;

    public EditMyProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EditMyProfileFragment newInstance() {
        EditMyProfileFragment fragment = new EditMyProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarEditMyProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutEditMyProfile.setTitle("Edit Mode");
        collapsingToolbarLayoutEditMyProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutEditMyProfile.setCollapsedTitleTextColor(Color.WHITE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.editmyprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editDone){
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
