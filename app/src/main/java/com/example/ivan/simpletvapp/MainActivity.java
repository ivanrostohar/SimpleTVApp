package com.example.ivan.simpletvapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String API_KEY = BuildConfig.MOVIES_API_KEY;
        Log.v("API_KEY", API_KEY);
    }
}
