package com.kfive.hopebible.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kfive.hopebible.R;
import com.kfive.hopebible.fragments.HbTheme;
import com.kfive.hopebible.helpers.DbInitHelper;
import com.kfive.hopebible.helpers.ThemeHelper;

import java.io.IOException;

public class HbLanding extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,HbTheme.HbThemeListener{

    boolean schduledRestart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeid = new ThemeHelper(getApplicationContext()).getTheme();
        setTheme(themeid);
        super.onCreate(savedInstanceState);

        //lets create our db if it doesnt exist
        //TODO MOVE to SPLASH SCREEN.
        //TODO  Check storage space to set database location
        //TODO Init Database in Splash Activity
        DbInitHelper initDb = new DbInitHelper(this);
        try {
            initDb.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            initDb.openDataBase();

        } catch (Exception sqle) {

            Log.v("SQL Error", sqle.toString());

        }

        setContentView(R.layout.activity_hb_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    protected void onResume() {
        super.onResume();
        Log.v("restart","resumed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
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

        if (id == R.id.nav_read) {
            Intent intent = new Intent(this, HbBooksAll.class);
            startActivity(intent);

        } else if (id == R.id.nav_devotion) {
            Toast.makeText(this, "Daily Devotion Coming Soon to Hope Bible", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, HbSearchPage.class);
            startActivity(intent);

        } else if (id == R.id.nav_bookmarks) {
            Intent intent = new Intent(this, HbBookmarks.class);
            startActivity(intent);

        } else if (id == R.id.nav_theme) {
            HbTheme hbTheme = new HbTheme();
            hbTheme.show(getSupportFragmentManager(), "Theme Dialog");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onThemeClick(DialogFragment dialog, String colorcode) {
        new ThemeHelper(getApplicationContext()).saveTheme(colorcode);
//        restert activity
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
