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
import android.widget.TextView;
import android.widget.Toast;

import com.hollywood.movies.adapter.MovieAdapter;
import com.hollywood.movies.model.MovieDetailInfo;

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

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_main,container,false);

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyler_view);

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
        new FetchMovieTask().execute(soryBy, API_KEY);
    }

    class FetchMovieTask extends AsyncTask<String, String, MovieResultsPage>{


        @Override
        protected MovieResultsPage doInBackground(String... params) {

            TmdbApi tmdbApi = new TmdbApi(params[1]);
            TmdbMovies movies = tmdbApi.getMovies();
            if(params[0].equals("highlyRated")){
                return movies.getTopRatedMovies("en",1);
            }else if(params[0].equals("popularMovies")){
                return movies.getPopularMovies("en",1);
            }else {

                return movies.getNowPlayingMovies("en", 1);
            }

        }

        @Override
        protected void onPostExecute(MovieResultsPage movies){
            movieAdapter.loadMovies(movies);

        }
    }

}
