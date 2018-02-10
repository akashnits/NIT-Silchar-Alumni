package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.AlumniAdapter;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerMenuItem;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.akash.android.nitsilcharalumni.utils.MyDrawerLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniFragment extends Fragment implements AlumniAdapter.OnAlumniClickHandler,
        DrawerMenuItem.OnClickMenuItemHandler {


    @BindView(R.id.toolbarAlumni)
    Toolbar toolbarAlumni;
    @BindView(R.id.drawerViewAlumni)
    PlaceHolderView drawerViewAlumni;
    @BindView(R.id.drawerLayoutAlumni)
    MyDrawerLayout drawerLayoutAlumni;
    @BindView(R.id.rvAlumni)
    RecyclerView rvAlumni;
    @BindView(R.id.alumniFragment)
    RelativeLayout alumniFragment;
    Unbinder unbinder;
    @BindView(R.id.llAlumniFragment)
    LinearLayout llAlumniFragment;
    @BindView(R.id.pbAlumniFragment)
    ProgressBar pbAlumniFragment;


    private Context mContext;
    public static final int ALUMNI_LOADER_ID = 1;
    private FirebaseFirestore mFirestore;
    private DocumentSnapshot mLastVisible = null;
    private static final long LIMIT = 8;
    private AlumniAdapter mAlumniAdapter;
    private boolean isLoading;
    private int mLastDocumentSnapshotSize;


    public AlumniFragment() {
        // Required empty public constructor
    }

    public static AlumniFragment newInstance() {
        AlumniFragment fragment = new AlumniFragment();
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
        View view = inflater.inflate(R.layout.fragment_alumni, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarAlumni);
        setupDrawer();

        //first time load
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAlumni.setLayoutManager(lm);
        rvAlumni.hasFixedSize();
        mAlumniAdapter = new AlumniAdapter(mContext, this);
        rvAlumni.setAdapter(mAlumniAdapter);

        if(savedInstanceState == null){
            pbAlumniFragment.setVisibility(View.VISIBLE);
            isLoading= true;

            final List<User> newAlumni= new ArrayList<>();
            mFirestore.collection(Constants.USER_COLLECTION)
                    .whereEqualTo("mTypeOfUser", "Alumni")
                    .orderBy("mEmail")
                    .limit(LIMIT)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            mLastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);
                            mLastDocumentSnapshotSize= documentSnapshots.size();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newAlumni.add(documentSnapshot.toObject(User.class));
                            mAlumniAdapter.addAll(newAlumni);
                            pbAlumniFragment.setVisibility(View.INVISIBLE);
                            isLoading= false;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                            pbAlumniFragment.setVisibility(View.INVISIBLE);
                            isLoading= false;
                        }
                    });
        }

        rvAlumni.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisibleItem =((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findFirstVisibleItemPosition();
                    if ((LIMIT + pastVisibleItem) >= totalItemCount && !isLoading && mLastDocumentSnapshotSize == LIMIT) {
                        isLoading= true;
                        loadMore();
                    }
                }
            }
        });
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

    public void onAlumniClicked(int position, View view) {
        ((MainActivity) getActivity()).commitAlumniDetailsFragment();
    }

    private void loadMore(){
        pbAlumniFragment.setVisibility(View.VISIBLE);
        isLoading= true;

        final List<User> newAlumni= new ArrayList<>();
        mFirestore.collection(Constants.USER_COLLECTION)
                .whereEqualTo("mTypeOfUser", "Alumni")
                .orderBy("mEmail")
                .startAfter(mLastVisible)
                .limit(LIMIT)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                        isLoading= false;
                        if (!documentSnapshots.isEmpty()) {
                            mLastDocumentSnapshotSize= documentSnapshots.size();
                            mLastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);
                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                newAlumni.add(documentSnapshot.toObject(User.class));
                            mAlumniAdapter.addAll(newAlumni);
                        }else {
                            mLastDocumentSnapshotSize=0;
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                        isLoading= false;
                    }
                });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
        {
            List<User> alumniList= savedInstanceState.getParcelableArrayList("alumni");
            mAlumniAdapter.addAll(alumniList);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("position");
            rvAlumni.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("alumni", mAlumniAdapter.getmAlumniList());
        outState.putParcelable("position", rvAlumni.getLayoutManager().onSaveInstanceState());
    }



    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alumnimenu, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchAlumni);
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

                //TODO: Search for the newText in the list of alumni and update the list
                //TODO: set the new list on adapter and notify

                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                setItemsVisibility(menu, searchItem, false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setItemsVisibility(menu, searchItem, true);
                //TODO: set the whole list (without any filter) on adapter and notify
                return true;

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.filter:
                ((MainActivity) getActivity()).commitFilterAlumniFragment();
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
        drawerViewAlumni
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_RATE_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_CONTACT_US, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, mainActivity, this))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_DEVELOPED_BY, mainActivity, this));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle((MainActivity) getActivity(),
                drawerLayoutAlumni, toolbarAlumni,
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

        drawerLayoutAlumni.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onClickMenuItemListener() {
        drawerLayoutAlumni.closeDrawers();
    }
}
