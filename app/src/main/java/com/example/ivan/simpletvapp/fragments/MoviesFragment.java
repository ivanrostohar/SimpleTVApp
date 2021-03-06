package com.example.ivan.simpletvapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.ivan.simpletvapp.other.UrlConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements SearchView.OnQueryTextListener{

    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    private static final String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private LinearLayoutManager llm;
    private int currentPage = 1;
    private int totalPages;
    private long requestStartTime, requestEndTime, requestTotalTime;
    private MoviesModel moviesObject;
    private ArrayList<MoviesModel> moviesModel = new ArrayList<>();
    public Runnable downloadMoviesThread = new Runnable() {
        @Override
        public void run() {

            while (currentPage < totalPages) {
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

        toolbarMovies = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((MainActivity) getActivity()).setToolbar(toolbarMovies, "Movies");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
//            @Override
//            public void onLoadMore(int current_page) {
//                downloadMovies(url, current_page);
//                Log.v("TIME", String.valueOf(requestTotalTime));
//            }
//        });


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

    public void downloadMovies(String url, int currentPage) {
        requestStartTime = System.currentTimeMillis();
        String url_final = url + MOVIES_API_KEY + "&page=" + currentPage;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_final, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalPages = response.getInt("total_pages");
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject resultsObject = results.getJSONObject(i);
                        moviesObject = new MoviesModel(
                                resultsObject.getString("poster_path"),
                                resultsObject.getString("release_date"),
                                resultsObject.getString("original_title"),
                                resultsObject.getDouble("vote_average"),
                                resultsObject.getInt("id")
                        );
                        Log.v("ID", String.valueOf(moviesObject.getMoviesId()));
                        moviesModel.add(moviesObject);
                        moviesAdapter = new MoviesAdapter(getActivity(), moviesModel);
                        recyclerView.setAdapter(moviesAdapter);
                       // moviesAdapter.notifyDataSetChanged();
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
        requestTotalTime += requestEndTime;


        AppController.getInstance().addToRequestQueue(objectRequest);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_menu, menu);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuIntem = menu.findItem(R.id.search_toolbar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuIntem);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_rated_movies:
                Toast.makeText(getActivity(), "Top rated movies", Toast.LENGTH_SHORT).show();
                moviesModel.clear();
                downloadMovies(UrlConstants.TOP_RATED_MOVIES, currentPage);
                break;
            case R.id.now_playing_movies:
                Toast.makeText(getActivity(), "Now playing movies", Toast.LENGTH_SHORT).show();
                moviesModel.clear();
                downloadMovies(UrlConstants.NOW_PLAYING_MOVIES, currentPage);
                break;
            case R.id.upcoming_movies:
                Toast.makeText(getActivity(), "Upcoming movies", Toast.LENGTH_SHORT).show();
                moviesModel.clear();
                downloadMovies(UrlConstants.UPCOMING_MOVIES, currentPage);
                break;
            case R.id.popular_movies:
                Toast.makeText(getActivity(), "Popular movies", Toast.LENGTH_SHORT).show();
                moviesModel.clear();
                downloadMovies(UrlConstants.POPULAR_MOVIES, currentPage);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //search works fine if there is no recyclerview.onScroolListener
    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<MoviesModel> newList = new ArrayList<>();
        for(MoviesModel mm : moviesModel){
            String name = mm.getOriginalTitle().toLowerCase();
            if(name.contains(newText)){
                newList.add(mm);
                Log.v("SEARCH", name);
            }
        }
        moviesAdapter.setFilter(newList);
        return true;
    }
}
