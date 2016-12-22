package com.example.ivan.simpletvapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.models.MoviesDetails;
import com.example.ivan.simpletvapp.models.MoviesModel;
import com.example.ivan.simpletvapp.other.AppController;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    MoviesModel moviesObject;
    MoviesDetails moviesDetails;
    ArrayList<String> genresName = new ArrayList<>();
    ArrayList<String> productionCompaniesArray = new ArrayList<>();
    private String url = "https://api.themoviedb.org/3/movie/";
    private String movies_url;
    private Toolbar toolbarDetails;
    private CollapsingToolbarLayout collapsingToolbarDetails;
    private TextView txt_title_details, txt_overview_details, txt_budget_details, txt_revenue_details, txt_genres_details, txt_runtime_details, txt_production_companies;
    private ImageView img_movies_details;
    private NestedScrollView nsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        moviesObject = getIntent().getParcelableExtra("movies_object");

        downloadMoviesDetails(url, moviesObject.getMoviesId());

        toolbarDetails = (Toolbar) findViewById(R.id.toolbar_details);
        collapsingToolbarDetails = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        txt_title_details = (TextView) findViewById(R.id.txt_title_details);
        txt_overview_details = (TextView) findViewById(R.id.txt_overview_details);
        txt_budget_details = (TextView) findViewById(R.id.txt_budget_details);
        txt_revenue_details = (TextView) findViewById(R.id.txt_revenue_details);
        txt_genres_details = (TextView) findViewById(R.id.txt_genres_details);
        txt_runtime_details = (TextView) findViewById(R.id.txt_runtime_details);
        txt_production_companies = (TextView) findViewById(R.id.txt_production_companies);
        img_movies_details = (ImageView) findViewById(R.id.img_sm_details);
        nsv = (NestedScrollView) findViewById(R.id.nested_scroll_view_ms);

        setSupportActionBar(toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img_movies_details.setOnClickListener(this);
    }

    private void downloadMoviesDetails(String url, int id) {
        String url_final = url + id + "?api_key=" + MOVIES_API_KEY;
        Log.v("URL", url_final);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_final, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                        genresName.add(response.getJSONArray("genres").getJSONObject(i).getString("name"));
                    }
                    for (int i = 0; i < response.getJSONArray("production_companies").length(); i++) {
                        productionCompaniesArray.add(response.getJSONArray("production_companies").getJSONObject(i).getString("name"));
                    }
                    moviesDetails = new MoviesDetails(
                            genresName,
                            response.getString("homepage"),
                            response.getString("original_language"),
                            response.getString("original_title"),
                            response.getString("overview"),
                            response.getString("poster_path"),
                            productionCompaniesArray,
                            response.getString("release_date"),
                            response.getString("status"),
                            response.getLong("budget"),
                            response.getDouble("popularity"),
                            response.getLong("revenue"),
                            response.getDouble("vote_average"),
                            response.getDouble("vote_count"),
                            response.getInt("runtime")
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    movies_url = moviesDetails.getHomepage();
                    Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "28_Days_Later.ttf");
                    txt_title_details.setText(moviesDetails.getOriginal_title());
                    txt_title_details.setTypeface(custom_font);
                    txt_overview_details.setText(moviesDetails.getOverview());
                    txt_budget_details.setText(String.valueOf(moviesDetails.getBudget()) + " $");
                    txt_revenue_details.setText(String.valueOf(moviesDetails.getRevenue()) + " $");
                    txt_runtime_details.setText(String.valueOf(moviesDetails.getRuntime()) + " min");
                    StringBuffer genres = new StringBuffer();
                    for (int i = 0; i < moviesDetails.getGenres().size(); i++) {
                        genres.append(moviesDetails.getGenres().get(i) + System.getProperty("line.separator"));
                    }
                    StringBuffer production_comapnies = new StringBuffer();
                    for (int i = 0; i < moviesDetails.getProduction_companies().size(); i++) {
                        production_comapnies.append(moviesDetails.getProduction_companies().get(i) + System.getProperty("line.separator"));
                    }
                    txt_production_companies.setText(production_comapnies);
                    txt_genres_details.setText(genres);
                    collapsingToolbarDetails.setTitle(moviesDetails.getOriginal_title());
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + moviesDetails.getPoster_path()).into(img_movies_details, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap drawable = ((BitmapDrawable) img_movies_details.getDrawable()).getBitmap();
                            Palette.Builder builder = new Palette.Builder(drawable);
                            builder.generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                                    Palette.Swatch vibrantDark = palette.getLightMutedSwatch();
                                    if (vibrant != null) {
                                        try {
                                            nsv.setBackgroundColor(vibrant.getRgb());
                                            txt_genres_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_runtime_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_title_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_overview_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_budget_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_revenue_details.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_production_companies.setTextColor(vibrantDark.getTitleTextColor());
                                        } catch (Exception e) {
                                            e.getLocalizedMessage();
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError() {
                            Snackbar.make(findViewById(android.R.id.content), "Error in image colour extracting!", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getLocalizedMessage();
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (movies_url == null) {
            Snackbar.make(findViewById(android.R.id.content), "No url provided", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.addCategory(Intent.CATEGORY_BROWSABLE);
            i.setData(Uri.parse(movies_url));
            startActivity(i);
        }
    }
}
