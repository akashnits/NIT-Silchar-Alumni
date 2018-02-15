package com.akash.android.nitsilcharalumni.ui.feed;


import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.Feed;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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



    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mAuth;
    private User mAuthor;

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
        mAuth = FirebaseAuth.getInstance();
        downloadAuthorData();
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

    @OnClick({R.id.uploadFab, R.id.btPost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uploadFab:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        fabProgressCircle.show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        fabProgressCircle.beginFinalAnimation();
                    }
                }.execute();
                break;
            case R.id.btPost:
                List<String> searchKeywordList = new ArrayList<>();
                if (TextUtils.isEmpty(editTextFeedDescription.getText())) {
                    Toast.makeText(getContext(), "Please enter a brief decription about the post",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //getting the search keywords
                if (!TextUtils.isEmpty(editTextFeedKeywords.getText())) {
                    String feedSearchKeywords = editTextFeedKeywords.getText().toString().trim();
                    if (!Pattern.compile("(\\w+)(,\\s*\\w+)*").matcher(feedSearchKeywords).matches()) {
                        Toast.makeText(getContext(), "Search keywords should be alphanumeric and separated by comma",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    searchKeywordList = Arrays.asList(feedSearchKeywords.split("\\s*,\\s*"));
                    for (String str : searchKeywordList)
                        Log.v(TAG, "Keyword: " + str);
                }

                //creating a Feed object
                if (mAuthor != null) {
                    Feed feed = new Feed("https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg",
                            mAuthor.getmName(),
                            null,
                            "https://c.tadst.com/gfx/750w/world-post-day.jpg?1",
                            editTextFeedDescription.getText().toString(),
                            searchKeywordList,
                            mAuthor.getmEmail());



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

    private void downloadAuthorData() {
        String authorEmail = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : null;
        if (authorEmail != null) {
            //get user's display name
            mFirebaseFirestore.collection(Constants.USER_COLLECTION)
                    .document(authorEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    mAuthor = document.toObject(User.class);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }
    }
}
