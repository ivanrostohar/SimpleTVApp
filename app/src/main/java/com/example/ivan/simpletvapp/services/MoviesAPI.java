package com.example.ivan.simpletvapp.services;

import com.example.ivan.simpletvapp.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ivan on 1.12.2016..
 */

public interface MoviesAPI {

    //https://api.themoviedb.org/3/movie/now_playing?api_key=7dd608256b9c1a213469b6675f0d6b6f&page=2

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);



    public class GetClient {

        public static String BASE_URL = "https://api.themoviedb.org/3/";

        private static MoviesAPI service;

        public static MoviesAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(MoviesAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }
}
