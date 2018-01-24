package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniDetailsFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.alumniDetailsLinearlayout)
    LinearLayout alumniDetailsLinearlayout;
    @BindView(R.id.alumniDetailsFramelayout)
    FrameLayout alumniDetailsFramelayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    Unbinder unbinder;
    @BindView(R.id.tvAboutYou)
    TextView tvAboutYou;
    @BindView(R.id.tvAboutYouText)
    TextView tvAboutYouText;
    @BindView(R.id.tvClassOf)
    TextView tvClassOf;
    @BindView(R.id.tvClassofText)
    TextView tvClassofText;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvLocationText)
    TextView tvLocationText;
    @BindView(R.id.tvContact)
    TextView tvContact;
    @BindView(R.id.tvContactText)
    TextView tvContactText;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvEmailText)
    TextView tvEmailText;
    @BindView(R.id.tvOrganisation)
    TextView tvOrganisation;
    @BindView(R.id.tvOrganisationText)
    TextView tvOrganisationText;
    @BindView(R.id.tvDesignation)
    TextView tvDesignation;
    @BindView(R.id.tvDesignationText)
    TextView tvDesignationText;
    @BindView(R.id.tvSkills)
    TextView tvSkills;
    @BindView(R.id.tvSkillsText)
    TextView tvSkillsText;
    @BindView(R.id.toolbarAlumniDetails)
    Toolbar toolbarAlumniDetails;


    private Context mContext;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    public AlumniDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AlumniDetailsFragment newInstance() {
        AlumniDetailsFragment fragment = new AlumniDetailsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumni_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(tvTitle, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(tvTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(tvTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(alumniDetailsLinearlayout, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(alumniDetailsLinearlayout, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
