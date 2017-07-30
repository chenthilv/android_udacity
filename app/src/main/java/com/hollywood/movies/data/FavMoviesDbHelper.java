package com.hollywood.movies.data;

import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.hollywood.movies.data.FavMoviesContract.FavMoviesEntry;

/**
 * Created by chenthil on 7/23/17.
 */

public class FavMoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favmovies.db";
    public static final int DATABASE_VERSION = 2;


    public FavMoviesDbHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE "+
                FavMoviesEntry.TABLE_NAME + "( " +
                                                FavMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                FavMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                                                FavMoviesEntry.COLUMN_POSTER_PATH + " TEXT );" ;

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FavMoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
