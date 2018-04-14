package com.akash.android.nitsilcharalumni.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.job.FilterJobFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JobLocationAdapter extends RecyclerView.Adapter<JobLocationAdapter.JobLocationViewHolder> {

    private List<String> mJobLocationList;
    private Context mContext;
    private int mPositionLastChecked;
    private RecyclerView mRecyclerView;
    private FilterJobFragment mFilterJobFragment;

    public JobLocationAdapter(Context mContext, FragmentManager fm, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mJobLocationList = Arrays.asList(mContext.getResources().getStringArray(R.array.location));
        this.mRecyclerView = mRecyclerView;
        this.mFilterJobFragment = ((FilterJobFragment) fm.findFragmentByTag("FilterJob"));
        this.mPositionLastChecked = mFilterJobFragment.getmJobLocationCheckedPosition();
    }

    @Override
    public JobLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job_location,
                parent, false);
        return new JobLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobLocationViewHolder holder, int position) {
        holder.ctvJobLocation.setText(mJobLocationList.get(position));
        if ((mFilterJobFragment.getmMainActivity().isJobLocationPreferenceChecked()
                && position == mPositionLastChecked)) {
            holder.ctvJobLocation.setSelected(true);
            holder.ctvJobLocation.setCheckMarkDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_check_box_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return mJobLocationList.size();
    }

    class JobLocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctvJobLocation)
        CheckedTextView ctvJobLocation;

        JobLocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ctvJobLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //wherever the click be, set the last checked position false if it's there
                    if (mFilterJobFragment.getmMainActivity().isJobLocationPreferenceChecked()) {
                        JobLocationViewHolder viewHolder = (JobLocationViewHolder)
                                mRecyclerView.findViewHolderForAdapterPosition(mPositionLastChecked);
                        viewHolder.ctvJobLocation.setSelected(false);
                        viewHolder.ctvJobLocation.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
                    }

                    //Clicked on  somewhere else or no checks
                    if (!mFilterJobFragment.getmMainActivity().isJobLocationPreferenceChecked()
                            || getAdapterPosition() != mPositionLastChecked) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_black_24dp));
                        mPositionLastChecked = getAdapterPosition();
                        mFilterJobFragment.setmJobLocationCheckedPosition(mPositionLastChecked);
                        mFilterJobFragment.getmMainActivity().setJobLocationPreferenceChecked(true);
                        ctv.toggle();
                    } else {
                        mFilterJobFragment.getmMainActivity().setJobLocationPreferenceChecked(false);
                    }
                }
            });
        }
    }
}
