package com.hollywood.movies.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.hollywood.movies.R.id.progressBar;


/**
 * Created by chenthil on 4/8/17.
 */

public class FetchMoviesTask extends AsyncTask<String, String, MovieResultsPage>{

    private static final String TAG="FetchMoviesTask";
    private Context context;
    private AsyncTaskCompleteListener asyncTaskCompleteListener;


    public FetchMoviesTask(Context context, AsyncTaskCompleteListener asyncTaskCompleteListener) {
        this.context = context;
        this.asyncTaskCompleteListener = asyncTaskCompleteListener;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MovieResultsPage doInBackground(String... params) {

        try {
            TmdbApi tmdbApi = new TmdbApi(params[1]);
            TmdbMovies movies = tmdbApi.getMovies();
            if (params[0].equals("highlyRated")) {
                return movies.getTopRatedMovies("en", 1);
            } else if (params[0].equals("popularMovies")) {
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
    protected void onPostExecute(MovieResultsPage movies){
        super.onPostExecute(movies);
        if(movies != null) {
            asyncTaskCompleteListener.onTaskComplete(movies);
        }else{
            Toast.makeText(context, "Movies list is empty",Toast.LENGTH_LONG).show();
        }


    }
}

