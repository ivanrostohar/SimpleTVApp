package com.example.ivan.simpletvapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.adapters.MoviesAdapter;
import com.example.ivan.simpletvapp.models.MoviesResponse;
import com.example.ivan.simpletvapp.models.Result;
import com.example.ivan.simpletvapp.services.MoviesAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ivan.simpletvapp.BuildConfig.MOVIES_API_KEY;

public class MoviesFragment extends Fragment {

    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    private List<Result> moviesResult;
    //  private Context context = getActivity();
    LinearLayoutManager llm;



    public MoviesFragment() {

    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

      //  gridLayoutManager = new GridLayoutManager(getActivity(), 1);
      //  recyclerView.setLayoutManager(gridLayoutManager);
        llm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        downloadPopularMovies();

        Log.v("moviesRESULT", moviesResult.toString());

        return view;
    }

    public void downloadPopularMovies() {

        moviesResult = new ArrayList<>();


            MoviesAPI.GetClient.getInstance().getNowPlayingMovies(MOVIES_API_KEY).enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    int odgovor = response.body().getTotalPages();
                    Log.v("ODGOVOR", String.valueOf(odgovor));
                    Log.v("NAZIV", String.valueOf(response.body().getResults().get(0).getOriginalTitle()));

                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Result rezultat = new Result(
                                response.body().getResults().get(i).getPosterPath(),
                                response.body().getResults().get(i).getOriginalTitle(),
                                response.body().getResults().get(i).getReleaseDate(),
                                response.body().getResults().get(i).getVoteAverage());
                        moviesResult.add(rezultat);
                    }
                    //   Log.v("moviesRESULT2", moviesResult.toString());
                    moviesAdapter = new MoviesAdapter(getActivity(), moviesResult);
                    recyclerView.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    //    Log.v("PROMAŠAJ", t.getLocalizedMessage());
                }
            });
        }
    }
