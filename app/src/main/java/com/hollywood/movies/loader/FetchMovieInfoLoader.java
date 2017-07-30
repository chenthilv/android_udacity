package com.hollywood.movies.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.hollywood.movies.MainFragment;
import com.hollywood.movies.adapter.MovieAdapter;
import com.hollywood.movies.data.FavMoviesContract;
import com.hollywood.movies.model.MovieDetailInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.tools.WebBrowser;

import static com.hollywood.movies.BuildConfig.API_KEY;

/**
 * Created by chenthil on 7/15/17.
 */

public class FetchMovieInfoLoader extends AsyncTaskLoader<List<MovieDetailInfo>> {


    private Bundle movieBundle;

    private Context context;

    private List<MovieDetailInfo> movieDetailInfo;

    private static final String[] FAV_PROJECTION = {
            FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_ID,
            FavMoviesContract.FavMoviesEntry.COLUMN_POSTER_PATH
    };

    public FetchMovieInfoLoader(Context context, Bundle bundle) {
        super(context);
        this.context = context;
        this.movieBundle = bundle;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if(movieDetailInfo != null){
            deliverResult(movieDetailInfo);
        }else{
            forceLoad();
        }

    }

    @Override
    public List<MovieDetailInfo> loadInBackground() {
        String sortBy = movieBundle.getString("sortBy");
        MovieResultsPage movieResultsPage = null;
        Cursor cursor = null;
        List<MovieDetailInfo> movieDetailInfoList = null;
        try {
            TmdbApi tmdbApi = new TmdbApi(API_KEY);
            TmdbMovies movies = tmdbApi.getMovies();
            if ("highlyRated".equals(sortBy)) {
                movieResultsPage = movies.getTopRatedMovies("en", 1);
                movieDetailInfoList = readMovieDbDetails(movieResultsPage);
            } else if ("popularMovies".equals(sortBy)) {
                movieResultsPage = movies.getPopularMovies("en", 1);
                movieDetailInfoList = readMovieDbDetails(movieResultsPage);
            } else if ("favouriteMovies".equals(sortBy)) {
                Uri query = FavMoviesContract.FavMoviesEntry.CONTENT_URI;
                cursor = getContext().getContentResolver().query(query,FAV_PROJECTION,null,null,null);
                movieDetailInfoList = readCursorDbDetails(cursor);
                Set<MovieDetailInfo> movieDetailInfoSet = new HashSet<>();
                movieDetailInfoSet.addAll(movieDetailInfoList);
                movieDetailInfoList = new ArrayList<>();
                movieDetailInfoList.addAll(movieDetailInfoSet);
            } else {
                movieResultsPage = movies.getNowPlayingMovies("en", 1);
                movieDetailInfoList = readMovieDbDetails(movieResultsPage);
            }

            return movieDetailInfoList;

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(List<MovieDetailInfo> movieDetailInfo) {
        this.movieDetailInfo = movieDetailInfo;
        super.deliverResult(movieDetailInfo);
    }


    private List<MovieDetailInfo> readMovieDbDetails(MovieResultsPage movieResultPage){

        List<MovieDetailInfo> movieDetailInfoList = new ArrayList<>();

        if(movieResultPage != null){

            for (MovieDb movieDb : movieResultPage.getResults()){
                MovieDetailInfo movieDetailInfo = new MovieDetailInfo();
                movieDetailInfo.setMovieId(movieDb.getId());
                movieDetailInfo.setTitle(movieDb.getTitle());
                movieDetailInfo.setMoviePosterPath(movieDb.getPosterPath());
                movieDetailInfo.setReleaseDate(movieDb.getReleaseDate());
                movieDetailInfo.setRunTime(movieDb.getRuntime());
                movieDetailInfo.setRating(movieDb.getUserRating());
                movieDetailInfo.setOverview(movieDb.getOverview());
                movieDetailInfoList.add(movieDetailInfo);
            }
        }
        return movieDetailInfoList;
    }


    private List<MovieDetailInfo> readCursorDbDetails(Cursor cursor){
        List<MovieDetailInfo> movieDetailInfoList = new ArrayList<>();

        if(cursor != null){

            for(int i=0;i<cursor.getCount();i++){
                if(cursor.moveToPosition(i)) {
                    MovieDetailInfo movieDetailInfo = new MovieDetailInfo();
                    movieDetailInfo.setMovieId(cursor.getInt(cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_ID)));
                    movieDetailInfo.setMoviePosterPath(cursor.getString(cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_POSTER_PATH)));
                    movieDetailInfoList.add(movieDetailInfo);
                }
            }

        }

        return movieDetailInfoList;
    }
}
