package com.example.weekend2_real_master;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Locale;

import static com.example.weekend2_real_master.DatabaseContract.FIELD_NAME;
import static com.example.weekend2_real_master.DatabaseContract.TABLE_NAME;
import static com.example.weekend2_real_master.CelebrityProviderContract.CONTENT_AUTHORITY;
import static com.example.weekend2_real_master.CelebrityProviderContract.PATH_CELEBRITY;

public class CelebrityProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    public static final UriMatcher uriMatcher = buildUriMatcher();
    public static final int CELEBRITY = 100;
    public static final int CELEBRITY_ITEM = 101;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //gives whole database
        matcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY, CELEBRITY);
        //gets each specific record
        matcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY + "/#", CELEBRITY_ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor retCursor = null;
        switch (uriMatcher.match(uri)) {
            case CELEBRITY:
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CELEBRITY_ITEM:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        CelebrityProviderContract.CelebrityEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
        }

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CELEBRITY:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_TYPE;
            case CELEBRITY_ITEM:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnUri;
        long _id;
        SQLiteDatabase writeableDatabase = databaseHelper.getWritableDatabase();
        writeableDatabase.insert(TABLE_NAME, null, contentValues);
        switch (uriMatcher.match(uri)) {
            case CELEBRITY:
                _id = writeableDatabase.insert(TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = CelebrityProviderContract.CelebrityEntry.buildCelebrityUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase writeableDatabase = databaseHelper.getWritableDatabase();
        int rowsAffected = writeableDatabase.delete(
                TABLE_NAME,
                String.format(Locale.US, "WHERE %s = ", FIELD_NAME),
                strings);
        if (rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase writeableDatabase = databaseHelper.getWritableDatabase();
        int rowsAffected = writeableDatabase.update(
                TABLE_NAME,
                contentValues,
                String.format(Locale.US, "WHERE %s = ", FIELD_NAME),
                strings);
        if (rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }
}
