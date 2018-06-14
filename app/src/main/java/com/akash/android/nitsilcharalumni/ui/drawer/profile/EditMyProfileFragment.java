package com.akash.android.nitsilcharalumni.ui.drawer.profile;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

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

    public static final int SELECT_PROFILE_PICTURE = 749;

    private static final String TAG = EditMyProfileFragment.class.getSimpleName();
    @BindView(R.id.pbEditMyProfileFragment)
    ProgressBar pbEditMyProfileFragment;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private Uri mSelectedImageUri;
    private Context mContext;
    private FirebaseStorage mFirebaseStorage;
    private Uri mDownloadUri;
    private boolean mEditDoneVisible = true;

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
        mFirebaseStorage = FirebaseStorage.getInstance();
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
        if (userHashMap.get("mProfileImageUrl") != null)
            Picasso.with(mContext).load(userHashMap.get("mProfileImageUrl").toString()).fit().into(backdropEditProfileImage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.editmyprofile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.getItem(0).setEnabled(mEditDoneVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.editDone) {
            //build the map with the updated values
            Map<String, Object> updatedMap = new HashMap<>();
            if (etEditAboutYouMyProfile.getText() != null)
                updatedMap.put("mAboutYou", etEditAboutYouMyProfile.getText().toString());
            if (etEditClassOfMyProfile.getText() != null)
                updatedMap.put("mClassOf", etEditClassOfMyProfile.getText().toString());
            if (etEditLocationMyProfile.getText() != null)
                updatedMap.put("mLocation", etEditLocationMyProfile.getText().toString());
            if (etContactMyProfile.getText() != null)
                updatedMap.put("mContact", etContactMyProfile.getText().toString());
            if (etEmailMyProfile.getText() != null)
                updatedMap.put("mEmail", etEmailMyProfile.getText().toString());
            if (etOrganisationMyProfile.getText() != null)
                updatedMap.put("mOrganisation", etOrganisationMyProfile.getText().toString());
            if (etDesignationMyProfile.getText() != null)
                updatedMap.put("mDesignation", etDesignationMyProfile.getText().toString());
            if (etSkillsMyProfile.getText() != null)
                updatedMap.put("mSkills", etSkillsMyProfile.getText().toString());
            if (mDownloadUri != null && !Uri.EMPTY.equals(mDownloadUri)) {
                final String downloadUrl = mDownloadUri.toString();
                updatedMap.put("mProfileImageUrl", downloadUrl);

                //updating feed and job documents for author image url

                final WriteBatch batchFeed = mFirestore.batch();
                mFirestore.collection(Constants.FEED_COLLECTION)
                        .whereEqualTo("mAuthorEmail", mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                        if (documentSnapshot.exists()) {
                                            DocumentReference documentReference = documentSnapshot.getReference();
                                            batchFeed.update(documentReference, "mAuthorImageUrl", downloadUrl);
                                        }
                                    }
                                    batchFeed.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.v(TAG, "Feed Batch write completed");
                                        }
                                    });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                final WriteBatch batchJob = mFirestore.batch();
                mFirestore.collection(Constants.JOB_COLLECTION)
                        .whereEqualTo("mAuthorEmail", mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                        if (documentSnapshot.exists()) {
                                            DocumentReference documentReference = documentSnapshot.getReference();
                                            batchJob.update(documentReference, "mAuthorImageUrl", downloadUrl);
                                        }
                                    }
                                    batchJob.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.v(TAG, "Job Batch write completed");
                                        }
                                    });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }

            // write updates to the server in the user document
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
            if ((frag = (MyProfileFragment) getFragmentManager().findFragmentByTag("MyProfileFragment")) != null)
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

    @OnClick(R.id.ivEditProfileImage)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select a picture"), SELECT_PROFILE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PROFILE_PICTURE) {
                mSelectedImageUri = data.getData();
                Log.i(TAG, "Uri is " + mSelectedImageUri);
                if (mSelectedImageUri != null && !Uri.EMPTY.equals(mSelectedImageUri)) {
                    mEditDoneVisible = false;
                    if (getActivity() != null)
                        getActivity().invalidateOptionsMenu();

                    final StorageReference storageReference = mFirebaseStorage.getReference()
                            .child(Constants.PROFILE_IMAGE_COLLECTION + mSelectedImageUri.getLastPathSegment());

                    //show progress bar while uploading the profile picture
                    pbEditMyProfileFragment.setVisibility(View.VISIBLE);
                    UploadTask uploadTask = storageReference.putFile(mSelectedImageUri);

                    /*uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDownloadUri = taskSnapshot.getDownloadUrl();
                            Log.v(TAG, "Download uri is" + mDownloadUri);

                            if (pbEditMyProfileFragment != null)
                                pbEditMyProfileFragment.setVisibility(View.GONE);
                            //show done icon
                            mEditDoneVisible = true;
                            if (getActivity() != null)
                                getActivity().invalidateOptionsMenu();

                            //update the image on backdropEditProfileImage
                            Picasso.with(mContext).load(mDownloadUri.toString()).fit()
                                    .placeholder(R.drawable.loading)
                                    .into(backdropEditProfileImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, R.string.upload_failed, Toast.LENGTH_SHORT).show();
                            if (pbEditMyProfileFragment != null)
                                pbEditMyProfileFragment.setVisibility(View.GONE);
                            mEditDoneVisible = true;
                            if (getActivity() != null)
                                getActivity().invalidateOptionsMenu();
                        }
                    });*/

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                mDownloadUri = task.getResult();
                                Log.v(TAG, "Download uri is" + mDownloadUri);

                                if (pbEditMyProfileFragment != null)
                                    pbEditMyProfileFragment.setVisibility(View.GONE);
                                //show done icon
                                mEditDoneVisible = true;
                                if (getActivity() != null)
                                    getActivity().invalidateOptionsMenu();

                                //update the image on backdropEditProfileImage
                                Picasso.with(mContext).load(mDownloadUri.toString()).fit()
                                        .placeholder(R.drawable.loading)
                                        .into(backdropEditProfileImage);
                            } else {
                                // Handle failures
                                // ...
                                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                                if (pbEditMyProfileFragment != null)
                                    pbEditMyProfileFragment.setVisibility(View.GONE);
                                mEditDoneVisible = true;
                                if (getActivity() != null)
                                    getActivity().invalidateOptionsMenu();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, R.string.please_select_a_image_to_upload, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
