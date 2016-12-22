package com.example.ivan.simpletvapp.models;

import java.util.ArrayList;

/**
 * Created by Ivan on 22.12.2016..
 */

public class SeriesDetails {
    private ArrayList<String> created_by, genres, networks, production_companies, seasons;
    private String first_air_date, homepage, last_air_date, name, original_language, original_name, overview, poster_path, status;
    private int number_of_episodes, number_of_seasons;

    public SeriesDetails(ArrayList<String> created_by, ArrayList<String> genres, ArrayList<String> networks, ArrayList<String> production_companies,
                         ArrayList<String> seasons, String first_air_date, String homepage, String last_air_date, String name, String original_language,
                         String original_name, String overview, String poster_path, String status, int number_of_episodes, int number_of_seasons) {
        this.created_by = created_by;
        this.genres = genres;
        this.networks = networks;
        this.production_companies = production_companies;
        this.seasons = seasons;
        this.first_air_date = first_air_date;
        this.homepage = homepage;
        this.last_air_date = last_air_date;
        this.name = name;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.status = status;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
    }

    public ArrayList<String> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ArrayList<String> created_by) {
        this.created_by = created_by;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<String> networks) {
        this.networks = networks;
    }

    public ArrayList<String> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<String> production_companies) {
        this.production_companies = production_companies;
    }

    public ArrayList<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<String> seasons) {
        this.seasons = seasons;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }
}
