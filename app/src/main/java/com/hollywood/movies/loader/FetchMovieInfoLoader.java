package com.hollywood.movies.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.hollywood.movies.MainFragment;
import com.hollywood.movies.adapter.MovieAdapter;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.hollywood.movies.BuildConfig.API_KEY;

/**
 * Created by chenthil on 7/15/17.
 */

public class FetchMovieInfoLoader extends AsyncTaskLoader<MovieResultsPage> {


    private Bundle movieBundle;

    private Context context;

    private MovieResultsPage movieDbs;

    public FetchMovieInfoLoader(Context context, Bundle bundle) {
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
    public MovieResultsPage loadInBackground() {
        String sortBy = movieBundle.getString("sortBy");

        try {
            TmdbApi tmdbApi = new TmdbApi(API_KEY);
            TmdbMovies movies = tmdbApi.getMovies();
            if ("highlyRated".equals(sortBy)) {
                return movies.getTopRatedMovies("en", 1);
            } else if ("popularMovies".equals(sortBy)) {
                return movies.getPopularMovies("en", 1);
            } else if ("favouriteMovies".equals(sortBy)) {

                return movies.getPopularMovies("en", 1);
            } else {
                return movies.getNowPlayingMovies("en", 1);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(MovieResultsPage movies) {
        movieDbs = movies;
        super.deliverResult(movies);
    }
}
