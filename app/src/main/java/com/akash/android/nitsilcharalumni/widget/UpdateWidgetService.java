package com.akash.android.nitsilcharalumni.widget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.akash.android.nitsilcharalumni.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateWidgetService extends IntentService{


    public static String ACTION_UPDATE_WIDGET= "com.akash.android.nitsilcharalumni.widget.action.update_widget";

    private int[] mAppWidgetIds;
    private AppWidgetManager mAppWidgetManager;

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }


    public static void startUpdatingWidget(Context context, List<String> feedList){
        Intent intent= new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putStringArrayListExtra("feedList", (ArrayList<String>) feedList);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            if(intent.getAction().equals(ACTION_UPDATE_WIDGET)){
                mAppWidgetManager= AppWidgetManager.getInstance(this);
                mAppWidgetIds= mAppWidgetManager.getAppWidgetIds(new ComponentName(this, FeedWidgetProvider.class));
                mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetIds, R.id.widget_list_view);
                List<String> feedList= intent.getStringArrayListExtra("feedList");
                FeedWidgetProvider.updateAppWidget(this, mAppWidgetManager,feedList, mAppWidgetIds);
            }
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        FeedWidgetProvider.updateAppWidget(this, mAppWidgetManager, null, mAppWidgetIds);
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }
}
