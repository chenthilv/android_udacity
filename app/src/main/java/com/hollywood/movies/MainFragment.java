package com.hollywood.movies;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hollywood.movies.adapter.MovieAdapter;
import com.hollywood.movies.model.MovieDetailInfo;
import com.hollywood.movies.task.AsyncTaskCompleteListener;
import com.hollywood.movies.task.FetchMoviesTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MovieAdapter.MovieDetailViewHandler{


    private MovieAdapter movieAdapter;
    String API_KEY = BuildConfig.API_KEY;
    private ProgressBar progressBar;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_main,container,false);

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyler_view);

        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2);

        recyclerView.setLayoutManager(gridLayoutManager);

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        sortMovies("");
        return fragmentView;
    }

    @Override
    public void onMovieClick(MovieDb movieDb) {


        Context context = this.getContext();

        Class<MovieDetailInfoActivity> movieDetailInfoActivityClass = MovieDetailInfoActivity.class;

        Intent intent = new Intent(context,movieDetailInfoActivityClass);

        MovieDetailInfo movieDetailInfo = new MovieDetailInfo();
        movieDetailInfo.setTitle(movieDb.getTitle());
        movieDetailInfo.setMoviePosterPath(movieDb.getPosterPath());
        movieDetailInfo.setOverview(movieDb.getOverview());
        movieDetailInfo.setRating(movieDb.getVoteAverage());
        movieDetailInfo.setReleaseDate(movieDb.getReleaseDate());
        intent.putExtra(Intent.EXTRA_TEXT,movieDetailInfo);

        startActivity(intent);

    }

    public void sortMovies(String soryBy){
        progressBar.setVisibility(View.VISIBLE);
        new FetchMoviesTask(this.getContext(),new FetchMoviesOnTaskCompleteListener()).execute(soryBy, API_KEY);
    }



    // Fetch the movies using async task

    public class FetchMoviesOnTaskCompleteListener implements AsyncTaskCompleteListener {
        @Override
        public void onTaskComplete(Object result) {

            if(result instanceof MovieResultsPage){
                MovieResultsPage movies = (MovieResultsPage) result;
                movieAdapter.loadMovies(movies);
            }
            progressBar.setVisibility(View.INVISIBLE);

        }
    }



}
