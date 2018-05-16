package com.example.android.navdrawerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView chatForum = findViewById(R.id.chatForum);
        chatForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatForum = new Intent(MainActivity.this, Main2Activity.class);
                //Toast.makeText(view.getContext(), "Open the Discussion Forum", Toast.LENGTH_SHORT).show();
                startActivity(chatForum);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    //TextView mWeatherTextView = findViewById(R.id.tv_weather_data);
    ArrayList<String> dummyWeatherData = new ArrayList<>();

        dummyWeatherData.add("ICI-Timetable");
        dummyWeatherData.add("Memo");
        dummyWeatherData.add("Today, May 17 - Clear - 17°C / 15°C");
        dummyWeatherData.add("Today, May 17 - Clear - 17°C / 15°C");
        dummyWeatherData.add("Today, May 17 - Clear - 17°C / 15°C");

    ArrayAdapter dummyWeatherDataAdapter = new ArrayAdapter (this , android.R.layout.simple_list_item_1, dummyWeatherData);

    ListView listView = (ListView) findViewById(R.id.list);

    listView.setAdapter(dummyWeatherDataAdapter);

    //String[] dummyWeatherData = {
            //"Today, May 17 - Clear - 17°C / 15°C",
            //"Tomorrow - Cloudy - 19°C / 15°C",
            //"Thursday - Rainy- 30°C / 11°C",
            //"Friday - Thunderstorms - 21°C / 9°C",
            //"Saturday - Thunderstorms - 16°C / 7°C",
            //"Sunday - Rainy - 16°C / 8°C",
    //};

       // for (String dummyWeatherDay : dummyWeatherData) {
        //mWeatherTextView.append(dummyWeatherDay + "\n\n\n");
   // }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
