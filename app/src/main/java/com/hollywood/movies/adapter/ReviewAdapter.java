package com.hollywood.movies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hollywood.movies.BuildConfig;
import com.hollywood.movies.R;
import com.squareup.picasso.Picasso;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by chenthil on 7/15/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewiewHolder> {


    @Override
    public ReviewiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ReviewiewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ReviewiewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView imageView;

        public ReviewiewHolder(View movieView){
            super(movieView);
            imageView = (ImageView) movieView.findViewById(R.id.movie_image_view);
            movieView.setOnClickListener(this);
        }

        void bind(MovieDb movieDb){


            Picasso.with(imageView.getContext())
                    .load(BuildConfig.POSTER_PATH+movieDb.getPosterPath())
                    .into(imageView);
            imageView.setVisibility(ImageView.VISIBLE);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

        }
    }
}
