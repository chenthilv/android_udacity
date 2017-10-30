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
import info.movito.themoviedbapi.model.Video;


/**
 * Created by chenthil on 7/15/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    TrailerViewHandler trailerViewHandler;

    public interface TrailerViewHandler{
        public void onTrailerClick(Video video);
    }

    public TrailerAdapter(TrailerViewHandler trailerViewHandler){
        this.trailerViewHandler = trailerViewHandler;
    }
    private List<Video> trailerList = new ArrayList<>();

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_fragment, parent, false );
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(trailerList.get(position),position);
    }

    public void loadTrailers(List<Video> trailerList){
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (trailerList.size() > 3)?3:trailerList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView trailerView;

        public TrailerViewHolder(View movieView){
            super(movieView);

            trailerView = (TextView) movieView.findViewById(R.id.trailer_name);
            movieView.setOnClickListener(this);
        }

        void bind(Video video,int position){

           trailerView.setText(video.getType() + " - " + (position + 1)+ System.lineSeparator());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Video video = trailerList.get(position);
            trailerViewHandler.onTrailerClick(video);

        }
    }
}
