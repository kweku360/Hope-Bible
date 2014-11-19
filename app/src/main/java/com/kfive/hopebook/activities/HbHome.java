package com.kfive.hopebook.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.kfive.hopebook.R;
import com.kfive.hopebook.adapters.HomeSwipeAdapter;
import com.kfive.hopebook.data.BibleVersionKeyHelper;
import com.kfive.hopebook.fragments.HbBibleVersion;
import com.kfive.hopebook.fragments.HbColortag;
import com.kfive.hopebook.helpers.DbInitHelper;
import com.kfive.hopebook.helpers.DbInitSdHelper;
import com.kfive.hopebook.models.BibleVersionKey;

import java.io.IOException;

public class HbHome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lets create our db if it doesnt exist
        //TODO  Check storage spaceto set database location
        //TODO Init Database in Splash Activity
        DbInitHelper initDb = new DbInitHelper(this);
        try {
            initDb.createDataBase();
        } catch (IOException e) {
            Log.v("DatabaseInitialize",e.toString());
        }


        setContentView(R.layout.activity_hb_home);

        //swiper page
        HomeSwipeAdapter sadapter = new HomeSwipeAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.apppager);
        viewPager.setAdapter(sadapter);

 }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.v("orienchanged", "Landscape");
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            Log.v("orienchanged", "portrait");
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_home, menu);
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

    //when bible version is clicked
    public void onBibleVersionClick(View v){

        HbBibleVersion hbBibleVersion = new HbBibleVersion();

        hbBibleVersion.show(getSupportFragmentManager(),"Version Dialog");
    }

    public void onBookmarkClick(View v){
        Intent intent = new Intent(this, HbBookmarks.class);
        startActivity(intent);
    }
    public void onColortagClick(View v){
        Intent intent = new Intent(this, HbColortags.class);
        startActivity(intent);
    }
    public void onSearchClick(View v){
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);
    }

    public void onReadBible(View v){
//        start bible selector intent and activity
        Intent intent = new Intent(this, HbBooksAll.class);
        startActivity(intent);

//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "some free message");
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);
//        //a bit more verbose
//        startActivity(Intent.createChooser(sendIntent, "Choose your Destiny"));

    }

    public void onSearchPage(View v){
        //        start bible Search intent and activity
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);

    }

}
