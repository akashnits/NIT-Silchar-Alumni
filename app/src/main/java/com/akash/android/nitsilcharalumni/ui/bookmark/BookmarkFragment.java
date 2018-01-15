package com.akash.android.nitsilcharalumni.ui.bookmark;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.FeedCursorAdapter;
import com.akash.android.nitsilcharalumni.data.FeedContract;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerHeader;
import com.akash.android.nitsilcharalumni.ui.drawer.DrawerMenuItem;
import com.akash.android.nitsilcharalumni.utils.MyDrawerLayout;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] FEED_PROJECTION = {
            FeedContract.FeedEntry.COLUMN_FEED_ID,
            FeedContract.FeedEntry.COLUMN_FEED_IMAGE_URL,
            FeedContract.FeedEntry.COLUMN_PROFILE_IMAGE_URL,
            FeedContract.FeedEntry.COLUMN_PROFILE_NAME,
            FeedContract.FeedEntry.COLUMN_FEED_DESCRIPTION,
            FeedContract.FeedEntry.COLUMN_FEED_TIMESTAMP,
            FeedContract.FeedEntry.COLUMN_FEED_HASHTAG,
    };

    public static final int INDEX_FEED_ID = 0;
    public static final int INDEX_FEED_IMAGE_URL = 1;
    public static final int INDEX_PROFILE_IMAGE_URL = 2;
    public static final int INDEX_PROFILE_NAME = 3;
    public static final int INDEX_FEED_DESCRIPTION = 4;
    public static final int INDEX_FEED_TIMESTAMP = 5;
    public static final int INDEX_FEED_HASHTAG = 6;

    public static final int LOADER_BOOKMARKED_FEED_ID = 183;


    private FeedCursorAdapter mFeedCursorAdapter;

    private Context mContext;

    @BindView(R.id.toolbarBookmark)
    Toolbar toolbarBookmark;
    @BindView(R.id.rvBookmark)
    RecyclerView rvBookmark;
    @BindView(R.id.drawerViewBookmark)
    PlaceHolderView drawerViewBookmark;
    @BindView(R.id.drawerLayoutBookmark)
    MyDrawerLayout drawerLayoutBookmark;
    Unbinder unbinder;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
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
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarBookmark);

        setupDrawer();

        mFeedCursorAdapter = new FeedCursorAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvBookmark.setLayoutManager(linearLayoutManager);
        rvBookmark.setAdapter(mFeedCursorAdapter);
        rvBookmark.hasFixedSize();

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_BOOKMARKED_FEED_ID, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_BOOKMARKED_FEED_ID:
                return new CursorLoader(getContext(),
                        FeedContract.FeedEntry.CONTENT_URI,
                        FEED_PROJECTION,
                        null,
                        null,
                        FeedContract.FeedEntry._ID + " ASC");
            default:
                throw new RuntimeException("Loader not implemented: " + LOADER_BOOKMARKED_FEED_ID);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFeedCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupDrawer() {
        drawerViewBookmark
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_RATE_US))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_CONTACT_US))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT))
                .addView(new DrawerMenuItem(mContext, DrawerMenuItem.DRAWER_MENU_ITEM_DEVELOPED_BY));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle((MainActivity) getActivity(),
                drawerLayoutBookmark, toolbarBookmark,
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

        drawerLayoutBookmark.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
