package com.kfive.hopebook.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.data.BookmarksHelper;
import com.kfive.hopebook.fragments.HbBibleVersion;

public class HbBookmarks extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_bookmarks);

        showAllBookmarks();
    }

    //Custom methods

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

    private void showAllBookmarks() {
        //lets connect to our table and get all bookmarks
        BookmarksHelper bookmarksHelper = new BookmarksHelper(this);

        Cursor cursor = bookmarksHelper.findAll();
        // For the cursor adapter, specify which columns go into which views
        Log.v("cursorvals", cursor.toString());

        String[] fromColumns = {"fullverse","month","tstamp","versetext"};
        int[] toViews = {R.id.bookmarkfullverse,R.id.timestampstring,R.id.bookmarktime,R.id.bookmarkversetext}; // The TextView in simple_list_item_1

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this,
                R.layout.hb_bookmark_list, cursor,
                fromColumns, toViews, 0);
        final ListView listView1 = (ListView) findViewById(android.R.id.list);
        listView1.setAdapter(cAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_bookmarks, menu);
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
}
