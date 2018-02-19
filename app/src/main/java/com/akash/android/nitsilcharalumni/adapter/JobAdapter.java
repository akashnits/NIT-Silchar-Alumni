package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.Feed;
import com.akash.android.nitsilcharalumni.model.Job;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

            holder.tvNameJob.setText(job.getmAuthorName());

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            String strDate = formatter.format(job.getmTimestamp());

            holder.tvTimeStampJob.setText(strDate);

            holder.tvJobDescription.setText(job.getmJobDescription());
            holder.tvJobOrganisationName.setText(job.getmJobOrganisation());
            holder.tvJobTtitle.setText(job.getmJobTitle() + ", ");
            holder.tvJobLocation.setText(job.getmJobLocation());

            String strSearchKeyword= "";
            Map<String, Boolean> strSearchKeywordMap = job.getmJobSearchKeywordsMap();
            for (String key: strSearchKeywordMap.keySet())
                strSearchKeyword = strSearchKeyword.concat("#").concat(key).concat(" ");

            holder.tvJobSearchHashtag.setText(strSearchKeyword);

            loadProfileImageWithPicasso("https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg", holder);
            loadFeedImageWithPicasso("https://c.tadst.com/gfx/750w/world-post-day.jpg?1", holder);
        }
    }

    private void loadProfileImageWithPicasso(String imageUrl, JobViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .transform(new PicassoCircleTransformation())
                .into(holder.ivProfileIconJob);
    }

    private void loadFeedImageWithPicasso(String imageUrl, JobViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
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

    public void addAsPerSearch(List<Job> searchedJob){
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
        TextView tvJobTtitle;
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
