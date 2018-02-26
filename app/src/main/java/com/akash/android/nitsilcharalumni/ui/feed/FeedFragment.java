package com.akash.android.nitsilcharalumni.ui.feed;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.FeedAdapter;
import com.akash.android.nitsilcharalumni.data.FeedContract;
import com.akash.android.nitsilcharalumni.model.Feed;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerMenuItem;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mindorks.placeholderview.PlaceHolderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        FeedAdapter.OnBookmarkClickedHandler, DrawerMenuItem.OnClickMenuItemHandler {

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
    @BindView(R.id.drawerViewHome)
    PlaceHolderView drawerView;
    @BindView(R.id.drawerLayoutHome)
    DrawerLayout drawerLayout;
    @BindView(R.id.pbFeedFragment)
    ProgressBar pbFeedFragment;

    private FeedAdapter mFeedAdapter;
    private Context mContext;
    private boolean isBookmarked;
    private FirebaseFirestore mFirestore;
    private DocumentSnapshot mLastVisible = null;
    private static final long LIMIT = 4;
    private boolean mIsLoading;
    private int mLastDocumentSnapshotSize;
    private DocumentSnapshot mDocumentAtFirstPosition = null;
    private Feed[] mInitialArray;
    private SearchView mSearchView;
    private String mSearchString;

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
        mFirestore = FirebaseFirestore.getInstance();
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
        setupDrawer();

        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvFeed.setLayoutManager(lm);
        rvFeed.hasFixedSize();
        mFeedAdapter = new FeedAdapter(mContext, this);
        rvFeed.setAdapter(mFeedAdapter);

        if (savedInstanceState == null) {
            loadInitial();
        }

        rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findFirstVisibleItemPosition();
                    if ((LIMIT + pastVisibleItem) >= totalItemCount && !mIsLoading && mLastDocumentSnapshotSize == LIMIT) {
                        loadMore();
                    }
                }
            }
        });
    }


    private void loadInitial(){
        pbFeedFragment.setVisibility(View.VISIBLE);
        mIsLoading = true;

        final List<Feed> newFeed = new ArrayList<>();
        mFirestore.collection(Constants.FEED_COLLECTION)
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .limit(LIMIT)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            mDocumentAtFirstPosition = documentSnapshots.getDocuments().get(0);
                            mLastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);
                            mLastDocumentSnapshotSize = documentSnapshots.size();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newFeed.add(documentSnapshot.toObject(Feed.class));
                            mFeedAdapter.addAll(newFeed);
                        }
                        if (pbFeedFragment != null)
                            pbFeedFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        if (pbFeedFragment != null)
                            pbFeedFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                });
    }

    private void loadMore() {
        pbFeedFragment.setVisibility(View.VISIBLE);
        mIsLoading = true;

        final List<Feed> newFeed = new ArrayList<>();
        mFirestore.collection(Constants.FEED_COLLECTION)
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .startAfter(mLastVisible)
                .limit(LIMIT)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            mLastDocumentSnapshotSize = documentSnapshots.size();
                            mLastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newFeed.add(documentSnapshot.toObject(Feed.class));
                            mFeedAdapter.addAll(newFeed);
                        } else {
                            mLastDocumentSnapshotSize = 0;
                        }
                        if (pbFeedFragment != null)
                            pbFeedFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        if (pbFeedFragment != null)
                            pbFeedFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            List<Feed> feedList = savedInstanceState.getParcelableArrayList("feed");
            if(feedList != null && feedList.size() > 0)
                mFeedAdapter.addAll(feedList);
            else
                loadInitial();
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("position");
            if(rvFeed != null)
                rvFeed.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            mSearchString= savedInstanceState.getString("searchFeed");
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getUserVisibleHint())
            outState.putParcelableArrayList("feed", mFeedAdapter.getmFeedList());
        if(rvFeed != null)
            outState.putParcelable("position", rvFeed.getLayoutManager().onSaveInstanceState());
        if(mSearchView != null && !TextUtils.isEmpty(mSearchView.getQuery()))
            outState.putString("searchFeed", mSearchView.getQuery().toString());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onRefresh() {
        reload();
    }

    private void reload() {
        mIsLoading = true;
        final List<Feed> newFeed = new ArrayList<>();
        mFirestore.collection(Constants.FEED_COLLECTION)
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .endBefore(mDocumentAtFirstPosition)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {

                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            mDocumentAtFirstPosition = documentSnapshots.getDocuments().get(0);
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newFeed.add(documentSnapshot.toObject(Feed.class));
                            mFeedAdapter.addAllAtStart(newFeed);
                            Toast.makeText(mContext, "Refreshed", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(mContext, "No new feed", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                        mIsLoading = false;
                    }
                });

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
                "CreateFeedFragment", R.anim.enter_from_right,
                R.anim.exit_to_left);
    }

    @Override
    public void onBookmarkClicked(int position) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = FeedContract.FeedEntry.CONTENT_URI;

        isBookmarked = getBookmarkStatus(uri, resolver, position);

        if (!isBookmarked) {
            Feed feed = mFeedAdapter.getFeedObjectAtPosition(position);
            ContentValues cv = new ContentValues();
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_ID, position);
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_IMAGE_URL,
                    feed.getmFeedImageUrl());
            cv.put(FeedContract.FeedEntry.COLUMN_PROFILE_IMAGE_URL,
                    feed.getmAuthorImageUrl());
            cv.put(FeedContract.FeedEntry.COLUMN_PROFILE_NAME, feed.getmAuthorName());
            cv.put(FeedContract.FeedEntry.COLUMN_FEED_DESCRIPTION,
                    feed.getmFeedDescription());

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            String strDate = formatter.format(feed.getmTimestamp());

            cv.put(FeedContract.FeedEntry.COLUMN_FEED_TIMESTAMP,
                    strDate);

            String strSearchKeyword = "";
            for (String key : feed.getmFeedSearchKeywordsMap().keySet())
                strSearchKeyword = strSearchKeyword.concat("#").concat(key).concat(" ");

            cv.put(FeedContract.FeedEntry.COLUMN_FEED_HASHTAG,
                    strSearchKeyword);
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

        MenuItem searchItem = menu.findItem(R.id.searchFeed);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("Search...");

        EditText etSearch = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        etSearch.setHintTextColor(Color.DKGRAY);
        etSearch.setTextColor(Color.WHITE);


        if (!TextUtils.isEmpty(mSearchString)) {
            searchItem.expandActionView();
            mSearchView.setQuery(mSearchString, true);
            mSearchView.clearFocus();
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    final List<Feed> searchedFeed = new ArrayList<Feed>();
                    String whereCondition = String.format("%s.%s", "mFeedSearchKeywordsMap", newText);
                    mFirestore.collection(Constants.FEED_COLLECTION)
                            .whereEqualTo(whereCondition, true)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            searchedFeed.add(documentSnapshot.toObject(Feed.class));
                                        mFeedAdapter.addAsPerSearch(searchedFeed);
                                    }else {
                                        mFeedAdapter.setEmptyView();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mContext, "Failed to search", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //save the current list
                List<Feed> initialFeedList= mFeedAdapter.getmFeedList();
                mInitialArray = initialFeedList.toArray(new Feed[initialFeedList.size()]);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //TODO: set the whole list (without any filter) on adapter and notify
                List<Feed> feedList= mFeedAdapter.getmFeedList();
                int currentSize = feedList != null ? feedList.size():0;
                //remove the current items
                    mFeedAdapter.replaceWithInitialList(mInitialArray, currentSize);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupDrawer() {
        MainActivity mainActivity = (MainActivity) getActivity();
        drawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_RATE_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_CONTACT_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_DEVELOPED_BY, mainActivity, this));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle((MainActivity) getActivity(),
                drawerLayout, toolbarHome,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onClickMenuItemListener() {
        drawerLayout.closeDrawers();
    }
}
