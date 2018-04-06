package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.Job;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.job.JobFragment;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.akash.android.nitsilcharalumni.ui.job.JobFragment.LIMIT;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> implements Filterable {


    private Context mContext;
    private ArrayList<Job> mJobList;
    private FirebaseFirestore mFirestore;
    private DocumentSnapshot mLastVisible = null;
    private JobFilter mJobFilter;
    private JobFragment mJobFragment;
    private MainActivity mMainActivity;

    public JobAdapter(Context mContext,JobFragment jobFragment) {
        this.mContext = mContext;
        mJobList = new ArrayList<>();
        mFirestore = FirebaseFirestore.getInstance();
        mJobFragment = jobFragment;
        this.mMainActivity= (MainActivity) mJobFragment.getActivity();
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

    @Override
    public Filter getFilter() {
        if (mJobFilter == null) {
            mJobFilter = new JobFilter();
        }
        return mJobFilter;
    }

    class JobFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (constraint != null && constraint.length() > 0) {
                final ArrayList<Job> filterList = new ArrayList<>();
                if (mMainActivity.getmJobLocationConstraint() != null &&
                        mMainActivity.getmJobOrganisationConstraint() != null) {
                    mJobFragment.setmIsLoading(true);
                    String[] constraints = constraint.toString().split(",");
                    Query query;
                    if (mLastVisible != null) {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .startAfter(mLastVisible)
                                .limit(LIMIT)
                                .whereEqualTo("mJobLocation", constraints[0])
                                .whereEqualTo("mJobOrganisation", constraints[1]);
                    } else {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .limit(LIMIT)
                                .whereEqualTo("mJobLocation", constraints[0])
                                .whereEqualTo("mJobOrganisation", constraints[1]);
                    }
                    query
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                        }
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            filterList.add(documentSnapshot.toObject(Job.class));
                                        if (filterList.size() > 0) {
                                            addAll(filterList);
                                        }
                                    } else {
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(0);
                                        }
                                        Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                    }
                                    mJobFragment.setmIsLoading(false);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    mJobFragment.setmIsLoading(false);
                                }
                            });
                } else if (mMainActivity.getmJobLocationConstraint() != null) {
                    mJobFragment.setmIsLoading(true);
                    Query query;
                    if (mLastVisible != null) {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .startAfter(mLastVisible)
                                .limit(LIMIT)
                                .whereEqualTo("mJobLocation", constraint.toString());
                    } else {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .limit(LIMIT)
                                .whereEqualTo("mJobLocation", constraint.toString());
                    }
                    query
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                        }
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            filterList.add(documentSnapshot.toObject(Job.class));
                                        if (filterList.size() > 0) {
                                            addAll(filterList);
                                        }
                                    } else {
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(0);
                                        }
                                        Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                    }
                                    mJobFragment.setmIsLoading(false);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    mJobFragment.setmIsLoading(false);
                                }
                            });
                } else if (mMainActivity.getmJobOrganisationConstraint() != null) {
                    mJobFragment.setmIsLoading(true);
                    Query query;
                    if (mLastVisible != null) {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .startAfter(mLastVisible)
                                .limit(LIMIT)
                                .whereEqualTo("mJobOrganisation", constraint.toString());
                    } else {
                        query = mFirestore.collection(Constants.JOB_COLLECTION)
                                .orderBy("mTimestamp", Query.Direction.DESCENDING)
                                .limit(LIMIT)
                                .whereEqualTo("mJobOrganisation", constraint.toString());
                    }
                    query
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                        mLastVisible = documentSnapshots.getDocuments()
                                                .get(documentSnapshots.size() - 1);
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                        }
                                        for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                            filterList.add(documentSnapshot.toObject(Job.class));
                                        if (filterList.size() > 0) {
                                            addAll(filterList);
                                        }
                                    } else {
                                        if (mJobFragment != null) {
                                            mJobFragment.setmLastDocumentSnapshotSize(0);
                                        }
                                        Toast.makeText(mContext, "That's all, folks!", Toast.LENGTH_SHORT).show();
                                    }
                                    mJobFragment.setmIsLoading(false);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Failed to Load data", Toast.LENGTH_SHORT).show();
                                    mJobFragment.setmIsLoading(false);
                                }
                            });
                }
            }
        }
    }

}
