package com.example.sayeed.xyzconferenceapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences("userdetails", MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (sharedPreferences.getString("name", "").isEmpty()) {
            Log.v("normal", "getting name");
            final Dialog textDialog = new Dialog(MainActivity.this);
            textDialog.setContentView(R.layout.custom_dialog);
            textDialog.setTitle("please enter your name");
            textDialog.show();
            textDialog.findViewById(R.id.submitNameButton).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick (View v) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", ((EditText)(textDialog.findViewById(R.id.nameInput))).getText().toString());
                            editor.commit();
                            textDialog.dismiss();
                        }
                    }
            );
        } else {
           Log.v("normal", "welcome back, " + sharedPreferences.getString("name", ""));
        }

        ((TextView)findViewById(R.id.lblGeneralSchedule)).setText(sharedPreferences.getString("name", "General")+"'s schedule");
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
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
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_generalschedule) {
            // Handle the camera action
            Intent i = new Intent(this, GeneralScheduleActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_myschedule) {
            Intent i = new Intent(this, MyScheduleActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_speakers) {
            Intent i = new Intent(this, SpeakersActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_twitter) {
            Intent i = new Intent(this, TwitterActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_maps) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_attendees) {
            Intent i = new Intent(this, AttendeesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_sponsors) {
            Intent i = new Intent(this, SponsorActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_surveys) {
            Intent i = new Intent(this, SurveysActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_leaderboard) {
            Intent i = new Intent(this, LeaderboardActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_signout) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
