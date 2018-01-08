package com.akash.android.nitsilcharalumni.ui.job;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.JobAdapter;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class JobFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvJob)
    RecyclerView rvJob;
    @BindView(R.id.jobFragment)
    NestedScrollView jobFragment;
    @BindView(R.id.jobFab)
    FloatingActionButton jobFab;
    @BindView(R.id.swipe_refresh_layout_job)
    SwipeRefreshLayout swipeRefreshLayoutJob;
    Unbinder unbinder;
    @BindView(R.id.toolbarJob)
    Toolbar toolbarJob;

    private Context mContext;
    private JobAdapter mJobAdapter;

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
        swipeRefreshLayoutJob.setOnRefreshListener(this);

        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvJob.setLayoutManager(lm);
        rvJob.hasFixedSize();
        mJobAdapter = new JobAdapter(mContext);
        rvJob.setAdapter(mJobAdapter);
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
        Toast.makeText(mContext, "Refreshed", Toast.LENGTH_SHORT).show();
        swipeRefreshLayoutJob.setRefreshing(false);
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
                        "CreateJobFragment");
                break;
            case R.id.swipe_refresh_layout_job:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.jobmenu, menu);
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
}
