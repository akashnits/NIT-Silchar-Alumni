package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
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

import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_CLASS_OF;
import static com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment.ALUMNI_LOCATION;

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
    private FirebaseFirestore mFirestore;
    private DocumentSnapshot mLastVisible = null;
    private static final long LIMIT = 8;
    private AlumniAdapter mAlumniAdapter;
    private boolean isLoading;
    private int mLastDocumentSnapshotSize;
    private User[] mInitialArray;
    private SearchView mSearchView;
    private String mSearchString;
    private SharedPreferences mSharedPreferences;
    private String[] mAlumniLocationArray;
    private String[] mAlumniClassOfArray;
    private static boolean isFilterApplied;
    private String mLocationFilter;
    private String mClassOfFilter;

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
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mAlumniLocationArray = getResources().getStringArray(R.array.location);
        mAlumniClassOfArray = getResources().getStringArray(R.array.classOfFilter);
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

        if (savedInstanceState == null) {
            pbAlumniFragment.setVisibility(View.VISIBLE);
            isLoading = true;

            final List<User> newAlumni = new ArrayList<>();
            if (!isFilterApplied) {
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
                                mLastDocumentSnapshotSize = documentSnapshots.size();
                                for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                    newAlumni.add(documentSnapshot.toObject(User.class));
                                mAlumniAdapter.addAll(newAlumni);
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                            }
                        });
            } else {
                for (String location : mAlumniLocationArray) {
                    String key = String.format("%s_%s", ALUMNI_LOCATION, location);
                    if (mSharedPreferences.getBoolean(key, false)) {
                        mLocationFilter = location;
                    }
                }

                for (String classOf : mAlumniClassOfArray) {
                    String key = String.format("%s_%s", ALUMNI_CLASS_OF, classOf);
                    if (mSharedPreferences.getBoolean(key, false)) {
                        mClassOfFilter = classOf;
                    }
                }

                if (mLocationFilter != null && mClassOfFilter != null) {
                    mFirestore.collection(Constants.USER_COLLECTION)
                            .whereEqualTo("mTypeOfUser", "Alumni")
                            .orderBy("mEmail")
                            .limit(LIMIT)
                            .whereEqualTo("mLocation", mLocationFilter)
                            .whereEqualTo("mClassOf", mClassOfFilter)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        mLastDocumentSnapshotSize = documentSnapshots.size();
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            newAlumni.add(documentSnapshot.toObject(User.class));
                                        mAlumniAdapter.addAll(newAlumni);
                                    } else {
                                        Toast.makeText(mContext, "Nothing found", Toast.LENGTH_SHORT).show();
                                    }
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            });

                } else if (mLocationFilter != null) {
                    mFirestore.collection(Constants.USER_COLLECTION)
                            .whereEqualTo("mTypeOfUser", "Alumni")
                            .orderBy("mEmail")
                            .limit(LIMIT)
                            .whereEqualTo("mLocation", mLocationFilter)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        mLastDocumentSnapshotSize = documentSnapshots.size();
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            newAlumni.add(documentSnapshot.toObject(User.class));
                                        mAlumniAdapter.addAll(newAlumni);
                                    } else {
                                        Toast.makeText(mContext, "Nothing found", Toast.LENGTH_SHORT).show();
                                    }
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            });

                } else if (mClassOfFilter != null) {
                    mFirestore.collection(Constants.USER_COLLECTION)
                            .whereEqualTo("mTypeOfUser", "Alumni")
                            .orderBy("mEmail")
                            .limit(LIMIT)
                            .whereEqualTo("mClassOf", mClassOfFilter)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        mLastDocumentSnapshotSize = documentSnapshots.size();
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            newAlumni.add(documentSnapshot.toObject(User.class));
                                        mAlumniAdapter.addAll(newAlumni);
                                    } else {
                                        Toast.makeText(mContext, "Nothing found", Toast.LENGTH_SHORT).show();
                                    }
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    if (pbAlumniFragment != null)
                                        pbAlumniFragment.setVisibility(View.INVISIBLE);
                                    isLoading = false;
                                }
                            });
                }
            }
        }

        rvAlumni.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if ((LIMIT + pastVisibleItem) >= totalItemCount && !isLoading &&
                            mLastDocumentSnapshotSize == LIMIT) {
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
        isFilterApplied = false;
        unbinder.unbind();
    }

    public void onAlumniClicked(String email, View view) {
        Bundle b = new Bundle();
        b.putString("email", email);
        ((MainActivity) getActivity()).commitAlumniDetailsFragment(b);
    }

    private void loadMore() {
        pbAlumniFragment.setVisibility(View.VISIBLE);
        isLoading = true;

        final List<User> newAlumni = new ArrayList<>();
        if (!isFilterApplied) {
            mFirestore.collection(Constants.USER_COLLECTION)
                    .whereEqualTo("mTypeOfUser", "Alumni")
                    .orderBy("mEmail")
                    .startAfter(mLastVisible)
                    .limit(LIMIT)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (pbAlumniFragment != null)
                                pbAlumniFragment.setVisibility(View.INVISIBLE);
                            isLoading = false;
                            if (!documentSnapshots.isEmpty()) {
                                mLastDocumentSnapshotSize = documentSnapshots.size();
                                mLastVisible = documentSnapshots.getDocuments()
                                        .get(documentSnapshots.size() - 1);
                                for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                    newAlumni.add(documentSnapshot.toObject(User.class));
                                mAlumniAdapter.addAll(newAlumni);
                            } else {
                                mLastDocumentSnapshotSize = 0;
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                            if (pbAlumniFragment != null)
                                pbAlumniFragment.setVisibility(View.INVISIBLE);
                            isLoading = false;
                        }
                    });
        } else {
            if (mLocationFilter != null && mClassOfFilter != null) {
                mFirestore.collection(Constants.USER_COLLECTION)
                        .whereEqualTo("mTypeOfUser", "Alumni")
                        .whereEqualTo("mLocation", mLocationFilter)
                        .whereEqualTo("mClassOf", mClassOfFilter)
                        .orderBy("mEmail")
                        .startAfter(mLastVisible)
                        .limit(LIMIT)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                                if (!documentSnapshots.isEmpty()) {
                                    mLastDocumentSnapshotSize = documentSnapshots.size();
                                    mLastVisible = documentSnapshots.getDocuments()
                                            .get(documentSnapshots.size() - 1);
                                    for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                        newAlumni.add(documentSnapshot.toObject(User.class));
                                    mAlumniAdapter.addAll(newAlumni);
                                } else {
                                    mLastDocumentSnapshotSize = 0;
                                    Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                            }
                        });
            }else if(mLocationFilter != null){
                mFirestore.collection(Constants.USER_COLLECTION)
                        .whereEqualTo("mTypeOfUser", "Alumni")
                        .whereEqualTo("mLocation", mLocationFilter)
                        .orderBy("mEmail")
                        .startAfter(mLastVisible)
                        .limit(LIMIT)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                                if (!documentSnapshots.isEmpty()) {
                                    mLastDocumentSnapshotSize = documentSnapshots.size();
                                    mLastVisible = documentSnapshots.getDocuments()
                                            .get(documentSnapshots.size() - 1);
                                    for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                        newAlumni.add(documentSnapshot.toObject(User.class));
                                    mAlumniAdapter.addAll(newAlumni);
                                } else {
                                    mLastDocumentSnapshotSize = 0;
                                    Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                            }
                        });
            }else if(mClassOfFilter != null){
                mFirestore.collection(Constants.USER_COLLECTION)
                        .whereEqualTo("mTypeOfUser", "Alumni")
                        .whereEqualTo("mClassOf", mClassOfFilter)
                        .orderBy("mEmail")
                        .startAfter(mLastVisible)
                        .limit(LIMIT)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                                if (!documentSnapshots.isEmpty()) {
                                    mLastDocumentSnapshotSize = documentSnapshots.size();
                                    mLastVisible = documentSnapshots.getDocuments()
                                            .get(documentSnapshots.size() - 1);
                                    for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                        newAlumni.add(documentSnapshot.toObject(User.class));
                                    mAlumniAdapter.addAll(newAlumni);
                                } else {
                                    mLastDocumentSnapshotSize = 0;
                                    Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                if (pbAlumniFragment != null)
                                    pbAlumniFragment.setVisibility(View.INVISIBLE);
                                isLoading = false;
                            }
                        });
            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            List<User> alumniList = savedInstanceState.getParcelableArrayList("alumni");
            mAlumniAdapter.addAll(alumniList);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("position");
            if (rvAlumni != null)
                rvAlumni.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            mSearchString = savedInstanceState.getString("searchAlumni");
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("alumni", mAlumniAdapter.getmAlumniList());
        if (rvAlumni != null)
            outState.putParcelable("position", rvAlumni.getLayoutManager().onSaveInstanceState());
        if (!TextUtils.isEmpty(mSearchView.getQuery()))
            outState.putString("searchAlumni", mSearchView.getQuery().toString());
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alumnimenu, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchAlumni);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("Type a name...");

        EditText etSearch = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        etSearch.setHintTextColor(Color.DKGRAY);
        etSearch.setTextColor(Color.WHITE);

        if (!TextUtils.isEmpty(mSearchString)) {
            searchItem.expandActionView();
            mSearchView.setQuery(mSearchString, true);
            mSearchView.clearFocus();
            mSearchView.setMaxWidth(Integer.MAX_VALUE);
            menu.findItem(R.id.filter).setVisible(false);
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText != null) {
                    final List<User> searchedAlumni = new ArrayList<User>();
                    mFirestore.collection(Constants.USER_COLLECTION)
                            .whereEqualTo("mTypeOfUser", "Alumni")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null) {
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                            User alumni = documentSnapshot.toObject(User.class);
                                            String name = alumni.getmName();

                                            if (name.toLowerCase().contains(newText.toLowerCase())) {
                                                searchedAlumni.add(alumni);
                                            }
                                        }
                                        mAlumniAdapter.addAsPerSearch(searchedAlumni);
                                    } else {
                                        mAlumniAdapter.setEmptyView();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
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
                //save the current list
                List<User> initialAlumniList = mAlumniAdapter.getmAlumniList();
                mInitialArray = initialAlumniList.toArray(new User[initialAlumniList.size()]);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setItemsVisibility(menu, searchItem, true);
                List<User> alumniList = mAlumniAdapter.getmAlumniList();
                int currentSize = alumniList != null ? alumniList.size() : 0;
                //remove the current items
                mAlumniAdapter.replaceWithInitialList(mInitialArray, currentSize);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (String key : mAlumniLocationArray) {
            editor.remove(String.format("%s_%s", ALUMNI_LOCATION, key));
            editor.apply();
        }
        for (String key : mAlumniClassOfArray) {
            editor.remove(String.format("%s_%s", ALUMNI_CLASS_OF, key));
            editor.apply();
        }
    }


    public static void setFilterApplied(boolean filterApplied) {
        isFilterApplied = filterApplied;
    }
}
