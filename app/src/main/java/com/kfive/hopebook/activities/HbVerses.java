package com.kfive.hopebook.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.data.VersionHelper;
import com.kfive.hopebook.fragments.HbBibleVersion;

import java.util.ArrayList;

public class HbVerses extends ActionBarActivity {
    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebookbete.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_verses);

        showVerses();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_verses, menu);
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
        Intent intent = new Intent(this, HbHome.class);
        startActivity(intent);
    }

    public void onVersion(View v){
        HbBibleVersion hbBibleVersion = new HbBibleVersion();

        hbBibleVersion.show(getSupportFragmentManager(),"Version Dialog");
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

    public void showVerses(){
        //first we get the intent as an arraylist
        Intent intent = getIntent();
        final ArrayList<String> message = intent.getStringArrayListExtra(HbChapterList.EXTRA_MESSAGE);
        //set Title
        setTitle("All verses of " + message.get(0) + " "+message.get(3));
        //We build our start and end verses
        final String startverse = message.get(1)+message.get(2)+"00"+1;
        final String endverse = message.get(1)+message.get(2)+999;
        Log.d("verses", startverse);
        Log.d("verses", endverse);
        //lets connect to out table and get the verse count
        VersionHelper versionHelper = new VersionHelper(this);
        int versenumber = versionHelper.getVerseCount(Integer.parseInt(startverse),Integer.parseInt(endverse));
        Log.d("verses", String.valueOf(versenumber));

//        niceee.. not lets populate our list
        String[] verselist = new String[versenumber];
        for(int i = 0;i < versenumber;i++){
           int k = i+1;
            verselist[i] = ""+k;
        }
        //then we pass it to an array adapter to be displayed
        ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(this,
                R.layout.hb_simple_list, verselist);
        ListView listView1 = (ListView) findViewById(android.R.id.list);

        listView1.setAdapter(aAdapter);

        //finally we register a click event listener for the listview.
        final Context that = this;

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //first we build another array list to be sent as intent
                ArrayList<String> itemlist = new ArrayList<String>();
                itemlist.add(message.get(0)); //0 the book name
                itemlist.add(startverse);//1 start verse
                itemlist.add(endverse);//2 end verse
                itemlist.add(String.valueOf(position));//3 position or target verse
                itemlist.add(message.get(3));//4 this is for title purposes more ore less add 1 to the previous listitem value

                Intent intent = new Intent(that, HbBibleText.class);

                intent.putStringArrayListExtra(EXTRA_MESSAGE,itemlist);
                startActivity(intent);
            }
        });


    }
}