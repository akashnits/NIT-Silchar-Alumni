package com.akash.android.nitsilcharalumni.ui.feed;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.akash.android.nitsilcharalumni.di.component.CreateFeedFragmentComponent;
import com.akash.android.nitsilcharalumni.di.component.DaggerCreateFeedFragmentComponent;
import com.akash.android.nitsilcharalumni.di.module.CreateFeedFragmentModule;
import com.akash.android.nitsilcharalumni.model.Feed;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
 * Use the {@link CreateFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFeedFragment extends Fragment implements FABProgressListener {


    private static final String TAG = CreateFeedFragment.class.getSimpleName();
    @BindView(R.id.tvFeedDescription)
    TextView tvFeedDescription;
    @BindView(R.id.tvFeedKeywords)
    TextView tvFeedKeywords;
    @BindView(R.id.edit_text_feed_keywords)
    EditText editTextFeedKeywords;
    @BindView(R.id.til_feed_keywords)
    TextInputLayout tilFeedKeywords;
    @BindView(R.id.btSelectImage)
    Button btSelectImage;
    @BindView(R.id.uploadFab)
    FloatingActionButton uploadFab;
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;
    @BindView(R.id.btPost)
    Button btPost;
    @BindView(R.id.edit_text_feed_description)
    EditText editTextFeedDescription;
    @BindView(R.id.til_feed_description)
    TextInputLayout tilFeedDescription;
    Unbinder unbinder;
    @BindView(R.id.toolbarCreateFeed)
    Toolbar toolbarCreateFeed;
    @BindView(R.id.fabLayout)
    RelativeLayout fabLayout;

    @Inject
    DataManager mDatamanager;

    private static final int SELECT_FEED_PICTURE = 1;

    private CreateFeedFragmentComponent createFeedFragmentComponent;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mAuth;
    private String mAuthorName;
    private FirebaseStorage mFirebaseStorage;
    private Uri mSelectedImageUri;
    private Uri mDownloadUri;

    public CreateFeedFragment() {
        // Required empty public constructor
    }

    public static CreateFeedFragment newInstance() {
        CreateFeedFragment fragment = new CreateFeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getCreateFeedFragmentComponent().inject(this);
    }

    public CreateFeedFragmentComponent getCreateFeedFragmentComponent() {
        if (createFeedFragmentComponent == null) {
            createFeedFragmentComponent = DaggerCreateFeedFragmentComponent.builder()
                    .createFeedFragmentModule(new CreateFeedFragmentModule(this))
                    .appComponent(NITSilcharAlumniApp.get(getContext()).getAppComponent())
                    .build();
        }
        return createFeedFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_feed, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabProgressCircle.attachListener(this);
        toolbarCreateFeed.setTitle(getResources().getString(R.string.create_feed));
        toolbarCreateFeed.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarCreateFeed);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFABProgressAnimationEnd() {
        Snackbar.make(fabProgressCircle, "Upload Complete", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    @OnClick({R.id.btSelectImage, R.id.uploadFab, R.id.btPost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSelectImage:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select a picture"), SELECT_FEED_PICTURE);
                break;
            case R.id.uploadFab:
                //start uploading the selected picture
                if (mSelectedImageUri != null && !Uri.EMPTY.equals(mSelectedImageUri)) {
                    //show animation that upload is in progress
                    fabProgressCircle.show();
                    StorageReference selectedFeedImagesReference = mFirebaseStorage.getReference()
                            .child(Constants.FEED_IMAGE_COLLECTION + mSelectedImageUri.getLastPathSegment());
                    UploadTask uploadTask = selectedFeedImagesReference.putFile(mSelectedImageUri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDownloadUri= taskSnapshot.getDownloadUrl();
                            Log.v(TAG, "Download uri is" + mDownloadUri);
                            fabProgressCircle.beginFinalAnimation();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Please select a image to upload", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btPost:
                if (TextUtils.isEmpty(editTextFeedDescription.getText())) {
                    Toast.makeText(getContext(), "Please enter brief description about the post",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Boolean> searchKeywordMap = new HashMap<>();
                //getting the search keywords
                if (!TextUtils.isEmpty(editTextFeedKeywords.getText())) {
                    String feedSearchKeywords = editTextFeedKeywords.getText().toString().trim();
                    if (!Pattern.compile("(\\w+)(,\\s*\\w+)*").matcher(feedSearchKeywords).matches()) {
                        Toast.makeText(getContext(), "Search keywords should be alphanumeric and separated by comma",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String[] searchKeywordArray = feedSearchKeywords.split("\\s*,\\s*");
                    for (String str : searchKeywordArray)
                        searchKeywordMap.put(str, true);
                }

                mAuthorName = mDatamanager.getUserName();
                //creating a Feed object
                if (mAuthorName != null) {
                    Feed feed = new Feed(
                            "https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg",
                            mAuthorName,
                            null,
                            (mDownloadUri != null && !Uri.EMPTY.equals(mDownloadUri))? mDownloadUri.toString():
                                    "https://c.tadst.com/gfx/750w/world-post-day.jpg?1",
                            editTextFeedDescription.getText().toString(),
                            searchKeywordMap,
                            mAuth.getCurrentUser().getEmail());


                    mFirebaseFirestore.collection(Constants.FEED_COLLECTION)
                            .add(feed)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    Toast.makeText(getContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
                                    if (getFragmentManager() != null)
                                        getFragmentManager().popBackStackImmediate();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(getContext(),
                                            "Internal error, please try after some time", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mSelectedImageUri != null){
            outState.putString("selectedImage", mSelectedImageUri.getLastPathSegment());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(mSelectedImageUri != null)
            btSelectImage.setText(savedInstanceState.get("selectedImage").toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FEED_PICTURE) {
                mSelectedImageUri = data.getData();
                Log.i(TAG, "Uri is " + mSelectedImageUri);
                btSelectImage.setText(mSelectedImageUri.getLastPathSegment());
            }
        }
    }
}