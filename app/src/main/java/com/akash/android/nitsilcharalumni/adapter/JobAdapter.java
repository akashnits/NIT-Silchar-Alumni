package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private Context mContext;

    public JobAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        holder.tvNameJob.setText("Akash Raj");
        holder.tvClassOfJob.setText("2013");
        holder.tvOrganisationNameJob.setText("L & T Technology Services");
        holder.tvJobDescription.setText("Hiring for 2 years experienced Android developer who is passionate about learning new technologies ");
        holder.tvJobOrganisationName.setText("Google Inc.");
        holder.tvJobTtitle.setText("Android developer");
        holder.tvJobLocation.setText("Bangalore");
        holder.tvJobSearchHashtag.setText("#android #java");

        loadProfileImageWithPicasso("https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg", holder);
        loadFeedImageWithPicasso("https://c.tadst.com/gfx/750w/world-post-day.jpg?1", holder);

    }

    private void loadProfileImageWithPicasso(String imageUrl, JobViewHolder holder){
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.ivProfileIconJob);
    }

    private void loadFeedImageWithPicasso(String imageUrl, JobViewHolder holder){
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.ivJobDescription);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileIconJob)
        ImageView ivProfileIconJob;
        @BindView(R.id.tvNameJob)
        TextView tvNameJob;
        @BindView(R.id.tvClassOfJob)
        TextView tvClassOfJob;
        @BindView(R.id.tvOrganisationNameJob)
        TextView tvOrganisationNameJob;
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
