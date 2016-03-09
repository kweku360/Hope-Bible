package com.kfive.hopebook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kfive.hopebook.R;
import com.kfive.hopebook.fragments.HBHomeAppShelve;
import com.kfive.hopebook.fragments.HbBibleVersion;
import com.kfive.hopebook.fragments.HbColortag;
import com.kfive.hopebook.fragments.HbHomeLanding;
import com.kfive.hopebook.fragments.HbTheme;
import com.kfive.hopebook.helpers.DbInitHelper;
import com.kfive.hopebook.uihelpers.ThemeRes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HbHome extends ActionBarActivity implements HbBibleVersion.HbBibleVersionListener,HbTheme.HbThemeListener {

    //fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HBHomeAppShelve hbHomeAppShelve = new HBHomeAppShelve();
    HbHomeLanding hbHomeLanding = new HbHomeLanding();
    private static final String CUSTOM_FONT = "fonts/Signika-Semibold.ttf";

    ImageView HomeImg;

    ThemeRes themeRes;
    TextView lverse;
    TextView lverseval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Parse.initialize(this, "Lu2DjUhR8yfY9zfIzKkY28pCxp6G2rGSWaYuVmIK", "kmMx9pW2tcVon1VBYEXnNltQsf82o4mFsiW1cCAN");


        // ParseObject testObject = new ParseObject("TestObject");
        // testObject.put("foo", "bar");
        //  testObject.saveInBackground();
        //lets create our db if it doesnt exist
        //TODO  Check storage space to set database location
        //TODO Init Database in Splash Activity
        DbInitHelper initDb = new DbInitHelper(this);
        try {
            initDb.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            initDb.openDataBase();

        } catch (Exception sqle) {

            Log.v("SQL Error", sqle.toString());

        }

        Log.v("database location", getFilesDir().toString());

        setContentView(R.layout.activity_hb_home);
        HomeImg = (ImageView)findViewById(R.id.hb_bgimg);
        lverse = (TextView)findViewById(R.id.hb_txtlastverse);
        lverseval = (TextView)findViewById(R.id.hb_lvval);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), CUSTOM_FONT);
        shuffleImage();
        getLastVerse();

    }

    private void getLastVerse() {
        SharedPreferences appprefs = getSharedPreferences(getString(R.string.lastverse), MODE_PRIVATE);
        SharedPreferences.Editor ed;
        String verse = appprefs.getString("lastversetext", "");
        String fullverse = appprefs.getString("lastfullverse", "");
        lverse.setText("Last Verse - "+fullverse);
        lverseval.setText(verse);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setResourcesColor();
        shuffleImage();
    }

    private void shuffleImage() {
        ArrayList<Integer> imlist = new ArrayList<>();
        imlist.add(R.drawable.hbimg_cross);
        imlist.add(R.drawable.hbimg_hope);
        imlist.add(R.drawable.hbimg_tablet);
        imlist.add(R.drawable.hbimg_church);
        Random random = new Random();
        int i = random.nextInt(3 - 0 + 1) + 0;
        HomeImg.setImageResource(imlist.get(i));

    }

    private String getColorTheme(){
        SharedPreferences appprefs = getSharedPreferences("com.kfive.hopebook.bible", MODE_PRIVATE);
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
        LinearLayout hbbometop = (LinearLayout)findViewById(R.id.hb_hometop);
        Button hbreadbible = (Button)findViewById(R.id.hb_readbiblebtn);
        TextView hblvtxt = (TextView)findViewById(R.id.hb_txtlastverse);
        TextView hblvval = (TextView)findViewById(R.id.hb_lvval);
        ImageButton dailyverse = (ImageButton)findViewById(R.id.dailyverse);
        ImageButton dailydevotion = (ImageButton)findViewById(R.id.hb_devotion);
        ImageButton search = (ImageButton)findViewById(R.id.hb_search);
        ImageButton bookmark = (ImageButton)findViewById(R.id.hb_bookmark);
        ImageButton theme = (ImageButton)findViewById(R.id.hb_theme);
        ImageButton about = (ImageButton)findViewById(R.id.hb_about);

        hbbometop.setBackgroundColor(Color.parseColor(color));
        hbreadbible.setTextColor(Color.parseColor(color));
        hblvtxt.setTextColor(Color.parseColor(color));
        hblvval.setTextColor(Color.parseColor(color));

        dailyverse.setBackgroundColor(Color.parseColor(color));
        dailydevotion.setBackgroundColor(Color.parseColor(color));
        search.setBackgroundColor(Color.parseColor(color));
        bookmark.setBackgroundColor(Color.parseColor(color));
        theme.setBackgroundColor(Color.parseColor(color));
        about.setBackgroundColor(Color.parseColor(color));
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

    public void onDailyDevotionClick(View v){

        Toast.makeText(this, "Daily Devotion Coming Soon to Hope Bible", Toast.LENGTH_LONG).show();
    }
    public void onDailyVerseClick(View v){

        Toast.makeText(this, "Daily Verse Coming Soon to Hope Bible", Toast.LENGTH_LONG).show();
    }

    //function runs when themes is clicked
    public void onThemeClick(View v){
        HbTheme hbTheme = new HbTheme();
        hbTheme.show(getSupportFragmentManager(), "Theme Dialog");
    }

    public void onSearchClick(View v){
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);
    }

    public void onReadBible(View v){
//        start bible selector intent and activity
        Intent intent = new Intent(this, HbBooksAll.class);
        startActivity(intent);

    }

    public void onSearchPage(View v){
        //        start bible Search intent and activity
        Intent intent = new Intent(this, HbSearchPage.class);
        startActivity(intent);

    }

    //Implement bible version dialog listeners
    @Override
    public void onVersionClick(DialogFragment dialog) {

    }

    //we implement dialog listeners/ callback event

    @Override
    public void onThemeClick(DialogFragment dialog,String ColorCode) {
        //first we save to shared prefs
        SharedPreferences appprefs = getSharedPreferences("com.kfive.hopebook.bible", MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = appprefs.edit();
        ed.putString("color", ColorCode);
        ed.commit();
        //now we call the funtion to change theme
        setResourcesColor();

        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onGetMoreVersionsClick(DialogFragment dialog) {
        Intent intent = new Intent(this, HbMoreVersions.class);
        startActivity(intent);
    }

//    //more overides
//
//    @Override
//    public Resources getResources() {
//
//        if (themeRes == null) {
//            themeRes = new ThemeRes(super.getResources());
//        }
//        return themeRes;
//    }
}


//lets show our first fragment programatically
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.app_frame, hbHomeLanding);
//        fragmentTransaction.add(R.id.app_frame, hbHomeAppShelve);
//        fragmentTransaction.hide(hbHomeAppShelve);
//        fragmentTransaction.commit();

//swiper page
//        HomeSwipeAdapter sadapter = new HomeSwipeAdapter(getSupportFragmentManager());
//        ViewPager viewPager = (ViewPager) findViewById(R.id.apppager);
//        viewPager.setAdapter(sadapter);


//    public void hb_frametoggle(View view) {
//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//
//
//        //some animations
//       ft.setCustomAnimations(R.anim.transition_slide_up, R.anim.transition_slide_down);
//
//        if(hbHomeAppShelve.isHidden()){
//            // Log.v("Love", "me");
//            ft.show(hbHomeAppShelve);
//
//        }else{
//            ft.hide(hbHomeAppShelve);
//
//        }
//
//        ft.commit();
//
//    }
