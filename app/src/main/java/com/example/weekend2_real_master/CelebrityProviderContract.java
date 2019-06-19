package com.example.weekend2_real_master;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class CelebrityProviderContract {
    public static final String CONTENT_AUTHORITY = "package com.example.weekend2_real_master";
    public static final Uri BASE_CONTENT_ID = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CELEBRITY = "celebrity";

    public static final class CelebrityEntry implements BaseColumns {
        //actually identifies the real database, through provider pipeline
        public static final Uri CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_CELEBRITY).build();
        //keep an eye out for malformed exception below for the wrong strings
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CELEBRITY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CELEBRITY;

        public static Uri buildCelebrityUri(long id){
            //builds URI so the resolver knows what the system is looking at
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
