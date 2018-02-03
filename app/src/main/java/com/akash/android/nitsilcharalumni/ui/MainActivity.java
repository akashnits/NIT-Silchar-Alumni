package com.akash.android.nitsilcharalumni.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

public class MainActivity extends AppCompatActivity implements DrawerMenuItem.DrawerCallBack {




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            FeedFragment.newInstance(),
                            R.id.content,
                            false,
                            "Home",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
                case R.id.navigation_alumni:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            AlumniFragment.newInstance(),
                            R.id.content,
                            false,
                            "Alumni",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
                case R.id.navigation_jobs:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            JobFragment.newInstance(),
                            R.id.content,
                            false,
                            "Job",
                            R.anim.enter_from_right,
                            R.anim.exit_to_left );
                    return true;
                case R.id.navigation_bookmark:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
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

    public void commitAlumniDetailsFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                getSupportFragmentManager(),
                AlumniDetailsFragment.newInstance(),
                R.id.content,
                true,
                "AlumniDetails",
                R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void commitFilterAlumniFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                getSupportFragmentManager(),
                FilterAlumniFragment.newInstance(),
                R.id.content,
                true,
                "FilterAlumni",
                R.anim.enter_from_right,
                R.anim.exit_to_left );
    }

    public void commitFilterJobFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                getSupportFragmentManager(),
                FilterJobFragment.newInstance(),
                R.id.content,
                true,
                "FilterJob",
                R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    private void commitMyProfileFragment(){
        ActivityUtils.replaceFragmentOnActivity(
                getSupportFragmentManager(),
                MyProfileFragment.newInstance(),
                R.id.content,
                true,
                "MyProfileFragment",
                R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    public void commitEditMyProfileFragment(Bundle args) {
        ActivityUtils.replaceFragmentOnActivity(
                getSupportFragmentManager(),
                EditMyProfileFragment.newInstance(args),
                R.id.content,
                true,
                "EditMyProfileFragment",
                R.anim.enter_from_right,
                R.anim.exit_to_left
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState == null) {
            ActivityUtils.replaceFragmentOnActivity(getSupportFragmentManager(),
                    FeedFragment.newInstance(),
                    R.id.content,
                    false,
                    "Home",R.anim.enter_from_right,
                    R.anim.exit_to_left );
        }
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
    }

    @Override
    public void onDeveloperMenuSelected() {
        //Do nothing
    }
}
