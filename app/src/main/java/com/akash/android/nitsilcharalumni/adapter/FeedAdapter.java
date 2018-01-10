package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.FeedContract;
import com.akash.android.nitsilcharalumni.ui.feed.FeedFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {


    private Context mContext;
    private OnBookmarkClickedHandler mBookmarkClickedHandler;

    public FeedAdapter(Context mContext, OnBookmarkClickedHandler bookmarkClickedHandler) {
        this.mContext = mContext;
        this.mBookmarkClickedHandler= bookmarkClickedHandler;
    }

    public interface OnBookmarkClickedHandler{
        void onBookmarkClicked(int position);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.tvName.setText("Akash Gupta");
        holder.tvFeedDescription.setText("Learning android is fun. You can turn your ideas into reality and let the world know");
        holder.tvTimeStamp.setText("July 6, 2017");
        holder.tvSearchHashtag.setText("#android #learning #event");

        boolean isBookmarked= FeedFragment.getBookmarkStatus(FeedContract.FeedEntry.CONTENT_URI, mContext.getContentResolver(),
                position);
        if(isBookmarked)
            holder.cbBookmark.setChecked(true);
        else
            holder.cbBookmark.setChecked(false);

        loadProfileImageWithPicasso("https://www2.mmu.ac.uk/research/research-study/student-profiles/james-xu/james-xu.jpg", holder);
        loadFeedImageWithPicasso("https://c.tadst.com/gfx/750w/world-post-day.jpg?1", holder);
    }

    private void loadProfileImageWithPicasso(String imageUrl, FeedViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.ivProfileIcon);
    }

    private void loadFeedImageWithPicasso(String imageUrl, FeedViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.ivFeedImage);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class FeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfileIcon)
        ImageView ivProfileIcon;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTimeStamp)
        TextView tvTimeStamp;
        @BindView(R.id.ivFeedImage)
        ImageView ivFeedImage;
        @BindView(R.id.tvFeedDescription)
        TextView tvFeedDescription;
        @BindView(R.id.tvSearchHashtag)
        TextView tvSearchHashtag;
        @BindView(R.id.cbBookmark)
        CheckBox cbBookmark;

        public FeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cbBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBookmarkClickedHandler.onBookmarkClicked(getAdapterPosition());
                }
            });
        }
    }
}
