package com.example.ivan.simpletvapp.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.activities.MainActivity;


public class AboutUsFragment extends Fragment {
    private TextView txt_about_the_app_title, txt_credits_title;
    private Toolbar aboutUsToolbar;


    public AboutUsFragment() {
    }

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "28_Days_Later.ttf");

        txt_about_the_app_title = (TextView) view.findViewById(R.id.txt_about_the_app_title);
        txt_credits_title = (TextView) view.findViewById(R.id.txt_credits_title);
        aboutUsToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((MainActivity) getActivity()).setToolbar(aboutUsToolbar, "About the app and Credits");

        txt_about_the_app_title.setTypeface(custom_font);
        txt_credits_title.setTypeface(custom_font);

        return view;
    }

}
