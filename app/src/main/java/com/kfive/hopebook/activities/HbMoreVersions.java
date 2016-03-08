package com.kfive.hopebook.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.kfive.hopebook.R;
import com.kfive.hopebook.adapters.MoreVersionsAdapter;
import com.kfive.hopebook.fragments.HbDownloader;
import com.kfive.hopebook.models.MoreBibleVersions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 11/21/14.
 */

public class HbMoreVersions extends ActionBarActivity {
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_more_versions);
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        pg.setVisibility(View.VISIBLE);
        setTitle("Hope Bible Download Versions");
        getMoreVersions();

    }

    private void getMoreVersions() {
        // Gets the URL from the UI's text field.
        final Context that = this;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //lets call our parse query
            ParseQuery<ParseObject> query = ParseQuery.getQuery("hopebible");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> versionslist, ParseException e) {
                    if (e == null) {
                        Activity activity = (Activity) that;

                        MoreVersionsAdapter mAdapter = new MoreVersionsAdapter(versionslist,activity);
                        //get listview of activity
                        ListView listView1 = (ListView) findViewById(android.R.id.list);
                        //set listview adapter
                        listView1.setAdapter(mAdapter);
                        pg.setVisibility(View.GONE);
                        Log.d("score", "Retrieved versions");
                    } else {
                        Log.d("Error", "Error: " + e.getMessage());
                    }
                }
            });

        } else {
            Log.d("Network Error", "No network ");
             }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_more_versions, menu);
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

    //Events
    public void onHome(View v){
        Intent intent = new Intent(this, HbHome.class);
        startActivity(intent);
    }

    public void onBookSelect(View v){
        Intent intent = new Intent(this, HbBooksAll.class);
        startActivity(intent);
    }
    public void onSearch(View v){
        //start the intent and search activity
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);
    }
    public void onDownloadClick(View view){
        HbDownloader hbDownloader = new HbDownloader();

        hbDownloader.show(getSupportFragmentManager(), "Downloader Dialog");

    }

}
