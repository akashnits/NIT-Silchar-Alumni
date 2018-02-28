package com.akash.android.nitsilcharalumni.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.data.FeedContract;
import com.akash.android.nitsilcharalumni.model.Feed;
import com.akash.android.nitsilcharalumni.ui.feed.FeedFragment;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {


    private Context mContext;
    private OnBookmarkClickedHandler mBookmarkClickedHandler;
    private ContentResolver mContentResolver;
    private ArrayList<Feed> mFeedList;


    public FeedAdapter(Context mContext, OnBookmarkClickedHandler bookmarkClickedHandler) {
        this.mContext = mContext;
        this.mBookmarkClickedHandler = bookmarkClickedHandler;
        this.mContentResolver = mContext.getContentResolver();
        this.mFeedList = new ArrayList<>();
    }

    public interface OnBookmarkClickedHandler {
        void onBookmarkClicked(int position);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        Feed feed = mFeedList.get(position);
        if (feed != null) {
            holder.tvName.setText(feed.getmAuthorName());String s= "<b>" + "Description: " + "</b> "
                    + feed.getmFeedDescription();
            holder.tvFeedDescription.setText(Html.fromHtml(s));

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            String strDate = formatter.format(feed.getmTimestamp());

            holder.tvTimeStamp.setText(strDate);

            String strSearchKeyword= "";
            Map<String, Boolean> strSearchKeywordMap = feed.getmFeedSearchKeywordsMap();
            if(strSearchKeywordMap != null && !strSearchKeywordMap.isEmpty()) {
                for (String key : strSearchKeywordMap.keySet())
                    strSearchKeyword = strSearchKeyword.concat("#").concat(key).concat(" ");

                holder.tvSearchHashtag.setVisibility(View.VISIBLE);
                holder.tvSearchHashtag.setText(strSearchKeyword);
            }else {
                holder.tvSearchHashtag.setVisibility(View.GONE);
            }

            loadProfileImageWithPicasso(feed.getmAuthorImageUrl(),
                    holder);
            if(feed.getmFeedImageUrl() != null) {
                holder.ivFeedImage.setVisibility(View.VISIBLE);
                loadFeedImageWithPicasso(feed.getmFeedImageUrl(), holder);
            }else{
                holder.ivFeedImage.setVisibility(View.GONE);
            }
        }


        if (FeedFragment.getBookmarkStatus(FeedContract.FeedEntry.CONTENT_URI, mContentResolver,
                position))
            holder.cbBookmark.setChecked(true);
        else
            holder.cbBookmark.setChecked(false);
    }

    private void loadProfileImageWithPicasso(String imageUrl, FeedViewHolder holder) {
        Picasso.with(mContext).load(imageUrl)
                .transform(new PicassoCircleTransformation())
                .into(holder.ivProfileIcon);
    }

    private void loadFeedImageWithPicasso(String imageUrl, FeedViewHolder holder) {
        /*Display display =  mContext.getSystemService(WindowManager.class).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;*/
        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.drawable.loading)
                .fit()
                .into(holder.ivFeedImage);
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public void addAll(List<Feed> newFeed) {
        int initialSize = mFeedList.size();
        mFeedList.addAll(newFeed);
        notifyItemRangeInserted(initialSize, newFeed.size());
    }

    public void addAllAtStart(List<Feed> newFeed){
        mFeedList.addAll(0, newFeed);
        notifyItemRangeChanged(0, newFeed.size());
    }

    public void addAsPerSearch(List<Feed> searchedFeed){
        //get the current items
        int currentSize = mFeedList.size();
        //remove the current items
        mFeedList.clear();
        //add all the new items
        mFeedList.addAll(searchedFeed);
        //tell the recycler view that all the old items are gone
        notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mFeedList.size());
    }

    public void replaceWithInitialList(Feed[] originalArray, int currentSize){
        mFeedList.clear();
        mFeedList.addAll(Arrays.asList(originalArray));
        notifyItemRangeRemoved(0, currentSize);
        notifyItemRangeInserted(0, mFeedList.size());
    }

    public void setEmptyView(){
        int currentSize= mFeedList.size();
        mFeedList.clear();
        notifyItemRangeRemoved(0, currentSize);
    }

    public ArrayList<Feed> getmFeedList() {
        return mFeedList;
    }

    public Feed getFeedObjectAtPosition(int position){
        return mFeedList.get(position);
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

       /* public void setVisibility(boolean isVisible, View view){
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (isVisible){
                param.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                param.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                view.setVisibility(View.VISIBLE);
            }else{
                view.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            view.setLayoutParams(param);
        }*/
    }
}
