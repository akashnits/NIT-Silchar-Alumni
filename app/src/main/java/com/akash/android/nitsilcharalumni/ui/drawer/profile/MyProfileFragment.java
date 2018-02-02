package com.akash.android.nitsilcharalumni.ui.drawer.profile;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.akash.android.nitsilcharalumni.R.id.postJobFabProgressCircle;

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

    private static final String TAG = MyProfileFragment.class.getSimpleName();

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private Map<String, Object> mUserMap;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();

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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadUserData();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarMyProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutMyProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutMyProfile.setCollapsedTitleTextColor(Color.WHITE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.myprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.editProfile)
            ((MainActivity) getActivity()).commitEditMyProfileFragment();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void downloadUserData(){

        final ProgressDialog progressDialog = new ProgressDialog((MainActivity)getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

                mFirestore.collection(Constants.USER_COLLECTION)
                        .document(mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot snapshot = task.getResult();
                                    if (snapshot.exists()) {
                                        mUserMap= snapshot.getData();
                                        collapsingToolbarLayoutMyProfile.setTitle(mUserMap.get("mName").toString());
                                        tvClassofTextMyProfile.setText(mUserMap.get("mClassOf").toString());
                                        tvLocationTextMyProfile.setText(mUserMap.get("mLocation").toString());
                                        tvEmailTextMyProfile.setText(mUserMap.get("mEmail").toString());
                                        tvOrganisationTextMyProfile.setText(mUserMap.get("mOrganisation").toString());
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                                progressDialog.dismiss();
                            }
                        });
    }


}
