package com.akash.android.nitsilcharalumni.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class FeedProvider extends ContentProvider {

    private SQLiteOpenHelper mDbHelper;

    public static final int CODE_FEED= 100;
    public static final int CODE_FEED_ID= 101;

    private static final UriMatcher sUriMatcher= buildUriMatcher();


    @Override
    public boolean onCreate() {
        mDbHelper= new FeedDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor=null;

        switch (sUriMatcher.match(uri)){
            case CODE_FEED:
                cursor= mDbHelper.getReadableDatabase().query(FeedContract.FeedEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_FEED_ID:
                selectionArgs= new String[]{uri.getLastPathSegment()};
                cursor= mDbHelper.getReadableDatabase().query(FeedContract.FeedEntry.TABLE_NAME,
                        projection,
                        FeedContract.FeedEntry.COLUMN_FEED_ID + " = ? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db= mDbHelper.getWritableDatabase();
        long id=0;
        Uri returnUri=null;
        switch (sUriMatcher.match(uri)){
            case CODE_FEED:
                try {
                    id = db.insertOrThrow(FeedContract.FeedEntry.TABLE_NAME, null, contentValues);
                    if(id > 0)
                        returnUri= ContentUris.withAppendedId(FeedContract.FeedEntry.CONTENT_URI, id);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(id == -1)
            return null;

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db= mDbHelper.getWritableDatabase();
        int rowsDeleted=0 ;
        switch (sUriMatcher.match(uri)){
            case CODE_FEED:
                try{
                    rowsDeleted= db.delete(FeedContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
                }catch (SQLException s){
                    s.printStackTrace();
                }
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_FEED, CODE_FEED);
        uriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_FEED + "/#", CODE_FEED_ID);
        return uriMatcher;
    }
}
