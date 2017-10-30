package com.hollywood.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hollywood.movies.BuildConfig;
import com.hollywood.movies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Reviews;

/**
 * Created by chenthil on 7/15/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewiewHolder> {

    private List<Reviews> reviewsList = new ArrayList<>();

    @Override
    public ReviewiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_fragment, parent, false );
        return new ReviewiewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewiewHolder holder, int position) {
        holder.bind(reviewsList.get(position));

    }

    public void loadReviews(List<Reviews> reviewsList){
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    class ReviewiewHolder extends RecyclerView.ViewHolder{

        TextView reviewAuthor;
        TextView reviewContent;


        public ReviewiewHolder(View reviewView){
            super(reviewView);
            reviewAuthor = (TextView) reviewView.findViewById(R.id.review_author);
            reviewContent = (TextView) reviewView.findViewById(R.id.review_content);
        }

        void bind(Reviews reviews){
            System.out.println("Review : "+reviews.getId());
            reviewAuthor.setText(reviews.getAuthor());
            reviewContent.setText(reviews.getContent());
        }

    }
}
