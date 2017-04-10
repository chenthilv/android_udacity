package com.hollywood.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hollywood.movies.model.MovieDetailInfo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieDetailInfoActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_info);

        progressBar = (ProgressBar) findViewById(R.id.progressBarDetailView);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();


        MovieDetailInfo movieDetailInfo = null;
        if(intent.hasExtra(Intent.EXTRA_TEXT)){

            movieDetailInfo = intent.getParcelableExtra(Intent.EXTRA_TEXT);
        }

       TextView titleView = (TextView) findViewById(R.id.movie_title);
        ImageView movieImageView = (ImageView) findViewById(R.id.movie_image_view);
        TextView movieRatingView = (TextView) findViewById(R.id.movie_rating);
        TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_release_date);

        titleView.setText(getResString(R.string.title)+movieDetailInfo.getTitle());

        Picasso.with(movieImageView.getContext())
                .load(BuildConfig.POSTER_PATH+movieDetailInfo.getMoviePosterPath())
                .into(movieImageView);

        movieRatingView.setText(getResString(R.string.user_rating)+String.valueOf(movieDetailInfo.getRating()));
        movieOverview.setText(getResString(R.string.overview) +movieDetailInfo.getOverview());
        movieReleaseDate.setText(getResString(R.string.release_date)+movieDetailInfo.getReleaseDate());
        progressBar.setVisibility(View.INVISIBLE);

    }

    public String getResString(int resourceId){
        return getResources().getString(resourceId);
    }

}
