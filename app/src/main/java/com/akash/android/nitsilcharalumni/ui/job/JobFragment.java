package com.akash.android.nitsilcharalumni.ui.job;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.akash.android.nitsilcharalumni.adapter.JobAdapter;
import com.akash.android.nitsilcharalumni.model.Job;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerMenuItem;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.akash.android.nitsilcharalumni.utils.MyDrawerLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class JobFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        DrawerMenuItem.OnClickMenuItemHandler {

    @BindView(R.id.rvJob)
    RecyclerView rvJob;
    @BindView(R.id.jobFragment)
    RelativeLayout jobFragment;
    @BindView(R.id.jobFab)
    FloatingActionButton jobFab;
    @BindView(R.id.swipe_refresh_layout_job)
    SwipeRefreshLayout swipeRefreshLayoutJob;
    Unbinder unbinder;
    @BindView(R.id.toolbarJob)
    Toolbar toolbarJob;
    @BindView(R.id.drawerViewJob)
    PlaceHolderView drawerViewJob;
    @BindView(R.id.drawerLayoutJob)
    MyDrawerLayout drawerLayoutJob;
    @BindView(R.id.pbJobFragment)
    ProgressBar pbJobFragment;

    private Context mContext;
    private JobAdapter mJobAdapter;
    private FirebaseFirestore mFirestore;
    private boolean mIsLoading;
    private static final long LIMIT = 4;
    private DocumentSnapshot mDocumentAtFirstPosition= null;
    private DocumentSnapshot mLastVisible = null;
    private int mLastDocumentSnapshotSize;
    private Job[] mInitialArray;
    private SearchView mSearchView;
    private String mSearchString;

    public JobFragment() {
        // Required empty public constructor
    }

    public static JobFragment newInstance() {
        JobFragment fragment = new JobFragment();
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
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarJob);

        setupDrawer();
        swipeRefreshLayoutJob.setOnRefreshListener(this);

        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvJob.setLayoutManager(lm);
        rvJob.hasFixedSize();
        rvJob.addItemDecoration(new DividerItemDecoration(rvJob.getContext(),
                DividerItemDecoration.VERTICAL));
        mJobAdapter = new JobAdapter(mContext);
        rvJob.setAdapter(mJobAdapter);

        if (savedInstanceState == null) {
            loadInitial();
        }

        rvJob.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        pbJobFragment.setVisibility(View.VISIBLE);
        mIsLoading = true;

        final List<Job> newJob = new ArrayList<>();
        mFirestore.collection(Constants.JOB_COLLECTION)
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
                                newJob.add(documentSnapshot.toObject(Job.class));
                            mJobAdapter.addAll(newJob);
                        }
                        if (pbJobFragment != null)
                            pbJobFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        if (pbJobFragment != null)
                            pbJobFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                });
    }

    private void loadMore() {
        pbJobFragment.setVisibility(View.VISIBLE);
        mIsLoading = true;

        final List<Job> newJob = new ArrayList<>();
        mFirestore.collection(Constants.JOB_COLLECTION)
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
                                newJob.add(documentSnapshot.toObject(Job.class));
                            mJobAdapter.addAll(newJob);
                        } else {
                            mLastDocumentSnapshotSize = 0;
                        }
                        if (pbJobFragment != null)
                            pbJobFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        if (pbJobFragment != null)
                            pbJobFragment.setVisibility(View.INVISIBLE);
                        mIsLoading = false;
                    }
                });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            List<Job> jobList = savedInstanceState.getParcelableArrayList("job");
            if(jobList != null && jobList.size() > 0)
                mJobAdapter.addAll(jobList);
            else
                loadInitial();
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("position");
            if(rvJob != null)
                rvJob.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            mSearchString= savedInstanceState.getString("searchJob");
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getUserVisibleHint() && isVisible())
            outState.putParcelableArrayList("job", mJobAdapter.getmJobList());
        if(rvJob != null)
            outState.putParcelable("position", rvJob.getLayoutManager().onSaveInstanceState());
        if(mSearchView != null && !TextUtils.isEmpty(mSearchView.getQuery()))
            outState.putString("searchJob", mSearchView.getQuery().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
       reload();
    }

    private void reload() {
        mIsLoading = true;
        final List<Job> newJob = new ArrayList<>();
        mFirestore.collection(Constants.JOB_COLLECTION)
                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                .endBefore(mDocumentAtFirstPosition)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {

                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            mDocumentAtFirstPosition= documentSnapshots.getDocuments().get(0);
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newJob.add(documentSnapshot.toObject(Job.class));
                            mJobAdapter.addAllAtStart(newJob);
                            Toast.makeText(mContext, "Refreshed", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayoutJob.setRefreshing(false);
                        }else {
                            Toast.makeText(mContext, "No new jobs", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayoutJob.setRefreshing(false);
                        }
                        mIsLoading = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayoutJob.setRefreshing(false);
                        mIsLoading = false;
                    }
                });

    }


    @OnClick({R.id.rvJob, R.id.jobFragment, R.id.jobFab, R.id.swipe_refresh_layout_job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rvJob:
                break;
            case R.id.jobFragment:
                break;
            case R.id.jobFab:
                ActivityUtils.replaceFragmentOnActivity(getFragmentManager(),
                        CreateJobFragment.newInstance(),
                        R.id.content,
                        true,
                        "CreateJobFragment", R.anim.enter_from_right,
                        R.anim.exit_to_left);
                break;
            case R.id.swipe_refresh_layout_job:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.jobmenu, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchJob);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("Search...");

        EditText etSearch = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        etSearch.setHintTextColor(Color.DKGRAY);
        etSearch.setTextColor(Color.WHITE);

        if (!TextUtils.isEmpty(mSearchString)) {
            searchItem.expandActionView();
            mSearchView.setQuery(mSearchString, true);
            mSearchView.clearFocus();
            mSearchView.setMaxWidth( Integer.MAX_VALUE );
            menu.findItem(R.id.jobFilter).setVisible(false);
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //TODO: Search for the newText in the list of job and update the list
                //TODO: set the new list on adapter and notify

                if (!TextUtils.isEmpty(newText)) {
                    final List<Job> searchedJob = new ArrayList<Job>();
                    String whereCondition = String.format("%s.%s", "mJobSearchKeywordsMap", newText);
                    mFirestore.collection(Constants.JOB_COLLECTION)
                            .whereEqualTo(whereCondition, true)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            searchedJob.add(documentSnapshot.toObject(Job.class));
                                        mJobAdapter.addAsPerSearch(searchedJob);
                                    }else {
                                        mJobAdapter.setEmptyView();
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
                setItemsVisibility(menu, searchItem, false);
                List<Job> initialJobList= mJobAdapter.getmJobList();
                mInitialArray = initialJobList.toArray(new Job[initialJobList.size()]);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setItemsVisibility(menu, searchItem, true);
                //TODO: set the whole list (without any filter) on adapter and notify
                List<Job> jobList=  mJobAdapter.getmJobList();
                int currentSize = jobList != null ? jobList.size():0;
                //remove the current items
                mJobAdapter.replaceWithInitialList(mInitialArray, currentSize);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.jobFilter:
                ((MainActivity) getActivity()).commitFilterJobFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    private void setupDrawer() {
        MainActivity mainActivity = (MainActivity) getActivity();

        drawerViewJob
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_RATE_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_CONTACT_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_DEVELOPED_BY, mainActivity, this));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle((MainActivity) getActivity(),
                drawerLayoutJob, toolbarJob,
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

        drawerLayoutJob.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onClickMenuItemListener() {
        drawerLayoutJob.closeDrawers();
    }
}
