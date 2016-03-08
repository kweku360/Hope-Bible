package com.kfive.hopebook.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.adapters.MoreVersionsAdapter;
import com.kfive.hopebook.async.MoreversionsAsync;
import com.kfive.hopebook.fragments.HbColortag;
import com.kfive.hopebook.fragments.HbDownloader;
import com.kfive.hopebook.helpers.JsonParserHelper;
import com.kfive.hopebook.models.MoreBibleVersions;

import java.util.ArrayList;

public class HbMoreVersionsold extends ActionBarActivity{
    public TextView textView;
    int i = 0;
    //Instantiate our async class
    MoreversionsAsync moreversionsAsync = new MoreversionsAsync();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_more_versions);
        //textView = (TextView) findViewById(R.id.textView);

        getMoreVersions();
    }

    private void getMoreVersions() {
        // Gets the URL from the UI's text field.
        final Context that = this;
        String stringUrl = "http://192.168.59.103:3002/api/bibleversions";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            moreversionsAsync.execute(stringUrl);

          moreversionsAsync.setAsyncTaskResponseListener(new MoreversionsAsync.AsyncTaskResponse() {
              @Override
              public void processFinish(String Output) {
                  //Ok now lets parse our string  to the json parser
                 ArrayList<MoreBibleVersions> mVersions = JsonParserHelper.moreBibleibleVersionJsonParse(Output);

//                  //now we pass mvesions to our adapter
//                  Activity activity = (Activity) that;
//                  MoreVersionsAdapter mAdapter = new MoreVersionsAdapter(mVersions,activity);
//                  //get listview of activity
//                  ListView listView1 = (ListView) findViewById(android.R.id.list);
//                  //set listview adapter
//                  listView1.setAdapter(mAdapter);
//                  Log.v("result", Output);
              }
          });

        } else {
            Log.v("Networ err", "No network connection available");

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

    //Event
    public void onDownloadClick(View view){
        HbDownloader hbDownloader = new HbDownloader();

        hbDownloader.show(getSupportFragmentManager(), "Downloader Dialog");

    }



}
