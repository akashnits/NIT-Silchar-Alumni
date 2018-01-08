package com.akash.android.nitsilcharalumni.ui.job;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.github.jorgecastilloprz.FABProgressCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJobFragment extends Fragment {


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
    @BindView(R.id.postJobFabProgressCircle)
    FABProgressCircle postJobFabProgressCircle;
    @BindView(R.id.postJobfabLayout)
    RelativeLayout postJobfabLayout;
    @BindView(R.id.btPostJob)
    Button btPostJob;
    Unbinder unbinder;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
