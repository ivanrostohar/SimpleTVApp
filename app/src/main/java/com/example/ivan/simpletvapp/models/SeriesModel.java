package com.example.ivan.simpletvapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan on 9.12.2016..
 */

public class SeriesModel implements Parcelable {
    public static final Parcelable.Creator<SeriesModel> CREATOR = new Parcelable.Creator<SeriesModel>() {
        @Override
        public SeriesModel createFromParcel(Parcel source) {
            return new SeriesModel(source);
        }

        @Override
        public SeriesModel[] newArray(int size) {
            return new SeriesModel[size];
        }
    };
    private int page, totalPages, voteCount, id;
    private String posterPath, overview, airDate, language, name;
    private double voteAverage;


    public SeriesModel(double voteAverage, String posterPath, String airDate, String name, int id) {
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.airDate = airDate;
        this.name = name;
        this.id = id;
    }

    protected SeriesModel(Parcel in) {
        this.page = in.readInt();
        this.totalPages = in.readInt();
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.airDate = in.readString();
        this.language = in.readString();
        this.name = in.readString();
        this.voteAverage = in.readDouble();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalPages);
        dest.writeInt(this.voteCount);
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.airDate);
        dest.writeString(this.language);
        dest.writeString(this.name);
        dest.writeDouble(this.voteAverage);
    }
}
