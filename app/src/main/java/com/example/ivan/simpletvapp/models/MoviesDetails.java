package com.example.ivan.simpletvapp.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Ivan on 19.12.2016..
 */

public class MoviesDetails {
    private String original_language, original_title, overview, poster_path, release_date, status, homepage;
    private double popularity, vote_average, vote_count;
    private long budget;
    private long revenue;
    private int runtime;
    private ArrayList<String> genres, production_companies;

    public MoviesDetails(ArrayList<String> genres, String homepage, String original_language, String original_title, String overview, String poster_path,
                         ArrayList<String> production_companies, String release_date, String status, long budget, double popularity, long revenue,
                         double vote_average, double vote_count, int runtime) {
        this.genres = genres;
        this.homepage = homepage;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.release_date = release_date;
        this.status = status;
        this.budget = budget;
        this.popularity = popularity;
        this.revenue = revenue;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.runtime = runtime;
        this.homepage = homepage;
    }


    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<String> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<String> production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getVote_count() {
        return vote_count;
    }

    public void setVote_count(double vote_count) {
        this.vote_count = vote_count;
    }
}
