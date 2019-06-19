package com.example.weekend2_real_master;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvCelebRecyclerView;
    ArrayList<Celebrity> celebList = new ArrayList<>();
    DatabaseHelper dbhelp;
    static final int ADD_CELEB_REQUEST = 1;
    DrawerLayout drawer;
    RecyclerViewAdapter celebRecyclerViewAdapter;

    final String TAG = "TAG_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializes and assigns the toolbar to its view
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //drawer layout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //populates the initial list of celebrities
        populateCelebs();
        //initializes recyclerview
        initializeRecyclerView();

    }

    private void populateCelebs() {
        if (!celebList.isEmpty()) {
            celebList = dbhelp.queryForAllCelebRecords();
            for (Celebrity c : celebList){
                celebRecyclerViewAdapter.addCeleb(c);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //creates drawer object and finds the view for it
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //if the drawer is open, this will be closed when the back button is pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //otherwise, this reverts it to previous class
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeRecyclerView() {
        rvCelebRecyclerView = findViewById(R.id.rvCelebrityRecyclerView);
        //creates layout manager with context of this
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //passes the movie list into the recycler view adapter for it to adapt
        celebRecyclerViewAdapter = new RecyclerViewAdapter(celebList);
        //decides how to render layout
        rvCelebRecyclerView.setLayoutManager(layoutManager);
        rvCelebRecyclerView.setAdapter(celebRecyclerViewAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected");

        if (id == R.id.nav_add_celebrity) {

            Log.d(TAG, "onNavigationItemSelected: Inside second if");
            //if the ID of the nav drawer is clicked, send to the addceleb activity
            Intent add = new Intent(this, AddCeleb.class);
            startActivityForResult(add, ADD_CELEB_REQUEST);

        } else if (id == R.id.nav_favorites) {
            //if the ID is for favorites then send to the viewfavorites class
            Intent favorites = new Intent(this, ViewFavorites.class);
            startActivity(favorites);

        }

        //closes the drawer and starts the gravity (closes the drawer)
        drawer.closeDrawer(GravityCompat.START);
        //returns true when item is selected
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode) == 2 && (data != null)) {
            Bundle receivedBundle = data.getExtras();
            if (receivedBundle != null) {
                Celebrity celeb = receivedBundle.getParcelable("celebrity");
                celebRecyclerViewAdapter.addCeleb(celeb);
            }
        }
    }


}

