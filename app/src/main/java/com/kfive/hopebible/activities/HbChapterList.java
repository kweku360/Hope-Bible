package com.kfive.hopebible.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.fragments.HbBibleVersion;
import com.kfive.hopebible.helpers.ThemeHelper;

import java.util.ArrayList;

public class HbChapterList extends AppCompatActivity implements HbBibleVersion.HbBibleVersionListener {

    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebible.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeid = new ThemeHelper(getApplicationContext()).getTheme();
        setTheme(themeid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_chapter_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showChapterList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_chapter_list, menu);
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

    public void onHome(View v){
        Intent intent = new Intent(this, HbLanding.class);
        startActivity(intent);
    }

    public void onVersion(View v){
        HbBibleVersion hbBibleVersion = new HbBibleVersion();

        hbBibleVersion.show(getSupportFragmentManager(),"Version Dialog");
    }
    public void onBookSelect(View v) {
        Intent intent = new Intent(this, HbBooksAll.class);
        startActivity(intent);
    }
    public void onSearch(View v){
        //start the intent and search activity
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);
    }

    //this method shows the chapters for the book selected in the previous intent
    public void showChapterList(){
        //first we get the intent as an arraylist
        Intent intent = getIntent();
        final ArrayList<String> message = intent.getStringArrayListExtra(HbBooksAll.EXTRA_MESSAGE);

        //set Title
        getSupportActionBar().setTitle(message.get(0));

        //then we build a string array from the last item in the arrray list
        int chapterlength = Integer.parseInt(message.get(2));
        String[] chapterlist = new String[chapterlength];
        for(int i = 0;i < chapterlength;i++){
            int k = i + 1;
            chapterlist[i] = "Chapter "+ k;

        }
        //then we pass it to an array adapter to be displayed
        ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(this,
                R.layout.hb_simple_list, chapterlist);
        ListView listView1 = (ListView) findViewById(android.R.id.list);

        listView1.setAdapter(aAdapter);
        //finally we register a click event listener for the listview.
        final Context that = this;

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //first we build another array list to be sent as intent : format [BOOKNAME,BOODCODE,SELECTEDCHAPTER]
                ArrayList<String> itemlist = new ArrayList<String>();
                itemlist.add(message.get(0));
                itemlist.add(message.get(1));
                //finally
                position = position + 1;
                int positionlength = String.valueOf(position).length();
                if(positionlength == 1){itemlist.add("00"+position);};
                if(positionlength == 2){itemlist.add("0"+position);};
                if(positionlength == 3){itemlist.add(""+position);};

                itemlist.add(""+position);

                Intent intent = new Intent(that, HbVerses.class);

                intent.putStringArrayListExtra(EXTRA_MESSAGE,itemlist);
                startActivity(intent);
}
        });

    }

    @Override
    public void onVersionClick(DialogFragment dialog) {

    }

    @Override
    public void onGetMoreVersionsClick(DialogFragment dialog) {
//        Intent intent = new Intent(this, HbMoreVersions.class);
//        startActivity(intent);
    }
//Deprecated
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
    //Deprecated
    private void setResourcesColor(){
        String color = getColorTheme();
        LinearLayout hbmenubar = (LinearLayout)findViewById(R.id.hb_menubar);
        hbmenubar.setBackgroundColor(Color.parseColor(color));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }
}
