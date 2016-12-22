package com.example.ivan.simpletvapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan on 13.12.2016..
 */

public class MoviesModel implements Parcelable {
    public static final Parcelable.Creator<MoviesModel> CREATOR = new Parcelable.Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel source) {
            return new MoviesModel(source);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
        }
    };
    private int page, totalResults, totalPages, moviesId, voteCount;
    private String posterPath, overview, releaseDate, originalTitle, originalLanguage, title;
    private double popularity, voteAverage;
    private boolean adoult, video;


    public MoviesModel(String posterPath, String releaseDate, String originalTitle, double voteAverage, int moviesId) {
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.moviesId = moviesId;
    }

    protected MoviesModel(Parcel in) {
        this.page = in.readInt();
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
        this.moviesId = in.readInt();
        this.voteCount = in.readInt();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.adoult = in.readByte() != 0;
        this.video = in.readByte() != 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(int moviesId) {
        this.moviesId = moviesId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isAdoult() {
        return adoult;
    }

    public void setAdoult(boolean adoult) {
        this.adoult = adoult;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
        dest.writeInt(this.moviesId);
        dest.writeInt(this.voteCount);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
        dest.writeByte(this.adoult ? (byte) 1 : (byte) 0);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
    }
}
