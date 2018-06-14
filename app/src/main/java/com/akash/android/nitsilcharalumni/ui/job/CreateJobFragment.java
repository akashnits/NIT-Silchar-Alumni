package com.akash.android.nitsilcharalumni.ui.job;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.NITSilcharAlumniApp;
import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.DataManager;
import com.akash.android.nitsilcharalumni.di.component.CreateJobFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.DaggerCreateJobFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.CreateJobFragmentModule;
import com.akash.android.nitsilcharalumni.model.Job;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJobFragment extends Fragment implements FABProgressListener {


    @BindView(R.id.tvPostJobTitle)
    TextView tvPostJobTitle;
    @BindView(R.id.edit_text_post_job_title)
    EditText editTextPostJobTitle;
    @BindView(R.id.til_post_job_title)
    TextInputLayout tilPostJobTitle;
    @BindView(R.id.tvPostJobOrganisation)
    TextView tvPostJobOrganisation;
    @BindView(R.id.edit_text_post_job_organiation)
    EditText editTextPostJobOrganisation;
    @BindView(R.id.til_post_job_organisation)
    TextInputLayout tilPostJobOrganisation;
    @BindView(R.id.tvPostJobLocation)
    TextView tvPostJobLocation;
    @BindView(R.id.edit_text_post_job_location)
    EditText editTextPostJobLocation;
    @BindView(R.id.til_post_job_location)
    TextInputLayout tilPostJobLocation;
    @BindView(R.id.tvPostJobDescription)
    TextView tvPostJobDescription;
    @BindView(R.id.edit_text_post_job_description)
    EditText editTextPostJobDescription;
    @BindView(R.id.til_post_job_description)
    TextInputLayout tilPostJobDescription;
    @BindView(R.id.tvPostJobKeywords)
    TextView tvPostJobKeywords;
    @BindView(R.id.edit_text_post_job_keywords)
    EditText editTextPostJobKeywords;
    @BindView(R.id.til_post_job_keywords)
    TextInputLayout tilPostJobKeywords;
    @BindView(R.id.btPostJobSelectImage)
    Button btPostJobSelectImage;
    @BindView(R.id.postJobUploadFab)
    FloatingActionButton postJobUploadFab;
    @BindView(R.id.postJobfabLayout)
    RelativeLayout postJobfabLayout;
    @BindView(R.id.btPostJob)
    Button btPostJob;
    Unbinder unbinder;
    @BindView(R.id.postJobFabProgressCircle)
    FABProgressCircle postJobFabProgressCircle;
    @BindView(R.id.toolbarCreateJob)
    Toolbar toolbarCreateJob;

    @Inject
    DataManager mDataManager;

    public static final String TAG = CreateJobFragment.class.getSimpleName();

    private static final int SELECT_JOB_PICTURE = 7347;

    private CreateJobFragmentComponent createJobFragmentComponent;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mAuth;
    private String mAuthorName;
    private Uri mSelectedImageUri;
    private Uri mDownloadUri;
    private Context mContext;
    private String mNameOfFile;
    private FirebaseStorage mFirebaseStorage;
    private String mAuthorImageUrl;


    public CreateJobFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateJobFragment newInstance() {
        CreateJobFragment fragment = new CreateJobFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        getCreateJobFragmentComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public CreateJobFragmentComponent getCreateJobFragmentComponent() {
        if (createJobFragmentComponent == null) {
            createJobFragmentComponent = DaggerCreateJobFragmentComponent.builder()
                    .createJobFragmentModule(new CreateJobFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return createJobFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        postJobFabProgressCircle.attachListener(this);
        toolbarCreateJob.setTitle(getResources().getString(R.string.create_job_post));
        toolbarCreateJob.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarCreateJob);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLoggedInUserProfileImageUrl();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onFABProgressAnimationEnd() {
        Snackbar.make(postJobFabProgressCircle, R.string.upload_complete, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    @OnClick({R.id.postJobUploadFab, R.id.btPostJob, R.id.btPostJobSelectImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btPostJobSelectImage:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        getResources().getString(R.string.please_select_a_image_to_upload)), SELECT_JOB_PICTURE);
                break;
            case R.id.postJobUploadFab:
                //start uploading the selected picture
                if (mSelectedImageUri != null && !Uri.EMPTY.equals(mSelectedImageUri)) {
                    btPostJob.setEnabled(false);
                    //show animation that upload is in progress
                    postJobFabProgressCircle.show();
                    final StorageReference selectedFeedImagesReference = mFirebaseStorage.getReference()
                            .child(Constants.FEED_IMAGE_COLLECTION + mSelectedImageUri.getLastPathSegment());
                    UploadTask uploadTask = selectedFeedImagesReference.putFile(mSelectedImageUri);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return selectedFeedImagesReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                mDownloadUri = task.getResult();
                                postJobFabProgressCircle.beginFinalAnimation();
                                btPostJob.setEnabled(true);
                            } else {
                                // Handle failures
                                // ...
                                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                                btPostJob.setEnabled(true);
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, R.string.please_select_a_image_to_upload, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btPostJob:
                Map<String, Boolean> searchKeywordMap = new HashMap<>();
                if (TextUtils.isEmpty(editTextPostJobDescription.getText())) {
                    Toast.makeText(getContext(), R.string.brief_description_about_post,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //getting the search keywords
                if (!TextUtils.isEmpty(editTextPostJobKeywords.getText())) {
                    String jobSearchKeywords = editTextPostJobKeywords.getText().toString().trim();
                    if (!Pattern.compile("(\\w+)(,\\s*\\w+)*").matcher(jobSearchKeywords).matches()) {
                        Toast.makeText(getContext(), R.string.seach_keyword_warning,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String[] searchKeywordArray = jobSearchKeywords.split("\\s*,\\s*");
                    for (String str : searchKeywordArray)
                        searchKeywordMap.put(str.toLowerCase(), true);
                }

                mAuthorName = mDataManager.getUserName();
                //creating a job object
                if (mAuthorName != null) {
                    Job job = new Job(mAuthorName,
                            mAuthorImageUrl,
                            null,
                            TextUtils.isEmpty(editTextPostJobTitle.getText())? null: editTextPostJobTitle.getText().toString(),
                            TextUtils.isEmpty(editTextPostJobLocation.getText())? null: editTextPostJobLocation.getText().toString(),
                            TextUtils.isEmpty(editTextPostJobOrganisation.getText())? null:editTextPostJobOrganisation.getText().toString(),
                            editTextPostJobDescription.getText().toString(),
                            (mDownloadUri != null && !Uri.EMPTY.equals(mDownloadUri)) ? mDownloadUri.toString() :
                                    null,
                            searchKeywordMap,
                            mAuth.getCurrentUser().getEmail());

                    Toast.makeText(mContext, "Posting...", Toast.LENGTH_SHORT).show();
                    mFirebaseFirestore.collection(Constants.JOB_COLLECTION)
                            .add(job)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                    if (getFragmentManager() != null)
                                        getFragmentManager().popBackStackImmediate();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(getContext(),
                                            R.string.internal_error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSelectedImageUri != null) {
            outState.putString("selectedImage", mNameOfFile);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && mSelectedImageUri != null)
            if (savedInstanceState.get("selectedImage") != null) {
                btPostJobSelectImage.setText(savedInstanceState.get("selectedImage").toString());
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_JOB_PICTURE) {
                mSelectedImageUri = data.getData();
                Log.i(TAG, "Uri is " + mSelectedImageUri);
                if (mSelectedImageUri != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = mContext.getContentResolver().query(mSelectedImageUri,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String[] filePathElements = cursor.getString(columnIndex) != null ?
                                cursor.getString(columnIndex).split("\\/") : null;
                        if (filePathElements != null && filePathElements.length > 0) {
                            mNameOfFile = filePathElements[filePathElements.length - 1];
                            if (!TextUtils.isEmpty(mNameOfFile))
                                btPostJobSelectImage.setText(mNameOfFile);
                            else
                                btPostJobSelectImage.setText(R.string.image_selected);
                            cursor.close();
                        } else {
                            btPostJobSelectImage.setText(R.string.image_selected);
                        }
                    }
                }
            }
        }
    }

    private void getLoggedInUserProfileImageUrl() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseFirestore.getInstance().collection(Constants.USER_COLLECTION)
                .whereEqualTo("mEmail", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            User currentUser = null;
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                currentUser = documentSnapshot.toObject(User.class);
                            if (currentUser != null)
                                mAuthorImageUrl = currentUser.getmProfileImageUrl();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
