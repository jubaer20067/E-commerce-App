package com.jubaer_ahmed.e_commers;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    MaterialToolbar toolbar;

    NavigationView navigationview;

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);
        navigationview = findViewById(R.id.navigationview);
        frameLayout = findViewById(R.id.fragment_container);

        bottomNavigationView = findViewById(R.id.bottomNavigation);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawer,toolbar,R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);

        loadFragment(new HomeFragment(),true);
        drawer.closeDrawer(GravityCompat.START);

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home){

                    loadFragment(new HomeFragment(),false);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (id == R.id.nav_add) {

                    loadFragment(new AddCartFragment(),false);
                    drawer.closeDrawer(GravityCompat.START);

                } else if (id == R.id.nav_histor) {

                    loadFragment(new HistoryFragment(),false);
                    drawer.closeDrawer(GravityCompat.START);

                }else if (id == R.id.nav_profile) {

                    loadFragment(new ProfileFragment(),false);
                    drawer.closeDrawer(GravityCompat.START);

                } else {

                    Toast.makeText(MainActivity.this, ""+item, Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home){

                    loadFragment(new HomeFragment(),false);

                } else if (id == R.id.nav_add) {

                    loadFragment(new AddCartFragment(),false);


                } else if (id == R.id.nav_histor) {

                    loadFragment(new HistoryFragment(),false);


                } else {

                    loadFragment(new ProfileFragment(),false);
                }

                return false;
            }
        });


    }


    private void loadFragment(Fragment fragment, boolean isAppInitialize) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialize) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
