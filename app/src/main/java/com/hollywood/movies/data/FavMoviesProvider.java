package com.hollywood.movies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hollywood.movies.data.FavMoviesContract.FavMoviesEntry;



/**
 * Created by chenthil on 7/23/17.
 */

public class FavMoviesProvider extends ContentProvider {

    private FavMoviesDbHelper favMoviesDbHelper;

    private static final int FAVORITES = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavMoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FavMoviesContract.PATH_FAV_MOVIES, FAVORITES);
        //matcher.addURI(authority, FavMoviesContract.PATH_FAV_MOVIES + "/#", CODE_WEATHER_WITH_DATE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        favMoviesDbHelper = new FavMoviesDbHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase sqLiteDatabase = favMoviesDbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(FavMoviesEntry.TABLE_NAME,projection,selection,
                                                selectionArgs,null,null,sortOrder);

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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase sqLiteDatabase = favMoviesDbHelper.getWritableDatabase();
        long rowId = sqLiteDatabase.insert(FavMoviesEntry.TABLE_NAME,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse("");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = favMoviesDbHelper.getWritableDatabase();

        int numOfRowsDeleted = sqLiteDatabase.delete(FavMoviesEntry.TABLE_NAME, selection, selectionArgs);

        return numOfRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
