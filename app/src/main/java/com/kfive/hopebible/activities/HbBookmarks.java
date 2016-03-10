package com.kfive.hopebible.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.data.BookmarksHelper;
import com.kfive.hopebible.data.VersionHelper;

import java.util.ArrayList;

public class HbBookmarks extends ActionBarActivity {
    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebible.MESSAGE";

    //classes
    VersionHelper versionHelper = new VersionHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_hb_bookmarks);
        setResourcesColor();
        showAllBookmarks();
    }

    //Custom methods

    //events

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

        hbmenubar.setBackgroundColor(Color.parseColor(color));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

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

        final Cursor cursor = bookmarksHelper.findAll();
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
        //making our context available in our listener
        final Context that = this;

        //click event for each item clicked in the listview
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //first we get cursor on clicked position
                Cursor c = (Cursor) listView1.getAdapter().getItem(position);
                //then we get staret and end verses and parse to versionhelper to get full details on
                //Log.v("cursor val", String.valueOf(c.getInt(4)));
                //bible verse
                Cursor txtcursor = versionHelper.getVerseText(c.getInt(4),c.getInt(5));
                String bookname = "";
                String tp = "";
                String tp1 = "";
                while(txtcursor.moveToNext()) {
                  bookname = txtcursor.getString(7);
                  tp = String.valueOf(txtcursor.getInt(3)-1);
                  tp1 = String.valueOf(txtcursor.getInt(3));
                }
                //now we can create our intent well
                //first we build another array list to be sent as intent
                ArrayList<String> itemlist = new ArrayList<String>();
                itemlist.add(bookname); //0 the book name
                itemlist.add(String.valueOf(c.getInt(4)));//1 start verse
                itemlist.add(String.valueOf(c.getInt(5)));//2 end verse
                itemlist.add(tp);//3 position or target verse
                itemlist.add(tp1);//4 this is for title purposes more ore less add 1 to the previous listitem value

                Intent intent = new Intent(that, HbBibleText.class);

                intent.putStringArrayListExtra(EXTRA_MESSAGE,itemlist);
                startActivity(intent);

              }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.hb_bookmarks, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
