package com.hollywood.movies;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hollywood.movies.adapter.ReviewAdapter;

import com.hollywood.movies.model.MovieDetailInfo;
import com.squareup.picasso.Picasso;

import info.movito.themoviedbapi.model.MovieDb;

import static com.hollywood.movies.R.id.progressBar;

/**
 * Created by chenthil on 7/16/17.
 */

public class MovieDetailInfoFragment extends Fragment {


    private ProgressBar progressBar;

    private ReviewAdapter reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        System.out.println("****Movie Detail Info *****");
        View fragmentView = inflater.inflate(R.layout.activity_movie_detail_fragment, container, false);

        RecyclerView reviewRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.review_recyler_view);

        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);

        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBarDetailView);

        loadMovieInfoFragment(fragmentView);
        return fragmentView;
    }

    private void loadMovieInfoFragment(View fragmentView){

        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getActivity().getIntent();

        MovieDetailInfo movieDetailInfo = null;

        if(intent.hasExtra(Intent.EXTRA_TEXT)){


            movieDetailInfo = intent.getParcelableExtra(Intent.EXTRA_TEXT);

        }

        System.out.println("movieDetailInfo.getRunTime() *****"+movieDetailInfo.getRunTime());
        ((TextView)fragmentView.findViewById(R.id.movie_time)).setText(String.valueOf(movieDetailInfo.getRunTime()));
      /*  mActivityMovieDetail.movieInfo.movieTime.setText(String.valueOf(movieDetailInfo.getRunTime()));
        Picasso.with(mActivityMovieDetail.movieInfo.movieImageView.getContext())
                .load(BuildConfig.POSTER_PATH+movieDetailInfo.getMoviePosterPath())
                .into(mActivityMovieDetail.movieInfo.movieImageView);
        mActivityMovieDetail.movieInfo.movieRating.setText(String.valueOf(movieDetailInfo.getRating()));
        mActivityMovieDetail.movieInfo.movieReleaseDate.setText(movieDetailInfo.getReleaseDate());
        mActivityMovieDetail.trailerInfo.movieOverview.setText(movieDetailInfo.getOverview());
        mActivityMovieDetail.movieInfo.movieHeadline.setText(movieDetailInfo.getTitle());*/

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void loadTrailerFragment(){}

    private void loadReviewFragment(){}
}
