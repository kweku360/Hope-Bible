package com.kfive.hopebook.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.adapters.ColortagAdapter;
import com.kfive.hopebook.data.BookmarksHelper;
import com.kfive.hopebook.data.ColortagsHelper;
import com.kfive.hopebook.fragments.HbBibleVersion;

public class HbColortags extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_colortags);
        showAllColorTags();
    }
//    custom methods
    private void showAllColorTags() {
        //lets connect to our table and get all bookmarks
        ColortagsHelper colortagsHelper = new ColortagsHelper(this);

        Cursor cursor = colortagsHelper.findAll();

        CursorAdapter cAdapter = new ColortagAdapter(this,cursor,0);
        final ListView listView1 = (ListView) findViewById(android.R.id.list);
        listView1.setAdapter(cAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_colortags, menu);
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

    //events
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
}
