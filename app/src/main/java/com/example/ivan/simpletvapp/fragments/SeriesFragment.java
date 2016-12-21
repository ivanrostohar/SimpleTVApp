package com.example.ivan.simpletvapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.activities.MainActivity;
import com.example.ivan.simpletvapp.adapters.SeriesAdapter;
import com.example.ivan.simpletvapp.models.SeriesModel;
import com.example.ivan.simpletvapp.other.AppController;
import com.example.ivan.simpletvapp.other.UrlConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesFragment extends Fragment{
    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    public int currentPage = 1;
    public int totalPages;
    String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=";
    public ArrayList<SeriesModel> seriesModel = new ArrayList<>();
    public SeriesModel seriesObject;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private SeriesAdapter seriesAdapter;
    private long requestStartTime,requestEndTime, requestTotalTime;
    private Toolbar seriesToolbar;


    public SeriesFragment() {

    }

    public static SeriesFragment newInstance() {
        SeriesFragment fragment = new SeriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.movies_series_fragment, container, false);

        seriesToolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);

        ((MainActivity)getActivity()).setToolbar(seriesToolbar, "Series");

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);



        SeriesDownload(url, currentPage);


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if(!recyclerView.canScrollVertically(-1)) {
//
//                    new Thread(downloadSeriesThread).start();
//
//                }
//                Log.v("TIME", String.valueOf(requestTotalTime));
//            }
//        });



        return view;
    }

    public Runnable downloadSeriesThread = new Runnable() {
        @Override
        public void run() {

            while(currentPage<totalPages){
                try {
                    Thread.sleep(1000);
                    SeriesDownload(url, currentPage);
                    currentPage++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void SeriesDownload(String url, int currentPage){

        requestStartTime = System.currentTimeMillis();
            String url_final = url + MOVIES_API_KEY + "&page=" + currentPage;
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_final, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        totalPages = response.getInt("total_pages");
                        JSONArray results = response.getJSONArray("results");
                        for(int i=0; i<results.length(); i++){
                            JSONObject resultsObject = results.getJSONObject(i);
                            seriesObject = new SeriesModel(
                                    resultsObject.getDouble("vote_average"),
                                    resultsObject.getString("poster_path"),
                                    resultsObject.getString("first_air_date"),
                                    resultsObject.getString("name")
                            );
                            seriesModel.add(seriesObject);
                            seriesAdapter = new SeriesAdapter(getActivity(),seriesModel);
                            recyclerView.setAdapter(seriesAdapter);
                            seriesAdapter.notifyDataSetChanged();
                        }
                        int page2 = response.getInt("page");
                        Log.v("DATA", String.valueOf(page2));
                        requestEndTime = System.currentTimeMillis() - requestStartTime;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getLocalizedMessage();
                }
            });
        requestTotalTime+=requestEndTime;

            AppController.getInstance().addToRequestQueue(objectRequest);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.series_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.popular_shows:
                Toast.makeText(getActivity(), "Popular shows", Toast.LENGTH_SHORT).show();
                seriesModel.clear();
                SeriesDownload(UrlConstants.POPULAR_SERIES, currentPage);
                break;
            case R.id.on_the_air:
                Toast.makeText(getActivity(), "On the air shows", Toast.LENGTH_SHORT).show();
                seriesModel.clear();
                SeriesDownload(UrlConstants.ON_THE_AIR_SERIES, currentPage);
                break;
            case R.id.top_rated_shows:
                Toast.makeText(getActivity(), "Top rated shows", Toast.LENGTH_SHORT).show();
                seriesModel.clear();
                SeriesDownload(UrlConstants.TOP_RATED_SERIES, currentPage);
                break;
            case R.id.airing_today:
                Toast.makeText(getActivity(), "Airing today", Toast.LENGTH_SHORT).show();
                seriesModel.clear();
                SeriesDownload(UrlConstants.AIRING_TODAY_SERIES, currentPage);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

