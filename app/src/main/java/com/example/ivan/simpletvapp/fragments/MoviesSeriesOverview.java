package com.example.ivan.simpletvapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.models.MoviesModel;


public class MoviesSeriesOverview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView naziv;
    MoviesModel moviesModel;
    Bundle bundle;

    public MoviesSeriesOverview() {
        // Required empty public constructor
    }


    public static MoviesSeriesOverview newInstance(String param1, String param2) {
        MoviesSeriesOverview fragment = new MoviesSeriesOverview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_series_overview, container, false);



        naziv = (TextView)view.findViewById(R.id.ms_name);
        bundle = this.getArguments();
        moviesModel = bundle.getParcelable("movies_object");
        naziv.setText(moviesModel.getOriginalTitle());


        return view;
    }

}
