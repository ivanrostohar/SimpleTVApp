package com.example.ivan.simpletvapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.activities.MainActivity;
import com.example.ivan.simpletvapp.adapters.MoviesAdapter;
import com.example.ivan.simpletvapp.models.MoviesModel;
import com.example.ivan.simpletvapp.other.AppController;
import com.example.ivan.simpletvapp.other.EndlessRecyclerOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

public class MoviesFragment extends Fragment {

    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    private static final String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private LinearLayoutManager llm;
    private int currentPage = 1;
    private int totalPages;
    private long requestStartTime,requestEndTime, requestTotalTime;
    private MoviesModel moviesObject;
    private ArrayList<MoviesModel> moviesModel = new ArrayList<>();
    private Toolbar toolbarMovies;


    public MoviesFragment() {

    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.movies_series_fragment, container, false);

        toolbarMovies = (Toolbar)getActivity().findViewById(R.id.toolbar);

        ((MainActivity)getActivity()).setToolbar(toolbarMovies, "Movies");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                downloadMovies(url, current_page);
                Log.v("TIME", String.valueOf(requestTotalTime));
            }
        });


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);



//                if((recyclerView.getAdapter().getItemCount() == moviesModel.size()-1) && loading){
//                    new Thread(downloadMoviesThread).start();
//                    loading = false;
//                }

//                if(!recyclerView.canScrollVertically(-1)) {
//
//                    new Thread(downloadMoviesThread).start();
//
//                }
//
//            }
//        });

        downloadMovies(url, currentPage);
//        currentPage++;

        return view;
    }



    public Runnable downloadMoviesThread = new Runnable() {
        @Override
        public void run() {

            while(currentPage<totalPages){
                try {
                    Thread.sleep(1000);
                    downloadMovies(url, currentPage);
                    currentPage++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };


    public void downloadMovies(String url, int currentPage){
        requestStartTime = System.currentTimeMillis();
        String url_final = url + MOVIES_API_KEY + "&page=" + currentPage;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_final, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalPages = response.getInt("total_pages");
                    JSONArray results = response.getJSONArray("results");
                    for(int i=0; i<results.length(); i++){
                        JSONObject resultsObject = results.getJSONObject(i);
                        moviesObject = new MoviesModel(
                                resultsObject.getString("poster_path"),
                                resultsObject.getString("release_date"),
                                resultsObject.getString("original_title"),
                                resultsObject.getDouble("vote_average")
                        );
                        moviesModel.add(moviesObject);
                        moviesAdapter = new MoviesAdapter(getActivity(),moviesModel);
                        recyclerView.setAdapter(moviesAdapter);
                        moviesAdapter.notifyDataSetChanged();
                    }
                    int page2 = response.getInt("page");
                    Log.v("DATA", String.valueOf(page2));
                    requestEndTime = System.currentTimeMillis() - requestStartTime;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
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
        inflater.inflate(R.menu.movies_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.latest_movies:
                Toast.makeText(getActivity(), "Latest movies", Toast.LENGTH_SHORT).show();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }
}
