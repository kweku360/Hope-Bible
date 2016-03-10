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
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.async.SearchResultAsync;
import com.kfive.hopebible.data.VersionHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HbSearchResult extends ActionBarActivity {
    SearchResultAsync searchResultAsync = new SearchResultAsync(this);
    ProgressBar pg;
    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebible.MESSAGE";

    //classes
    VersionHelper versionHelper = new VersionHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_search_result);
        setResourcesColor();
        getSupportActionBar().setElevation(0);
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        pg.setVisibility(View.VISIBLE);
        showSearchResult();

        onResultItemClick();
    }

    private void onResultItemClick() {

            final Context that = this;
            final ListView listView1 = (ListView) findViewById(R.id.hb_result);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    //first we get cursor on clicked position
                    Cursor c = (Cursor) listView1.getAdapter().getItem(position);
                    //then we get start and end verses and parse to versionhelper to get full details on

                    //bible verse
                    // Cursor txtcursor = versionHelper.getVerseText(c.getInt(3),c.getInt(4));
                    String bookname = c.getString(c.getColumnIndex("n"));
                    String startverse = "";
                    String endverse = "";
                    String verse = "" + c.getInt(1);
                    if(verse.length() == 7){
                        startverse = verse.substring(0, 4) + "001";
                        endverse = verse.substring(0, 4) + "999";
                    }else{
                        startverse = verse.substring(0, 5) + "001";
                        endverse = verse.substring(0, 5) + "999";
                    }

                    String verseposition = verse.substring(verse.length() - 3).replace("0", "");
                    int versepositionfix = Integer.parseInt(verseposition) - 1;
                    verseposition = "" + versepositionfix;

                    String chapter = verse.substring(2, 5).replace("0", "");

                    Log.v("start v", startverse);
                    Log.v("end  v", endverse);
                    Log.v("pos", verseposition);

//                    now we can create our intent well
//                    first we build another array list to be sent as intent
                    ArrayList<String> itemlist = new ArrayList<String>();
                    itemlist.add(bookname); //0 the book name
                    itemlist.add(startverse);//1 start verse
                    itemlist.add(endverse);//2 end verse
                    itemlist.add(verseposition);//3 position or target verse
                    itemlist.add(chapter);//4 this is for title purposes more ore less add 1 to the previous listitem value

                    Intent intent = new Intent(that, HbBibleText.class);

                    intent.putStringArrayListExtra(EXTRA_MESSAGE, itemlist);
                    startActivity(intent);

                }
            });

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

        searchResultAsync.setAsyncTaskResponseListener(new SearchResultAsync.AsyncTaskResponse() {
            @Override
            public void processFinish(Cursor Output) {
                //lets inflate a view

                String[] fromColumns = {"n", "c", "v", "t"};
                int[] toViews = {R.id.searchversebook, R.id.searchversechapter, R.id.searchverseno, R.id.searchversetest};

                // Create an empty adapter we will use to display the loaded data.
                // We pass null for the cursor, then update it in onLoadFinished()
                SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(that,
                        R.layout.hb_search_result_itemview, Output,
                        fromColumns, toViews, 0);
                //lets play around here
                cAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        if (view.getId() == R.id.searchversetest) {
                            //what this code does it it searches for a substring within a given string and
                            //highlight the substring-- pretty cool..huh :)

                            String textString = cursor.getString(cursor.getColumnIndex("t"));
                            Log.v("messageownertxt", message.get(0));
                            Pattern verse = Pattern.compile(message.get(0), Pattern.CASE_INSENSITIVE);
                            Matcher match = verse.matcher(textString);
                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(textString);
                            while (match.find()) {
                                Log.v("messagetxt", spanText.toString());
                                spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), match.start(), match.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ;
                            }
                            ((TextView) view).setText(spanText);
                            return true;
                        }
                        return false;
                    }
                });


                ListView mylist = (ListView) findViewById(R.id.hb_result);
                mylist.setAdapter(cAdapter);
                pg.setVisibility(View.GONE);
                TextView pageinfo = (TextView) findViewById(R.id.pageinfo);
                pageinfo.setText("Search Returned " + cAdapter.getCount() + " verses");


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
       // ImageButton searchbtn = (ImageButton)findViewById(R.id.hb_searchbtn);

        hbmenubar.setBackgroundColor(Color.parseColor(color));
       // searchbtn.setBackgroundColor(Color.parseColor(color));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
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
