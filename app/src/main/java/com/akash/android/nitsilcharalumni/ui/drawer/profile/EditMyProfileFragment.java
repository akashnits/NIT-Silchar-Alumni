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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.akash.android.nitsilcharalumni.utils.Contants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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


    private static final String TAG = EditMyProfileFragment.class.getSimpleName();
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    public EditMyProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EditMyProfileFragment newInstance(Bundle args) {
        EditMyProfileFragment fragment = new EditMyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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

        HashMap<String, Object> userHashMap = (HashMap<String, Object>) getArguments().get("userMap");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarEditMyProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutEditMyProfile.setTitle("Edit Mode");
        collapsingToolbarLayoutEditMyProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutEditMyProfile.setCollapsedTitleTextColor(Color.WHITE);

        if (userHashMap.get("mAboutYou") != null)
            etEditAboutYouMyProfile.setText(userHashMap.get("mAboutYou").toString());
        if (userHashMap.get("mClassOf") != null)
            etEditClassOfMyProfile.setText(userHashMap.get("mClassOf").toString());
        etEditLocationMyProfile.setText(userHashMap.get("mLocation").toString());
        if (userHashMap.get("mContact") != null)
            etContactMyProfile.setText(userHashMap.get("mContact").toString());
        etEmailMyProfile.setText(userHashMap.get("mEmail").toString());
        if (userHashMap.get("mOrganisation") != null)
            etOrganisationMyProfile.setText(userHashMap.get("mOrganisation").toString());
        if (userHashMap.get("mDesignation") != null)
            etDesignationMyProfile.setText(userHashMap.get("mDesignation").toString());
        if (userHashMap.get("mSkills") != null)
            etSkillsMyProfile.setText(userHashMap.get("mSkills").toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.editmyprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.editDone) {
            //build the map with the updated values
            Map<String, Object> updatedMap = new HashMap<>();
            if (etEditAboutYouMyProfile.getText() != null)
                updatedMap.put("mAboutYou", etEditAboutYouMyProfile.getText().toString());
            if (etEditClassOfMyProfile.getText() != null && !TextUtils.isEmpty(etEditClassOfMyProfile.getText()))
                updatedMap.put("mClassOf", etEditClassOfMyProfile.getText().toString());
            if(etEditLocationMyProfile.getText() != null)
                updatedMap.put("mLocation", etEditLocationMyProfile.getText().toString());
            if (etContactMyProfile.getText() != null)
                updatedMap.put("mContact", etContactMyProfile.getText().toString());
            if(etEmailMyProfile.getText() != null)
                updatedMap.put("mEmail", etEmailMyProfile.getText().toString());
            if (etOrganisationMyProfile.getText() != null && !TextUtils.isEmpty(etOrganisationMyProfile.getText()))
                updatedMap.put("mOrganisation", etOrganisationMyProfile.getText().toString());
            if (etDesignationMyProfile.getText() != null)
                updatedMap.put("mDesignation", etDesignationMyProfile.getText().toString());
            if (etSkillsMyProfile.getText() != null)
                updatedMap.put("mSkills", etSkillsMyProfile.getText().toString());

            // write updates to the server
            mFirestore.collection(Constants.USER_COLLECTION)
                    .document(mAuth.getCurrentUser().getEmail())
                    .update(updatedMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });

            // restart the loader to get the updates from server
            MyProfileFragment frag;
            if(( frag= (MyProfileFragment) getFragmentManager().findFragmentByTag("MyProfileFragment")) != null)
                frag.restartLoarder();
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
