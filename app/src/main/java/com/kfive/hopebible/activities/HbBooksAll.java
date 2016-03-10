package com.kfive.hopebible.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kfive.hopebible.Pojo.BookList;
import com.kfive.hopebible.R;
import com.kfive.hopebible.adapters.BookListAdapter;
import com.kfive.hopebible.fragments.HbBibleVersion;

import java.util.ArrayList;


public class HbBooksAll extends ActionBarActivity implements HbBibleVersion.HbBibleVersionListener {

    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebible.MESSAGE";
    ListView listView1;
    //Font
    private static final String CUSTOM_FONT = "fonts/Signika-Semibold.ttf";

    // lets declare our adapter clsss
    BookListAdapter bookListAdapter;

    //
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_books_all);
        //settheme color
        setResourcesColor();
        getSupportActionBar().setElevation(0);
        //show booklist
        bookListAdapter = new BookListAdapter(BookList.getBibleBooks(),this);
        //get listview of activity
        listView1 = (ListView) findViewById(android.R.id.list);
        //set listview adapter to booklist adapter
        listView1.setAdapter(bookListAdapter);

        //Lets set title to action bar
        setTitle("Hope Bible");
       // ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.mytext)).setText("new title");

        //register list item click
        bibleBookClick();

       // tabMethods();

        bookTabs();

    }

    private void bookTabs() {

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), CUSTOM_FONT);

        TextView allbooks = (TextView) findViewById(R.id.allbooks);
        TextView booksnew = (TextView) findViewById(R.id.oldtestament);
        TextView booksold = (TextView) findViewById(R.id.newtestament);
        allbooks.setTypeface(myTypeface);
        booksnew.setTypeface(myTypeface);
        booksold.setTypeface(myTypeface);



    }


    private void tabMethods() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        //not used anymore
        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab by calling the given function

            }

            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
     //   actionBar.addTab(actionBar.newTab().setIcon(R.drawable.hb_home).setTabListener(tabListener));
       // actionBar.addTab(actionBar.newTab().setIcon(R.drawable.hb_version).setTabListener(tabListener));
       // actionBar.addTab(actionBar.newTab().setIcon(R.drawable.hb_search).setTabListener(tabListener));


}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_books_all, menu);
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

    public void onVersion(View v){
        HbBibleVersion hbBibleVersion = new HbBibleVersion();

        hbBibleVersion.show(getSupportFragmentManager(), "Version Dialog");
    }
 public void onSearch(View v){
     //start the intent and search activity
     Intent intent = new Intent(this, HbSearchPage.class);
     startActivity(intent);
    }


    public void onAllbooks(View v) {
        bookListAdapter.changeData(BookList.getBibleBooks());
        listView1.setSelection(0);
        //Change button state
        Button allBooks = (Button) findViewById(R.id.allbooks);
        Button oldTestament = (Button) findViewById(R.id.oldtestament);
        Button newTestament = (Button) findViewById(R.id.newtestament);

        allBooks.setBackgroundResource(R.drawable.ab_btnpressed);
        oldTestament.setBackgroundResource(R.drawable.ab_buttons);
        newTestament.setBackgroundResource(R.drawable.ab_buttons);

    }

    public void onNewTestament(View v){
        bookListAdapter.changeData(BookList.getNewTestament());
        listView1.setSelection(0);
        //Change button state
        Button allBooks = (Button) findViewById(R.id.allbooks);
        Button oldTestament = (Button) findViewById(R.id.oldtestament);
        Button newTestament = (Button) findViewById(R.id.newtestament);

        newTestament.setBackgroundResource(R.drawable.ab_btnpressed);
        oldTestament.setBackgroundResource(R.drawable.ab_buttons);
        allBooks.setBackgroundResource(R.drawable.ab_buttons);

    }
    public void onOldTestament(View v){

        bookListAdapter.changeData(BookList.getOldTestament());
        listView1.setSelection(0);
        //Change button state
        Button allBooks = (Button) findViewById(R.id.allbooks);
        Button oldTestament = (Button) findViewById(R.id.oldtestament);
        Button newTestament = (Button) findViewById(R.id.newtestament);

        oldTestament.setBackgroundResource(R.drawable.ab_btnpressed);
        newTestament.setBackgroundResource(R.drawable.ab_buttons);
        allBooks.setBackgroundResource(R.drawable.ab_buttons);


    }

    //List Item Click events
    public void bibleBookClick(){
        final Context that = this;
        ListView listView1 = (ListView) findViewById(android.R.id.list);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                ArrayList itemlist = bookListAdapter.getItemList(position);
                Intent intent = new Intent(that, HbChapterList.class);

                intent.putStringArrayListExtra(EXTRA_MESSAGE, itemlist);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onVersionClick(DialogFragment dialog) {

    }

    @Override
    public void onGetMoreVersionsClick(DialogFragment dialog) {
        Intent intent = new Intent(this, HbMoreVersions.class);
        startActivity(intent);
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
        LinearLayout hbbottom = (LinearLayout)findViewById(R.id.hb_bottommenu);

        hbmenubar.setBackgroundColor(Color.parseColor(color));
        hbbottom.setBackgroundColor(Color.parseColor(color));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }
}
