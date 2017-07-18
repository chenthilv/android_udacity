package com.hollywood.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenthil on 3/26/17.
 */

public class MovieDetailInfo implements Parcelable {

    private long movieId;
    private String title;
    private float rating;
    private String moviePosterPath;
    private String overview;
    private String releaseDate;
    private int runTime;

    public MovieDetailInfo(){}

    protected MovieDetailInfo(Parcel in) {
        movieId = in.readLong();
        title = in.readString();
        rating = in.readFloat();
        moviePosterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        runTime = in.readInt();
    }

    public static final Creator<MovieDetailInfo> CREATOR = new Creator<MovieDetailInfo>() {
        @Override
        public MovieDetailInfo createFromParcel(Parcel in) {
            return new MovieDetailInfo(in);
        }

        @Override
        public MovieDetailInfo[] newArray(int size) {
            return new MovieDetailInfo[size];
        }
    };

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public float getRating() {
        return rating;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(title);
        dest.writeFloat(rating);
        dest.writeString(moviePosterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(runTime);
    }
}
