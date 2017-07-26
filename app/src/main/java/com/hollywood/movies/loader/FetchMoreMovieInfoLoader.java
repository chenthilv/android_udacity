package com.hollywood.movies.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Reviews;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.hollywood.movies.BuildConfig.API_KEY;

/**
 * Created by chenthil on 7/15/17.
 */

public class FetchMoreMovieInfoLoader extends AsyncTaskLoader<MovieDb> {


    private Bundle movieBundle;

    private Context context;

    private MovieDb movieDbs;

    public FetchMoreMovieInfoLoader(Context context, Bundle bundle) {
        super(context);
        this.context = context;
        this.movieBundle = bundle;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if(movieDbs != null){
            deliverResult(movieDbs);
        }else{
            forceLoad();
        }

    }

    @Override
    public MovieDb loadInBackground() {
        int movieId = movieBundle.getInt("movieId");

        System.out.println("Movie Id**********"+movieId);
        try {
            TmdbApi tmdbApi = new TmdbApi(API_KEY);
            MovieDb movies = tmdbApi.getMovies().getMovie(movieId, "en", TmdbMovies.MovieMethod.reviews, TmdbMovies.MovieMethod.videos);
            return movies;

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(MovieDb movies) {
        movieDbs = movies;
        super.deliverResult(movies);
    }
}
