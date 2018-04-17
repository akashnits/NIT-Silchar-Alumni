package com.akash.android.nitsilcharalumni.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.akash.android.nitsilcharalumni.R;

import java.util.List;

public class FeedWidgetProvider extends AppWidgetProvider{

    static List<String> mFeedList;

    private static RemoteViews getFeedListRemoteView(Context context){
        RemoteViews views= new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        Intent intent= new Intent(context, UpdateWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        return views;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<String> feedList,
                                int[] appWidgetIds){
        for(int appWidgetId: appWidgetIds){
            //construct the remoteView object
            mFeedList= feedList;
            RemoteViews views= getFeedListRemoteView(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
