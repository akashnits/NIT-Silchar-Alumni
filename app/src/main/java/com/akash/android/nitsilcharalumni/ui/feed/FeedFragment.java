package com.akash.android.nitsilcharalumni.ui.feed;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.FeedAdapter;
import com.akash.android.nitsilcharalumni.data.FeedContract;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FeedAdapter.OnBookmarkClickedHandler {

    public static final String TAG = FeedFragment.class.getSimpleName();

    @BindView(R.id.swipe_refresh_layout_home)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.postFab)
    FloatingActionButton postFab;
    @BindView(R.id.toolbarHome)
    Toolbar toolbarHome;
    @BindView(R.id.homeFragment)
    RelativeLayout homeFragment;

    private FeedAdapter mFeedAdapter;
    private Context mContext;
    private boolean isBookmarked;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarHome);


        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvFeed.setLayoutManager(lm);
        rvFeed.hasFixedSize();
        mFeedAdapter = new FeedAdapter(mContext, this);
        rvFeed.setAdapter(mFeedAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "Refreshed", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.postFab)
    public void onViewClicked() {
        ActivityUtils.replaceFragmentOnActivity(getFragmentManager(),
                CreateFeedFragment.newInstance(),
                R.id.content,
                true,
                "CreateFeedFragment");
    }

    @Override
    public void onBookmarkClicked(int position) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = FeedContract.FeedEntry.CONTENT_URI;

        isBookmarked = getBookmarkStatus(uri, resolver, position);

        if (!isBookmarked) {
            ContentValues cv = new ContentValues();
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_ID, position);
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_IMAGE_URL,
                    "https://c.tadst.com/gfx/750w/world-post-day.jpg?1");
            cv.put(FeedContract.FeedEntry.COLUMN_PROFILE_IMAGE_URL,
                    "https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg");
            cv.put(FeedContract.FeedEntry.COLUMN_PROFILE_NAME, "Akash Gupta");
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_DESCRIPTION,
                    "Learning android is fun. You can turn your ideas into reality and let the world know");
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_TIMESTAMP,
                    "July 6, 2017");
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_HASHTAG,
                    "#android #learning #event");
            Uri returnedUri = resolver.insert(uri, cv);
            Log.v(TAG, "Inserted uri: " + returnedUri);
            isBookmarked = true;
        } else {
            int numberOfRows = resolver.delete(uri, FeedContract.FeedEntry.COLUMN_FEED_ID + "=?",
                    new String[]{String.valueOf(position)});
            Log.v(TAG, "Rows deleted " + numberOfRows);
            isBookmarked = false;
        }

    }

    public static boolean getBookmarkStatus(Uri uri, ContentResolver resolver, int position) {
        Cursor cursor = resolver.query(uri, null, FeedContract.FeedEntry.COLUMN_FEED_ID +
                "=?", new String[]{String.valueOf(position)}, null);
        if (cursor != null && cursor.getCount() != 0)
            return true;
        else
            return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionmenu, menu);

        final MenuItem searchItem = menu.findItem(R.id.searchFeed);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search...");

        EditText etSearch = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        etSearch.setHintTextColor(Color.DKGRAY);
        etSearch.setTextColor(Color.WHITE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //TODO: Search for the newText in the list of feeds and update the list
                //TODO: set the new list on adapter and notify

                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                //TODO: set the whole list (without any filter) on adapter and notify
                return true;

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
