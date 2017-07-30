package com.hollywood.movies.adapter;
import android.graphics.Movie;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hollywood.movies.BuildConfig;
import com.hollywood.movies.MainFragment;
import com.hollywood.movies.R;
import com.hollywood.movies.model.MovieDetailInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * Created by chenthil on 3/5/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {

    private MovieDetailViewHandler onClickHandler;

    private List<MovieDetailInfo> movieList = new ArrayList<>();

    public MovieAdapter(MovieDetailViewHandler onClickHandler){
        this.onClickHandler = onClickHandler;
    }


    public interface MovieDetailViewHandler{
        public void onMovieClick(MovieDetailInfo movieDetailInfo );
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list, parent, false );
        return new MoviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviewViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    public void loadMovies(List<MovieDetailInfo> movies){
        movieList = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    class MoviewViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView imageView;

        public MoviewViewHolder(View movieView){
            super(movieView);
            imageView = (ImageView) movieView.findViewById(R.id.movie_image_view);
            movieView.setOnClickListener(this);
        }

        void bind(MovieDetailInfo movieDetailInfo){

            Picasso.with(imageView.getContext())
                    .load(BuildConfig.POSTER_PATH+movieDetailInfo.getMoviePosterPath())
                    .into(imageView);
            imageView.setVisibility(ImageView.VISIBLE);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MovieDetailInfo movieDetailInfo =  movieList.get(position);
            onClickHandler.onMovieClick(movieDetailInfo);
        }
    }


}
