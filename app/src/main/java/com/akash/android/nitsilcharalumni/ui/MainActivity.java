package com.akash.android.nitsilcharalumni.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniDetailsFragment;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniFragment;
import com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment;
import com.akash.android.nitsilcharalumni.ui.bookmark.BookmarkFragment;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerMenuItem;
import com.akash.android.nitsilcharalumni.ui.drawer.profile.EditMyProfileFragment;
import com.akash.android.nitsilcharalumni.ui.drawer.profile.MyProfileFragment;
import com.akash.android.nitsilcharalumni.ui.feed.FeedFragment;
import com.akash.android.nitsilcharalumni.ui.job.FilterJobFragment;
import com.akash.android.nitsilcharalumni.ui.job.JobFragment;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.akash.android.nitsilcharalumni.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements DrawerMenuItem.DrawerCallBack {

    private FirebaseAuth mAuth;
    private FragmentManager mSupportFragmentManager;
    private boolean isLocationPreferenceChecked;
    private boolean isClassOfPreferenceChecked;
    private String mAlumniLocationConstraint;
    private String mAlumniClassOfConstraint;
    private boolean isJobLocationPreferenceChecked;
    private boolean isJobOrganisationPreferenceChecked;
    private String mJobLocationConstraint;
    private String mJobOrganisationConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSupportFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState == null) {
            ActivityUtils.replaceFragmentOnActivity(mSupportFragmentManager,
                    FeedFragment.newInstance(),
                    R.id.content,
                    false,
                    "Home",R.anim.enter_from_right,
                    R.anim.exit_to_left );
        }else {
            mAlumniLocationConstraint= savedInstanceState.getString("alumniLocationConstraint");
            mAlumniClassOfConstraint= savedInstanceState.getString("alumniClassOfConstraint");
            isLocationPreferenceChecked= savedInstanceState.getBoolean("alumniLocationPrefChecked");
            isClassOfPreferenceChecked= savedInstanceState.getBoolean("alumniClassOfPrefChecked");
            mJobLocationConstraint= savedInstanceState.getString("jobLocationConstraint");
            mJobOrganisationConstraint= savedInstanceState.getString("jobOrganisationConstraint");
            isJobLocationPreferenceChecked= savedInstanceState.getBoolean("jobLocationPrefChecked");
            isJobOrganisationPreferenceChecked = savedInstanceState.getBoolean("jobOrganisationPrefChecked");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    popingFragmentWhileNavigating();
                    ActivityUtils.replaceFragmentOnActivity(
                            mSupportFragmentManager,
                            FeedFragment.newInstance(),
                            R.id.content,
                            false,
                            "Home",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left);
                    return true;
                case R.id.navigation_alumni:
                    popingFragmentWhileNavigating();
                    ActivityUtils.replaceFragmentOnActivity(
                            mSupportFragmentManager,
                            AlumniFragment.newInstance(),
                            R.id.content,
                            false,
                            "Alumni",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
                case R.id.navigation_jobs:
                    popingFragmentWhileNavigating();
                    ActivityUtils.replaceFragmentOnActivity(
                            mSupportFragmentManager,
                            JobFragment.newInstance(),
                            R.id.content,
                            false,
                            "Job",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
                case R.id.navigation_bookmark:
                    popingFragmentWhileNavigating();
                    ActivityUtils.replaceFragmentOnActivity(
                            mSupportFragmentManager,
                            BookmarkFragment.newInstance(),
                            R.id.content,
                            false,
                            "Bookmark",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
            }
            return false;
        }

    };

    public void commitAlumniDetailsFragment(Bundle args){
        ActivityUtils.replaceFragmentOnActivity(
                mSupportFragmentManager,
                AlumniDetailsFragment.newInstance(args),
                R.id.content,
                true,
                "AlumniDetails",
                R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    public void commitFilterAlumniFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                mSupportFragmentManager,
                FilterAlumniFragment.newInstance(),
                R.id.content,
                true,
                "FilterAlumni",
                R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void commitFilterJobFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                mSupportFragmentManager,
                FilterJobFragment.newInstance(),
                R.id.content,
                true,
                "FilterJob",
                R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    private void commitMyProfileFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                mSupportFragmentManager,
                MyProfileFragment.newInstance(),
                R.id.content,
                true,
                "MyProfileFragment",
                R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    public void commitEditMyProfileFragment(Bundle args) {
        ActivityUtils.replaceFragmentOnActivity(
                mSupportFragmentManager,
                EditMyProfileFragment.newInstance(args),
                R.id.content,
                true,
                "EditMyProfileFragment",
                R.anim.enter_from_right,
                R.anim.exit_to_left
        );
    }

    @Override
    public void onProfileMenuSelected() {
        commitMyProfileFragment();
    }

    @Override
    public void onRateUsMenuSelected() {
        //TODO: Navigate to Play store
    }

    @Override
    public void onContactUsMenuSelected() {
        //TODO: Define a UI to contact
    }

    @Override
    public void onLogoutMenuSelected() {
        //TODO: Logout current user
        mAuth= FirebaseAuth.getInstance();
        if(mAuth != null)
            mAuth.signOut();
            finish();
    }

    @Override
    public void onDeveloperMenuSelected() {
        //Do nothing
    }

    private void popingFragmentWhileNavigating(){
        if(mSupportFragmentManager.getFragments() != null
                && !mSupportFragmentManager.getFragments().isEmpty()){
            mSupportFragmentManager.popBackStack();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        mSupportFragmentManager.popBackStack();
        return true;
    }

    public boolean isLocationPreferenceChecked() {
        return isLocationPreferenceChecked;
    }

    public void setLocationPreferenceChecked(boolean locationPreferenceChecked) {
        isLocationPreferenceChecked = locationPreferenceChecked;
    }

    public boolean isClassOfPreferenceChecked() {
        return isClassOfPreferenceChecked;
    }

    public void setClassOfPreferenceChecked(boolean classOfPreferenceChecked) {
        isClassOfPreferenceChecked = classOfPreferenceChecked;
    }

    public String getmAlumniLocationConstraint() {
        return mAlumniLocationConstraint;
    }

    public void setmAlumniLocationConstraint(String mAlumniLocationConstraint) {
        this.mAlumniLocationConstraint = mAlumniLocationConstraint;
    }

    public String getmAlumniClassOfConstraint() {
        return mAlumniClassOfConstraint;
    }

    public void setmAlumniClassOfConstraint(String mAlumniClassOfConstraint) {
        this.mAlumniClassOfConstraint = mAlumniClassOfConstraint;
    }

    public boolean isJobLocationPreferenceChecked() {
        return isJobLocationPreferenceChecked;
    }

    public void setJobLocationPreferenceChecked(boolean jobLocationPreferenceChecked) {
        isJobLocationPreferenceChecked = jobLocationPreferenceChecked;
    }

    public boolean isJobOrganisationPreferenceChecked() {
        return isJobOrganisationPreferenceChecked;
    }

    public void setJobOrganisationPreferenceChecked(boolean jobOrganisationPreferenceChecked) {
        isJobOrganisationPreferenceChecked = jobOrganisationPreferenceChecked;
    }

    public String getmJobLocationConstraint() {
        return mJobLocationConstraint;
    }

    public void setmJobLocationConstraint(String mJobLocationConstraint) {
        this.mJobLocationConstraint = mJobLocationConstraint;
    }

    public String getmJobOrganisationConstraint() {
        return mJobOrganisationConstraint;
    }

    public void setmJobOrganisationConstraint(String mJobOrganisationConstraint) {
        this.mJobOrganisationConstraint = mJobOrganisationConstraint;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("alumniLocationConstraint", mAlumniLocationConstraint);
        outState.putString("alumniClassOfConstraint", mAlumniClassOfConstraint);
        outState.putBoolean("alumniLocationPrefChecked", isLocationPreferenceChecked);
        outState.putBoolean("alumniClassOfPrefChecked", isClassOfPreferenceChecked);
        outState.putString("jobLocationConstraint", mJobLocationConstraint);
        outState.putString("jobOrganisationConstraint", mJobOrganisationConstraint);
        outState.putBoolean("jobLocationPrefChecked", isJobLocationPreferenceChecked);
        outState.putBoolean("jobOrganisationPrefChecked", isJobOrganisationPreferenceChecked);
    }
}
