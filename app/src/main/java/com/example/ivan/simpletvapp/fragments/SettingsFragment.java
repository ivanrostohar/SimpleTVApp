package com.example.ivan.simpletvapp.fragments;


import android.os.Bundle;

import com.example.ivan.simpletvapp.R;
import com.github.machinarius.preferencefragment.PreferenceFragment;

import java.util.Locale;


public class SettingsFragment extends PreferenceFragment {
    Locale locale;

    public SettingsFragment() {

    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

}


