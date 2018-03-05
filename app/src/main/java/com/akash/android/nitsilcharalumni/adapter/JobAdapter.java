package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.Job;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {


    private Context mContext;
    private ArrayList<Job> mJobList;


    public JobAdapter(Context mContext) {
        this.mContext = mContext;
        this.mJobList = new ArrayList<>();
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        Job job = mJobList.get(position);
        if (job != null) {
            boolean flag= false;

            holder.tvNameJob.setText(job.getmAuthorName());

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            String strDate = formatter.format(job.getmTimestamp());

            holder.tvTimeStampJob.setText(strDate);

            holder.tvJobDescription.setText(job.getmJobDescription());

            String orgName = job.getmJobOrganisation();
            if (orgName != null) {
                holder.tvJobOrganisationName.setVisibility(View.VISIBLE);
                holder.tvJobOrganisationName.setText(job.getmJobOrganisation());
            } else
                holder.tvJobOrganisationName.setVisibility(View.GONE);

            String jobLocation = job.getmJobLocation();
            if (jobLocation != null) {
                holder.tvJobLocation.setText(job.getmJobLocation());
                holder.tvJobLocation.setVisibility(View.VISIBLE);
                flag= true;
            } else
                holder.tvJobLocation.setVisibility(View.GONE);

            String jobTitle = job.getmJobTitle();
            if (jobTitle != null) {
                holder.tvJobTitle.setVisibility(View.VISIBLE);
                if(flag)
                    holder.tvJobTitle.setText(job.getmJobTitle().concat(", "));
                else
                    holder.tvJobTitle.setText(job.getmJobTitle());
            } else
                holder.tvJobTitle.setVisibility(View.GONE);

            String strSearchKeyword = "";
            Map<String, Boolean> strSearchKeywordMap = job.getmJobSearchKeywordsMap();
            if (strSearchKeywordMap != null && !strSearchKeywordMap.isEmpty()) {
                for (String key : strSearchKeywordMap.keySet())
                    strSearchKeyword = strSearchKeyword.concat("#").concat(key).concat(" ");
                holder.tvJobSearchHashtag.setVisibility(View.VISIBLE);
                holder.tvJobSearchHashtag.setText(strSearchKeyword);
            } else {
                holder.tvJobSearchHashtag.setVisibility(View.GONE);
            }

            loadProfileImageWithPicasso(job.getmAuthorImageUrl(), holder);
            if (job.getmJobImageUrl() != null) {
                holder.ivJobDescription.setVisibility(View.VISIBLE);
                loadFeedImageWithPicasso(job.getmJobImageUrl(), holder);
            } else
                holder.ivJobDescription.setVisibility(View.GONE);
        }
    }

    private void loadProfileImageWithPicasso(String imageUrl, JobViewHolder holder) {
        if(imageUrl != null) {
            Picasso.with(mContext).load(imageUrl)
                    .transform(new PicassoCircleTransformation())
                    .into(holder.ivProfileIconJob);
        }else {
            Picasso.with(mContext).load(R.drawable.loading)
                    .transform(new PicassoCircleTransformation())
                    .into(holder.ivProfileIconJob);
        }
    }

    private void loadFeedImageWithPicasso(String imageUrl, JobViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.drawable.loading)
                .fit()
                .into(holder.ivJobDescription);
    }

    @Override
    public int getItemCount() {
        return mJobList.size();
    }

    public void addAll(List<Job> newJob) {
        int initialSize = mJobList.size();
        mJobList.addAll(newJob);
        notifyItemRangeInserted(initialSize, newJob.size());
    }

    public void addAllAtStart(List<Job> newJob) {
        mJobList.addAll(0, newJob);
        notifyItemRangeChanged(0, newJob.size());
    }

    public void addAsPerSearch(List<Job> searchedJob) {
        //get the current items
        int currentSize = mJobList.size();
        //remove the current items
        mJobList.clear();
        //add all the new items
        mJobList.addAll(searchedJob);
        //tell the recycler view that all the old items are gone
        notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mJobList.size());
    }

    public void replaceWithInitialList(Job[] originalArray, int currentSize) {
        mJobList.clear();
        mJobList.addAll(Arrays.asList(originalArray));
        notifyItemRangeRemoved(0, currentSize);
        notifyItemRangeInserted(0, mJobList.size());
    }

    public void setEmptyView() {
        int currentSize = mJobList.size();
        mJobList.clear();
        notifyItemRangeRemoved(0, currentSize);
    }

    public ArrayList<Job> getmJobList() {
        return mJobList;
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileIconJob)
        ImageView ivProfileIconJob;
        @BindView(R.id.tvNameJob)
        TextView tvNameJob;
        @BindView(R.id.tvTimeStampJob)
        TextView tvTimeStampJob;
        @BindView(R.id.tvJobTtitle)
        TextView tvJobTitle;
        @BindView(R.id.tvJobLocation)
        TextView tvJobLocation;
        @BindView(R.id.tvJobOrganisationName)
        TextView tvJobOrganisationName;
        @BindView(R.id.tvJobDescription)
        TextView tvJobDescription;
        @BindView(R.id.tvJobSearchHashtag)
        TextView tvJobSearchHashtag;
        @BindView(R.id.tvJobId)
        TextView tvJobId;
        @BindView(R.id.ivJobDescription)
        ImageView ivJobDescription;

        public JobViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
