package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder> {

    private Context mContext;
    private OnAlumniClickHandler mHandler;
    private ArrayList<User> mAlumniList;

    public interface OnAlumniClickHandler{
        void onAlumniClicked(int position, View view);
    }

    public AlumniAdapter(Context mContext, OnAlumniClickHandler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        mAlumniList = new ArrayList<>();
    }

    @Override
    public AlumniViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alumni,
                parent, false);
        return new AlumniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumniViewHolder holder, int position) {
        loadProfileImageWithPicasso("https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg", holder);

        User alumni= mAlumniList.get(position);
        if(alumni != null) {
            holder.tvName.setText(alumni.getmName());
            holder.tvOrganisationName.setText(alumni.getmOrganisation());
            holder.tvClassOf.setText(alumni.getmClassOf());
        }
    }

    private void loadProfileImageWithPicasso(String imageUrl, AlumniViewHolder holder){
        Picasso.with(mContext).load(imageUrl)
                .transform(new PicassoCircleTransformation())
                .into(holder.ivProfileIcon);
    }

    @Override
    public int getItemCount() {
        return mAlumniList.size();
    }

    public void addAll(List<User> newAlumni) {
        int initialSize = mAlumniList.size();
        mAlumniList.addAll(newAlumni);
        notifyItemRangeInserted(initialSize, newAlumni.size());
    }

    public void addAsPerSearch(List<User> searchedFeed){
        //get the current items
        int currentSize = mAlumniList.size();
        //remove the current items
        mAlumniList.clear();
        //add all the new items
        mAlumniList.addAll(searchedFeed);
        //tell the recycler view that all the old items are gone
        notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mAlumniList.size());
    }

    public void replaceWithInitialList(User[] originalArray, int currentSize){
        mAlumniList.clear();
        mAlumniList.addAll(Arrays.asList(originalArray));
        notifyItemRangeRemoved(0, currentSize);
        notifyItemRangeInserted(0, mAlumniList.size());
    }

    public void setEmptyView(){
        int currentSize= mAlumniList.size();
        mAlumniList.clear();
        notifyItemRangeRemoved(0, currentSize);
    }
    class AlumniViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfileIcon)
        ImageView ivProfileIcon;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvClassOf)
        TextView tvClassOf;
        @BindView(R.id.tvOrganisationName)
        TextView tvOrganisationName;

        public AlumniViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHandler.onAlumniClicked(getAdapterPosition(), view);
                }
            });
        }
    }

    public ArrayList<User> getmAlumniList() {
        return mAlumniList;
    }
}
