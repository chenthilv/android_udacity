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

import java.util.ArrayList;
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
    private static final String SAVED_MOVIES = "SAVED_MOVIES";
    private static final String SAVED_MOVIES_SORT_BY = "sortBy";
    private String sortBy;
    private LoaderManager loaderManager;

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

        loaderManager = getActivity().getLoaderManager();

        System.out.println("*****on Create view called ******"+savedInstanceState);

        if(savedInstanceState != null){

            System.out.println("*********Instance state is not null...retrieving from cache********");
            sortBy = savedInstanceState.getString(SAVED_MOVIES_SORT_BY);
            if(savedInstanceState.containsKey(SAVED_MOVIES)){
                List<MovieDetailInfo> movieDetailInfoList = savedInstanceState.getParcelableArrayList(SAVED_MOVIES);
                movieAdapter.loadMovies(movieDetailInfoList);

            }
        }else {
            loadMovies(null);
        }

        return fragmentView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        System.out.println("Saving instance state*****");
        ArrayList<MovieDetailInfo> movieDetailInfoList = (ArrayList)movieAdapter.getMovieList();

        if(movieDetailInfoList != null && !movieDetailInfoList.isEmpty()){
            outState.putParcelableArrayList(SAVED_MOVIES,movieDetailInfoList);
        }
        outState.putString(SAVED_MOVIES_SORT_BY,this.sortBy);

    }

    public void loadMovies(String sortBy){

        this.sortBy = sortBy;
        Bundle bundle = new Bundle();
        bundle.putString("sortBy", sortBy);
        progressBar.setVisibility(View.VISIBLE);

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
        loaderManager.destroyLoader(MOVIE_IMAGE_LOADER);
    }

}
