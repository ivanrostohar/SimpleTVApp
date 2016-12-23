package com.example.ivan.simpletvapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ivan.simpletvapp.BuildConfig;
import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.fragments.AboutUsFragment;
import com.example.ivan.simpletvapp.fragments.MoviesFragment;
import com.example.ivan.simpletvapp.fragments.SeriesFragment;
import com.example.ivan.simpletvapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;

    //we need ActionBarDrawerToggle for hamburger icon
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new MoviesFragment()).commit();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        //tu definiramo fragmente
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.movies_fragment:
                        switchFragment(R.id.main_container, new MoviesFragment());
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.series_fragment:
                        switchFragment(R.id.main_container, new SeriesFragment());
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.settings_fragment:
                        switchFragment(R.id.main_container, new SettingsFragment());
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.about_us_fragment:
                        switchFragment(R.id.main_container, new AboutUsFragment());
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                }

                return false;
            }
        });


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void switchFragment(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment).addToBackStack(null).commit();
    }

    public void setToolbar(Toolbar toolbar, String title) {
        AppCompatActivity actionBar = this;
        actionBar.setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) actionBar.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.setDrawerIndicatorEnabled(true);
        toogle.syncState();
        if (toolbar != null)
            toolbar.setTitle(title);

    }


}
