package com.akash.android.nitsilcharalumni.ui.alumni;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ScrollView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.adapter.AlumniAdapter;
import com.akash.android.nitsilcharalumni.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniFragment extends Fragment implements AlumniAdapter.OnAlumniClickHandler {

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.alumniFragment)
    ScrollView alumniFragment;
    Unbinder unbinder;
    @BindView(R.id.toolbarAlumni)
    Toolbar toolbarAlumni;

    private Context mContext;

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

        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvFeed.setLayoutManager(lm);
        rvFeed.hasFixedSize();
        AlumniAdapter alumniAdapter = new AlumniAdapter(mContext, this);
        rvFeed.setAdapter(alumniAdapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alumnimenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.filter:
                ((MainActivity) getActivity()).commitFilterAlumniFragment();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
