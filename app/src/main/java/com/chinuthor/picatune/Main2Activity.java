package com.chinuthor.picatune;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton mainAddSongFab;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainDrawerNavigationView;
    private AppCompatTextView mainDrawerHeaderNameTextView;
    private AppCompatTextView mainDrwerHeaderUsernameTextView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);


        mainAddSongFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainDrawerLayout = findViewById(R.id.drawer_layout);
        mainDrawerNavigationView = findViewById(R.id.main_drawer_navigation_view);

        final View mainNavigationDrawerHeader = mainDrawerNavigationView.inflateHeaderView(R.layout.main_navigation_drawer_header);
        mainDrawerHeaderNameTextView = mainNavigationDrawerHeader.findViewById(R.id.main_drawer_header_name_text_view);
        mainDrwerHeaderUsernameTextView = mainNavigationDrawerHeader.findViewById(R.id.main_drawer_header_username_text_view);

        // TODO: 2019-08-15 Add username and name here

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainDrawerLayout, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mainDrawerNavigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(new LibraryFragment(), "Library").commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_library:
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new LibraryFragment(), "Library").commit();
                break;

            case R.id.nav_play_list:
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new PlayListFragment(), "PlayList").commit();
                break;

            case R.id.nav_rating:

                break;

            case R.id.nav_logout:

                break;

            default:

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
