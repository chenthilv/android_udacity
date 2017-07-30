package com.hollywood.movies;



import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;

import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.hollywood.movies.adapter.MovieAdapter;
import com.hollywood.movies.loader.FetchMovieInfoLoader;
import com.hollywood.movies.model.MovieDetailInfo;
import com.hollywood.movies.task.AsyncTaskCompleteListener;
import com.hollywood.movies.task.FetchMoviesTask;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static android.R.attr.data;
import static com.hollywood.movies.R.id.progressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements
        MovieAdapter.MovieDetailViewHandler, LoaderManager.LoaderCallbacks<List<MovieDetailInfo>>
        {


    private MovieAdapter movieAdapter;
    String API_KEY = BuildConfig.API_KEY;
    private ProgressBar progressBar;

    private static final int MOVIE_IMAGE_LOADER = 100;

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

        loadMovies(null);

        return fragmentView;
    }


    public void loadMovies(String sortBy){
        LoaderManager loaderManager = getActivity().getLoaderManager();

        Bundle bundle = new Bundle();
        bundle.putString("sortBy", sortBy);

        progressBar.setVisibility(View.VISIBLE);

        System.out.println("sory by-----"+loaderManager.getLoader(MOVIE_IMAGE_LOADER));
        if(loaderManager.getLoader(MOVIE_IMAGE_LOADER) == null) {
            loaderManager.initLoader(MOVIE_IMAGE_LOADER, bundle, this);
        }else{
            loaderManager.restartLoader(MOVIE_IMAGE_LOADER,bundle,this);
        }
    }

    @Override
    public void onMovieClick(MovieDetailInfo movieDetailInfo) {

        Context context = this.getContext();

        Class<MovieDetailInfoActivity> movieDetailInfoActivityClass = MovieDetailInfoActivity.class;

        Intent intent = new Intent(context,movieDetailInfoActivityClass);

        intent.putExtra("movieId",movieDetailInfo.getMovieId());

        startActivity(intent);

    }


    @Override
    public Loader<List<MovieDetailInfo>> onCreateLoader(int id, Bundle args) {
        return new FetchMovieInfoLoader(this.getContext(),args);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetailInfo>> loader, List<MovieDetailInfo> data) {

        progressBar.setVisibility(View.INVISIBLE);
        if(data != null) {
            movieAdapter.loadMovies(data);
        }else{
            Toast.makeText(this.getContext(), "Movies list is empty",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetailInfo>> loader) {

    }

}
