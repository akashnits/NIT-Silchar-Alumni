package com.akash.android.nitsilcharalumni.widget;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.akash.android.nitsilcharalumni.R;

import java.util.List;

public class ListWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private Context mContext;
        private List<String> feedList;

        public ListRemoteViewsFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            feedList= FeedWidgetProvider.mFeedList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(feedList == null)
                return 0;
            return feedList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views= new RemoteViews(mContext.getPackageName(), R.layout.feed_widget);
            views.setTextViewText(R.id.appwidget_text, feedList.get(position));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
