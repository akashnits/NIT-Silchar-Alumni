package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.bookmark.BookmarkFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedCursorAdapter extends RecyclerView.Adapter<FeedCursorAdapter.BookmarkedFeedViewHolder> {

    public static final String TAG = FeedCursorAdapter.class.getSimpleName();

    private Context context;
    private Cursor mCursor;


    public FeedCursorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BookmarkedFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        view.setFocusable(true);
        return new BookmarkedFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkedFeedViewHolder holder, int position) {
        boolean moved = mCursor.moveToPosition(position);
        if (moved) {
            holder.tvName.setText(mCursor.getString(BookmarkFragment.INDEX_PROFILE_NAME));
            holder.tvFeedDescription.setText(mCursor.getString(BookmarkFragment.INDEX_FEED_DESCRIPTION));
            holder.tvTimeStamp.setText(mCursor.getString(BookmarkFragment.INDEX_FEED_TIMESTAMP));
            holder.tvSearchHashtag.setText(mCursor.getString(BookmarkFragment.INDEX_FEED_HASHTAG));

            String profileImageUrl = mCursor.getString(BookmarkFragment.INDEX_PROFILE_IMAGE_URL);
            Picasso.with(context).load(profileImageUrl).into(holder.ivProfileIcon);
            String feedImageUrl = mCursor.getString(BookmarkFragment.INDEX_FEED_IMAGE_URL);
            Picasso.with(context).load(feedImageUrl).into(holder.ivFeedImage);
            holder.itemView.setTag(mCursor.getInt(BookmarkFragment.INDEX_FEED_ID));
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    class BookmarkedFeedViewHolder extends RecyclerView.ViewHolder {

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

        public BookmarkedFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void swapCursor(Cursor c) {
        mCursor = c;
        notifyDataSetChanged();
    }
}
