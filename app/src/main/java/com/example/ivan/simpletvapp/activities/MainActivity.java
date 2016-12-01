package com.example.ivan.simpletvapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.models.MoviesResponse;
import com.example.ivan.simpletvapp.services.MoviesAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesAPI.GetClient.getInstance().getPopularMovies(MOVIES_API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int odgovor = response.body().getTotalPages();
                Log.v("ODGOVOR", String.valueOf(odgovor));

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.v("PROMAŠAJ", t.getLocalizedMessage());

            }
        });


    }
}
