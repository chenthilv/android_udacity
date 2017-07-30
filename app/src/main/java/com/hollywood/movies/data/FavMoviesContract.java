package com.hollywood.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by chenthil on 7/23/17.
 */

public class FavMoviesContract {

    public static final String CONTENT_AUTHORITY = "com.hollywood.movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_FAV_MOVIES = "favmovies";

    public static final class FavMoviesEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIES).build();

        public static final String TABLE_NAME =  "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /*public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RUN_TIME = "run_time";
        public static final String COLUMN_REVIEWS = "reviews";
        public static final String COLUMN_VIDEOS = "videos";
        */


    }

}
