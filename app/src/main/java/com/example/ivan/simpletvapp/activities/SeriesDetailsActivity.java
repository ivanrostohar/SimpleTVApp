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
import com.example.ivan.simpletvapp.models.SeriesDetails;
import com.example.ivan.simpletvapp.models.SeriesModel;
import com.example.ivan.simpletvapp.other.AppController;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    private String url = "https://api.themoviedb.org/3/tv/";

    private String series_url;
    private SeriesModel seriesObject;
    private TextView txt_sd_name, txt_sd_overview, txt_sd_status, txt_sd_created_by, txt_sd_genres, txt_sd_networks,
            txt_sd_production_companies, txt_sd_nbr_seasons, txt_sd_nbr_episodes, txt_first_last_air_date;
    private ImageView img_sd_poster;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbarSeriesDetails;
    private NestedScrollView nsv;
    private SeriesDetails seriesDetailsObject;
    private ArrayList<String> created_by = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<String> networks = new ArrayList<>();
    private ArrayList<String> production_companies = new ArrayList<>();
    private StringBuffer created_by_buffer, genres_buffer, networks_buffer, production_companies_buffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details);

        seriesObject = getIntent().getParcelableExtra("series_object");

        downloadSeriesDetails(url, seriesObject.getId());

        toolbarSeriesDetails = (Toolbar) findViewById(R.id.toolbar_details);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        nsv = (NestedScrollView) findViewById(R.id.nested_scroll_view_series);
        txt_sd_name = (TextView) findViewById(R.id.txt_title_details);
        txt_sd_overview = (TextView) findViewById(R.id.txt_overview_details);
        txt_sd_status = (TextView) findViewById(R.id.txt_series_status);
        txt_sd_created_by = (TextView) findViewById(R.id.txt_created_by);
        txt_sd_genres = (TextView) findViewById(R.id.txt_genres_details);
        txt_sd_networks = (TextView) findViewById(R.id.txt_network_details);
        txt_sd_production_companies = (TextView) findViewById(R.id.txt_production_companies);
        txt_sd_nbr_episodes = (TextView) findViewById(R.id.txt_number_of_episodes);
        txt_sd_nbr_seasons = (TextView) findViewById(R.id.txt_number_of_seasons);
        txt_first_last_air_date = (TextView) findViewById(R.id.txt_first_last_air_date);
        img_sd_poster = (ImageView) findViewById(R.id.img_series_details);

        img_sd_poster.setOnClickListener(this);

        setSupportActionBar(toolbarSeriesDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void downloadSeriesDetails(String url, int id) {
        String url_final = url + id + "?api_key=" + MOVIES_API_KEY;
        Log.v("URL", url_final);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_final, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.getJSONArray("created_by").length(); i++) {
                        created_by.add(response.getJSONArray("created_by").getJSONObject(i).getString("name"));
                    }
                    for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                        genres.add(response.getJSONArray("genres").getJSONObject(i).getString("name"));
                    }
                    for (int i = 0; i < response.getJSONArray("networks").length(); i++) {
                        networks.add(response.getJSONArray("networks").getJSONObject(i).getString("name"));
                    }
                    for (int i = 0; i < response.getJSONArray("production_companies").length(); i++) {
                        production_companies.add(response.getJSONArray("production_companies").getJSONObject(i).getString("name"));
                    }

                    seriesDetailsObject = new SeriesDetails(
                            created_by,
                            genres,
                            networks,
                            production_companies,
                            null,
                            response.getString("first_air_date"),
                            response.getString("homepage"),
                            response.getString("last_air_date"),
                            response.getString("name"),
                            response.getString("original_language"),
                            response.getString("original_name"),
                            response.getString("overview"),
                            response.getString("poster_path"),
                            response.getString("status"),
                            response.getInt("number_of_episodes"),
                            response.getInt("number_of_seasons")
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    created_by_buffer = new StringBuffer();
                    for (int i = 0; i < created_by.size(); i++) {
                        created_by_buffer.append(created_by.get(i) + System.getProperty("line.separator"));
                    }
                    genres_buffer = new StringBuffer();
                    for (int i = 0; i < genres.size(); i++) {
                        genres_buffer.append(genres.get(i) + System.getProperty("line.separator"));
                    }
                    networks_buffer = new StringBuffer();
                    for (int i = 0; i < networks.size(); i++) {
                        networks_buffer.append(networks.get(i) + System.getProperty("line.separator"));
                    }
                    production_companies_buffer = new StringBuffer();
                    for (int i = 0; i < production_companies.size(); i++) {
                        production_companies_buffer.append(production_companies.get(i) + System.getProperty("line.separator"));
                    }

                    Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "28_Days_Later.ttf");
                    series_url = seriesDetailsObject.getHomepage();
                    txt_sd_name.setText(seriesDetailsObject.getOriginal_name());
                    txt_sd_name.setTypeface(custom_font);
                    txt_sd_overview.setText(seriesDetailsObject.getOverview());
                    txt_sd_status.setText(seriesDetailsObject.getStatus());
                    txt_sd_created_by.setText(created_by_buffer);
                    txt_sd_genres.setText(genres_buffer);
                    txt_sd_networks.setText(networks_buffer);
                    txt_sd_production_companies.setText(production_companies_buffer);
                    txt_sd_nbr_episodes.setText(String.valueOf(seriesDetailsObject.getNumber_of_episodes()));
                    txt_sd_nbr_seasons.setText(String.valueOf(seriesDetailsObject.getNumber_of_seasons()));
                    txt_first_last_air_date.setText(seriesDetailsObject.getFirst_air_date() + " / " + seriesDetailsObject.getLast_air_date());
                    collapsingToolbarLayout.setTitle(seriesDetailsObject.getName());

                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + seriesDetailsObject.getPoster_path()).into(img_sd_poster, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap drawable = ((BitmapDrawable) img_sd_poster.getDrawable()).getBitmap();
                            Palette.Builder builder = new Palette.Builder(drawable);
                            builder.generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                                    Palette.Swatch vibrantDark = palette.getLightMutedSwatch();
                                    if (vibrant != null) {
                                        try {
                                            nsv.setBackgroundColor(vibrant.getRgb());
                                            txt_sd_overview.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_created_by.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_name.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_genres.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_networks.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_production_companies.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_nbr_episodes.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_sd_nbr_seasons.setTextColor(vibrantDark.getTitleTextColor());
                                            txt_first_last_air_date.setTextColor(vibrantDark.getTitleTextColor());
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
        if (series_url == null) {
            Snackbar.make(findViewById(android.R.id.content), "No url provided", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.addCategory(Intent.CATEGORY_BROWSABLE);
            i.setData(Uri.parse(series_url));
            startActivity(i);
        }
    }
}
