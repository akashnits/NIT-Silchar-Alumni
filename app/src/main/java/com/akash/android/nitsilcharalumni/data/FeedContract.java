package com.akash.android.nitsilcharalumni.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class FeedContract {

    public static final String CONTENT_AUTHORITY= "com.akash.android.nitsilcharalumni";

    public static final Uri BASE_CONTENT_URI= Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_FEED = "feed";

    public static class FeedEntry implements BaseColumns {

        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FEED)
                .build();

        public static final String TABLE_NAME= "feed";

        public static final String COLUMN_FEED_ID= "feedId";

        public static final String COLUMN_FEED_IMAGE_URL = "feedImage";

        public static final String COLUMN_PROFILE_IMAGE_URL= "profileImage";

        public static final String COLUMN_PROFILE_NAME= "profileName";

        public static final String COLUMN_FEED_DESCRIPTION= "feedDescription";

        public static final String COLUMN_FEED_TIMESTAMP= "feedTimestamp";

        public static final String COLUMN_FEED_HASHTAG = "feedHashtag";


        public static Uri buildUriWithFeedId(int _id){
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(_id))
                    .build();
        }
    }
}
