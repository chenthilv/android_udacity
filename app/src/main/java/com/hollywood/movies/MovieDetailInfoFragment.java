package com.hollywood.movies;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hollywood.movies.adapter.ReviewAdapter;

import com.hollywood.movies.adapter.TrailerAdapter;
import com.hollywood.movies.data.FavMoviesContract;
import com.hollywood.movies.loader.FetchMoreMovieInfoLoader;
import com.hollywood.movies.model.MovieDetailInfo;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static android.R.attr.data;
import static android.R.attr.id;
import static com.hollywood.movies.R.id.progressBar;
import static java.lang.System.load;
import com.hollywood.movies.data.FavMoviesContract.FavMoviesEntry;

/**
 * Created by chenthil on 7/16/17.
 */

public class MovieDetailInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieDb>, TrailerAdapter.TrailerViewHandler {


    private View fragmentView;

    private ProgressBar progressBar;

    private ReviewAdapter reviewAdapter;

    private TrailerAdapter trailerAdapter;

    private static final int MOVIE_REVIEW_LOADER = 101;

    private static final int MOVIE_TRAILER_LOADER = 102;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.activity_movie_detail_fragment, container, false);

        RecyclerView reviewRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.review_recyler_view);
        RecyclerView trailerRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.trailer_recyler_view);

        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);

        trailerAdapter = new TrailerAdapter(this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(reviewLinearLayoutManager);
        LinearLayoutManager trailerLinearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL, false);
        trailerRecyclerView.setLayoutManager(trailerLinearLayoutManager);

        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBarDetailView);

        Intent intent = getActivity().getIntent();

        int movieId = 0;

        if(intent.hasExtra("movieId")){
            movieId = intent.getIntExtra("movieId",0);
        }

        System.out.println("Movie Id*******************"+movieId);

        LoaderManager loaderManager = getActivity().getLoaderManager();

        Bundle bundle = new Bundle();
        bundle.putInt("movieId", movieId);

        loaderManager.initLoader(MOVIE_REVIEW_LOADER, bundle, this);

        return fragmentView;
    }

    private void loadMovieInfoFragment(final MovieDb movieDb){


        ((TextView)fragmentView.findViewById(R.id.movie_time)).setText(String.valueOf(movieDb.getRuntime()));

        ImageView movieImageView = ((ImageView)fragmentView.findViewById(R.id.movie_image_view));

        Picasso.with(movieImageView.getContext())
                .load(BuildConfig.POSTER_PATH+movieDb.getPosterPath())
                .into(movieImageView);

        ((TextView)fragmentView.findViewById(R.id.movie_rating)).setText(String.valueOf(movieDb.getUserRating()));
        ((TextView)fragmentView.findViewById(R.id.movie_release_date)).setText(movieDb.getReleaseDate());
        ((TextView)fragmentView.findViewById(R.id.movie_headline)).setText(movieDb.getTitle());
        ((TextView)fragmentView.findViewById(R.id.movie_overview)).setText(movieDb.getOverview());
        ImageView favImageView = (ImageView) fragmentView.findViewById(R.id.favorite_button);

        favImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                saveFavouriteMovies(movieDb);
            }
        });

    }

    private void saveFavouriteMovies(MovieDb movieDb){
        System.out.println("Favorite movie---->"+movieDb.getTitle());
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavMoviesEntry.COLUMN_MOVIE_ID, movieDb.getId());
/*        contentValues.put(FavMoviesEntry.COLUMN_POSTER_PATH, movieDb.getPosterPath());
        contentValues.put(FavMoviesEntry.COLUMN_RATING, movieDb.getUserRating());
        contentValues.put(FavMoviesEntry.COLUMN_RUN_TIME, movieDb.getRuntime());
        contentValues.put(FavMoviesEntry.COLUMN_RELEASE_DATE, movieDb.getReleaseDate());
        contentValues.put(FavMoviesEntry.COLUMN_TITLE, movieDb.getTitle());
        contentValues.put(FavMoviesEntry.COLUMN_REVIEWS, movieDb.getReviews());*/

    }


    @Override
    public Loader<MovieDb> onCreateLoader(int id, Bundle args) {
        return new FetchMoreMovieInfoLoader(this.getContext(),args);
    }

    @Override
    public void onLoadFinished(Loader<MovieDb> loader, MovieDb data) {
        if(data == null){

        }else{
            loadMovieInfoFragment(data);
            reviewAdapter.loadReviews(data.getReviews());
            trailerAdapter.loadTrailers(data.getVideos());
            progressBar.setVisibility(View.INVISIBLE);

//            System.out.println("Reviews*******"+data.getTitle());
//            System.out.println("Reviews*******"+data.getPosterPath());
//            System.out.println("Reviews*******"+data.getReleaseDate());
//            System.out.println("Reviews*******"+data.getOverview());
//            System.out.println("Reviews*******"+data.getRuntime());
//
            System.out.println("Videos*******"+data.getVideos().get(0).getId());
//            System.out.println("Reviews*******"+data.getReviews());
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieDb> loader) {

    }

    @Override
    public void onTrailerClick(Video video) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
