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


public class JobOrganisationAdapter extends RecyclerView.Adapter<JobOrganisationAdapter.JobOrganisationViewHolder> {



    private List<String> mJobOrganisationList;
    private Context mContext;
    private int mPositionLastChecked;
    private RecyclerView mRecyclerView;
    private FilterJobFragment mFilterJobFragment;

    public JobOrganisationAdapter(Context mContext, FragmentManager fm, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mJobOrganisationList = Arrays.asList(mContext.getResources().getStringArray(R.array.organisation));
        this.mRecyclerView = mRecyclerView;
        this.mFilterJobFragment = ((FilterJobFragment) fm.findFragmentByTag("FilterJob"));
        this.mPositionLastChecked = mFilterJobFragment.getmOrganisationCheckedPoistion();
    }

    @Override
    public JobOrganisationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job_organisation,
                parent, false);
        return new JobOrganisationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobOrganisationViewHolder holder, int position) {
        holder.ctvJobOrganisation.setText(mJobOrganisationList.get(position));
        if ((mFilterJobFragment.getmMainActivity().isOrganisationPreferenceChecked() &&
                position == mPositionLastChecked)) {
            holder.ctvJobOrganisation.setSelected(true);
            holder.ctvJobOrganisation.setCheckMarkDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_check_box_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return mJobOrganisationList.size();
    }

    class JobOrganisationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctvJobOrganisation)
        CheckedTextView ctvJobOrganisation;

        public JobOrganisationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ctvJobOrganisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //wherever the click be, set the last checked position false if it's there
                    if (mFilterJobFragment.getmMainActivity().isOrganisationPreferenceChecked()) {
                        JobOrganisationViewHolder viewHolder = (JobOrganisationViewHolder)
                                mRecyclerView.findViewHolderForAdapterPosition(mPositionLastChecked);
                        if (viewHolder != null) {
                            viewHolder.ctvJobOrganisation.setSelected(false);
                            viewHolder.ctvJobOrganisation.setCheckMarkDrawable(mContext.getResources()
                                    .getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
                        }
                    }

                    //Clicked on  somewhere else or no checks
                    if (!mFilterJobFragment.getmMainActivity().isOrganisationPreferenceChecked()
                            || getAdapterPosition() != mPositionLastChecked) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_black_24dp));
                        mPositionLastChecked = getAdapterPosition();
                        mFilterJobFragment.setmOrganisationCheckedPoistion(mPositionLastChecked);
                        mFilterJobFragment.getmMainActivity().setOrganisationPreferenceChecked(true);
                        ctv.toggle();
                    } else {
                        mFilterJobFragment.getmMainActivity().setOrganisationPreferenceChecked(false);
                    }
                }
            });
        }
    }
}
