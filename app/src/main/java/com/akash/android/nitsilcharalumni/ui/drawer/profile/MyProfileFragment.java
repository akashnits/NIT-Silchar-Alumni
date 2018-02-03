package com.akash.android.nitsilcharalumni.ui.drawer.profile;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.ObjectsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment implements LoaderManager.LoaderCallbacks<Map<String, Object>> {


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
    @BindView(R.id.pbMyProfileFragment)
    ProgressBar pbMyProfileFragment;

    private static final String TAG = MyProfileFragment.class.getSimpleName();
    private Context mContext;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private static final int USERMAP_LOADER_ID = 0;
    private HashMap<String, Object> mUserHashMap;


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

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(USERMAP_LOADER_ID, null, this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarMyProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutMyProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutMyProfile.setCollapsedTitleTextColor(Color.WHITE);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.myprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle args= new Bundle();
        args.putSerializable("userMap", mUserHashMap);
        if (item.getItemId() == R.id.editProfile)
            ((MainActivity) getActivity()).commitEditMyProfileFragment(args);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Map<String, Object>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Map<String, Object>>(mContext) {
            Map<String, Object> userMap = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (userMap == null) {
                    pbMyProfileFragment.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    forceLoad();
                } else {
                    deliverResult(userMap);
                }
            }

            @Nullable
            @Override
            public Map<String, Object> loadInBackground() {
                mFirestore.collection(Constants.USER_COLLECTION)
                        .document(mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot snapshot = task.getResult();
                                    if (snapshot.exists()) {
                                        userMap = snapshot.getData();
                                        mUserHashMap= toHashMap(userMap);
                                        deliverResult(userMap);
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                return null;
            }

            @Override
            public void deliverResult(@Nullable Map<String, Object> data) {
                if(data != null){
                    userMap = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Map<String, Object>> loader, Map<String, Object> data) {
        if(data != null) {
            collapsingToolbarLayoutMyProfile.setTitle(data.get("mName").toString());
            tvClassofTextMyProfile.setText(data.get("mClassOf").toString());
            tvLocationTextMyProfile.setText(data.get("mLocation").toString());
            tvEmailTextMyProfile.setText(data.get("mEmail").toString());
            tvOrganisationTextMyProfile.setText(data.get("mOrganisation").toString());
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            pbMyProfileFragment.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Map<String, Object>> loader) {

    }

    private HashMap<String, Object> toHashMap(Map<String, Object> userMap){
        return new HashMap<>(userMap);
    }
}
