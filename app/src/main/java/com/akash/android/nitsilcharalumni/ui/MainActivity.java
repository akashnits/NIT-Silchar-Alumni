package com.akash.android.nitsilcharalumni.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

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

}
