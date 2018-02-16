package com.akash.android.nitsilcharalumni.ui.job;


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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    EditText editTextPostJobOrganiation;
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

    private CreateJobFragmentComponent createJobFragmentComponent;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mAuth;
    private String mAuthorName;

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
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getCreateJobFragmentComponent().inject(this);
    }

    public CreateJobFragmentComponent getCreateJobFragmentComponent(){
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
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onFABProgressAnimationEnd() {
        Snackbar.make(postJobFabProgressCircle, "Upload Complete", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    @OnClick({R.id.postJobUploadFab, R.id.btPostJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.postJobUploadFab:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        postJobFabProgressCircle.show();
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
                        postJobFabProgressCircle.beginFinalAnimation();
                    }
                }.execute();
                break;
            case R.id.btPostJob:
                List<String> searchKeywordList = new ArrayList<>();
                if (TextUtils.isEmpty(editTextPostJobKeywords.getText())) {
                    Toast.makeText(getContext(), "Please enter a brief decription about the post",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //getting the search keywords
                if (!TextUtils.isEmpty(editTextPostJobKeywords.getText())) {
                    String jobSearchKeywords = editTextPostJobKeywords.getText().toString().trim();
                    if (!Pattern.compile("(\\w+)(,\\s*\\w+)*").matcher(jobSearchKeywords).matches()) {
                        Toast.makeText(getContext(), "Search keywords should be alphanumeric and separated by comma",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    searchKeywordList = Arrays.asList(jobSearchKeywords.split("\\s*,\\s*"));
                }

                mAuthorName= mDataManager.getUserName();
                //creating a job object
                if (mAuthorName != null) {
                    Job job = new Job(mAuthorName,
                            "https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg",
                            null,
                            editTextPostJobTitle.getText().toString(),
                            editTextPostJobLocation.getText().toString(),
                            editTextPostJobOrganiation.getText().toString(),
                            editTextPostJobDescription.getText().toString(),
                            "https://c.tadst.com/gfx/750w/world-post-day.jpg?1",
                            searchKeywordList);



                    mFirebaseFirestore.collection(Constants.JOB_COLLECTION)
                            .add(job)
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
}
