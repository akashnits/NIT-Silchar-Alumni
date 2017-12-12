package com.akash.android.nitsilcharalumni.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniDetailsFragment;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniFragment;
import com.akash.android.nitsilcharalumni.ui.bookmark.BookmarkFragment;
import com.akash.android.nitsilcharalumni.ui.feed.FeedFragment;
import com.akash.android.nitsilcharalumni.ui.job.JobFragment;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.akash.android.nitsilcharalumni.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {




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
                            "Home"
                    );
                    return true;
                case R.id.navigation_alumni:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            AlumniFragment.newInstance(),
                            R.id.content,
                            false,
                            "Alumni"
                    );
                    return true;
                case R.id.navigation_jobs:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            JobFragment.newInstance(),
                            R.id.content,
                            false,
                            "Job"
                    );
                    return true;
                case R.id.navigation_bookmark:
                    ActivityUtils.replaceFragmentOnActivity(
                            getSupportFragmentManager(),
                            BookmarkFragment.newInstance(),
                            R.id.content,
                            false,
                            "Bookmark"
                    );
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
                "AlumniDetails"
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
                    "Home");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
