package com.akash.android.nitsilcharalumni.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FeedDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME= "feed.db";

    private static final int DATABASE_VERSION= 1;

    public FeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FEED_TABLE=
                "CREATE TABLE " + FeedContract.FeedEntry.TABLE_NAME + " (" +
                        FeedContract.FeedEntry._ID + " INTEGER PRIMARY KEY, " +
                        FeedContract.FeedEntry.COLUMN_FEED_ID + " INTEGER UNIQUE NOT NULL, " +
                        FeedContract.FeedEntry.COLUMN_FEED_IMAGE_URL + " VARCHAR(200), " +
                        FeedContract.FeedEntry.COLUMN_PROFILE_IMAGE_URL + " VARCHAR(200), " +
                        FeedContract.FeedEntry.COLUMN_PROFILE_NAME + " VARCHAR(20), " +
                        FeedContract.FeedEntry.COLUMN_FEED_DESCRIPTION + " VARCHAR(900), " +
                        FeedContract.FeedEntry.COLUMN_FEED_TIMESTAMP + " VARCHAR(20), " +
                        FeedContract.FeedEntry.COLUMN_FEED_HASHTAG + " VARCHAR(200) " +
                        ");";

        db.execSQL(SQL_CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FeedContract.FeedEntry.TABLE_NAME);
        onCreate(db);
    }
}
