package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniFragment;
import com.akash.android.nitsilcharalumni.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder> {

    private Context mContext;
    private OnAlumniClickHandler mHandler;

    public interface OnAlumniClickHandler{
        void onAlumniClicked(int position, View view);
    }

    public AlumniAdapter(Context mContext, OnAlumniClickHandler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
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
        holder.tvName.setText("Akash Gupta");
        holder.tvClassOf.setText("Class of 2013, ");
        holder.tvOrganisationName.setText("L & T Technology Services");
    }

    private void loadProfileImageWithPicasso(String imageUrl, AlumniViewHolder holder){
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.ivProfileIcon);
    }

    @Override
    public int getItemCount() {
        return 10;
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
}
