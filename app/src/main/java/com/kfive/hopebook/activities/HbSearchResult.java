package com.kfive.hopebook.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.adapters.MoreVersionsAdapter;
import com.kfive.hopebook.async.MoreversionsAsync;
import com.kfive.hopebook.async.SearchResultAsync;
import com.kfive.hopebook.fragments.HbBibleVersion;
import com.kfive.hopebook.helpers.JsonParserHelper;
import com.kfive.hopebook.helpers.SearchHelper;
import com.kfive.hopebook.models.MoreBibleVersions;

import java.util.ArrayList;

public class HbSearchResult extends ListActivity {
    SearchResultAsync searchResultAsync = new SearchResultAsync(this);
    ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_search_result);
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        pg.setVisibility(View.VISIBLE);
        showSearchResult();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_search_result, menu);
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

    //retrieve the intent and show results
    private void showSearchResult() {
        //first we get the intent as an arraylist
        final Context that = this;
        Intent intent = getIntent();
        final ArrayList<String> message = intent.getStringArrayListExtra(HbSearchPage.EXTRA_MESSAGE);
           setTitle("Search Results '"+message.get(0)+"' ");
            searchResultAsync.execute(message.get(0), message.get(1), message.get(2));

        searchResultAsync.setAsyncTaskResponseListener(new SearchResultAsync.AsyncTaskResponse(){
            @Override
            public void processFinish(Cursor Output) {
                //lets inflate a view

                String[] fromColumns = {"n","c","v","t"};
                int[] toViews = {R.id.searchversebook,R.id.searchversechapter,R.id.searchverseno,R.id.searchversetest};

                // Create an empty adapter we will use to display the loaded data.
                // We pass null for the cursor, then update it in onLoadFinished()
                SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(that,
                        R.layout.hb_search_result_itemview, Output,
                        fromColumns, toViews, 0);
                setListAdapter(cAdapter);
                pg.setVisibility(View.GONE);
                TextView pageinfo = (TextView) findViewById(R.id.pageinfo);
                pageinfo.setText("Search Returned "+cAdapter.getCount()+" verses");


            }
        });
    }

    //events

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

//    private void searchEntireBible(String searchtext) {
//        SearchHelper searchHelper = new SearchHelper(this);
//        Cursor cursor = searchHelper.searchEntireBible(searchtext);
//
//        String[] fromColumns = {"n","c","v","t"};
//        int[] toViews = {R.id.searchversebook,R.id.searchversechapter,R.id.searchverseno,R.id.searchversetest};
//
//        // Create an empty adapter we will use to display the loaded data.
//        // We pass null for the cursor, then update it in onLoadFinished()
//        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this,
//                R.layout.hb_search_result_itemview, cursor,
//                fromColumns, toViews, 0);
//        setListAdapter(cAdapter);
//
//
//    }

}
