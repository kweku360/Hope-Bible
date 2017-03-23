package com.kfive.hopebible.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.kfive.hopebible.R;
import com.kfive.hopebible.adapters.MoreVersionsAdapter;
import com.kfive.hopebible.fragments.HbDownloader;
import com.kfive.hopebible.models.MoreBibleVersions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by apple on 11/21/14.
 */

public class HbMoreVersions extends ActionBarActivity {
    ProgressBar pg;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_more_versions);
        pg = (ProgressBar) findViewById(R.id.progressBar1);

        pg.setVisibility(View.VISIBLE);
        getSupportActionBar().setElevation(0); //hide shadow on actionbar
        setResourcesColor();//theme method
        setTitle("Hope Bible Versions");
        //getMoreVersions();

        listView = (ListView) findViewById(R.id.hb_morelist);

        //lets test real io database
        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        Realm realm = Realm.getInstance(realmConfig);
        MoreBibleVersions moreBibleVersions = new MoreBibleVersions();
        moreBibleVersions.setId(1);
        moreBibleVersions.setVersionname("RSV");
        moreBibleVersions.setDownloadcount(300);
        realm.beginTransaction();
        realm.copyToRealm(moreBibleVersions);
        realm.commitTransaction();

        RealmResults<MoreBibleVersions> results1 =
                realm.where(MoreBibleVersions.class).findAll();

        //more versions adapter
        MoreVersionsAdapter moreVersionsAdapter = new MoreVersionsAdapter(results1,this);
        listView.setAdapter(moreVersionsAdapter);
        pg.setVisibility(View.GONE);

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

                   //     MoreVersionsAdapter mAdapter = new MoreVersionsAdapter(versionslist,activity);
                        //get listview of activity
                        ListView listView1 = (ListView) findViewById(android.R.id.list);
                        //set listview adapter
                    //    listView1.setAdapter(mAdapter);
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

    private String getColorTheme(){
        SharedPreferences appprefs = getSharedPreferences("com.kfive.hopebible.bible", MODE_PRIVATE);
        SharedPreferences.Editor ed;
        String themecolor = appprefs.getString("color", "");
        if (themecolor.equals("")) {
            //means no value for theme so we use default redoctober
            ed = appprefs.edit();
            ed.putString("color", "#C44244");
            ed.commit(); //finally we commit
            themecolor = "#C44244";
        }
        return themecolor;
    }

    private void setResourcesColor(){
        String color = getColorTheme();
        LinearLayout hbmenubar = (LinearLayout)findViewById(R.id.hb_menubar);
      //  RelativeLayout hbfooter = (RelativeLayout)findViewById(R.id.hb_footer);
        // ImageButton searchbtn = (ImageButton)findViewById(R.id.hb_searchbtn);

        hbmenubar.setBackgroundColor(Color.parseColor(color));
      //  hbfooter.setBackgroundColor(Color.parseColor(color));
        // searchbtn.setBackgroundColor(Color.parseColor(color));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

}
