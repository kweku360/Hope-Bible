package com.kfive.hopebible.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.adapters.BibleTextAdapter;
import com.kfive.hopebible.data.BookmarksHelper;
import com.kfive.hopebible.data.ColortagsHelper;
import com.kfive.hopebible.data.VersionHelper;
import com.kfive.hopebible.fragments.HbBibleVersion;
import com.kfive.hopebible.fragments.HbColortag;
import com.kfive.hopebible.helpers.BibleHelper;
import com.kfive.hopebible.helpers.ThemeHelper;
import com.kfive.hopebible.helpers.UtilityHelper;
import com.kfive.hopebible.models.Bookmark;
import com.kfive.hopebible.models.Colortag;
import com.kfive.hopebible.uihelpers.AppTextViewDosis;
import com.kfive.hopebible.uihelpers.AppTextViewLibreBold;

import java.util.ArrayList;

public class HbBibleText extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,HbColortag.HbColortagListener,HbBibleVersion.HbBibleVersionListener {


    private Cursor SELECTEDITEM;
    private int SELECTEDPOSITION;
    private View SELECTEDVIEW;

    VersionHelper versionHelper;
    BibleHelper bibleHelper;
    int[] verses;
    //init
    BibleTextAdapter cAdapter;

    //Setup popup menu
    PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeid = new ThemeHelper(getApplicationContext()).getTheme();
        setTheme(themeid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_bible_text);

//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // lets settle on one version helper
        versionHelper = new VersionHelper(this);
        bibleHelper = new BibleHelper();
        //
        verses = bibleHelper.getStartEndVerses(getIntent());
        showBibleText(verses[0],verses[1]);

        //register list item click
        bibletextClick();

    }

    private void bibletextClick() {
        final Context that = this;
        final ListView listView1 = (ListView) findViewById(android.R.id.list);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //lets assign clicked item cursor to private variable
                SELECTEDITEM = (Cursor) listView1.getAdapter().getItem(position);
                SELECTEDPOSITION = position + 1;
                SELECTEDVIEW = view;

                //Set background color of selected item
                view.setSelected(true);
                //Setup popup menu
                popupMenu = new PopupMenu(that, view);
                popupMenu.setOnMenuItemClickListener(HbBibleText.this);

                popupMenu.inflate(R.menu.hb_bible_text_options);

                //we check if view is already bookmarked
                ImageView bookmarkimg = (ImageView) view.findViewById(R.id.bookmarkimg);
                if(bookmarkimg.getVisibility() == View.VISIBLE){
                    //if its bookmarked then we set our popupmenu to remove bookmark
                    popupMenu.getMenu().findItem(R.id.versebookmark).setTitle("remove bookmark");
                }else {
                    //if its NOT bookmarked we set our popupmenu to SET bookmark
                    popupMenu.getMenu().findItem(R.id.versebookmark).setTitle("set bookmark");
                }


                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String fullverse = SELECTEDITEM.getString(8) + " " + SELECTEDITEM.getInt(3) + " : " + SELECTEDPOSITION;
        String shareVal = fullverse+"\n"+SELECTEDITEM.getString(5)+"\n\n" + "Hope Bible";
        switch (item.getItemId()) {
//            case R.id.versehighlight:
//                HbColortag hbColortag = new HbColortag();
//
//                hbColortag.show(getSupportFragmentManager(),"Color Tag Dialog");
//                return true;
            case R.id.versecopy:
                Log.v("pop", "comedy");
                return true;
            case R.id.verseshare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,shareVal);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            case R.id.versebookmark:
                //Here also we use the visibility to know whether to remove or add
                //we check if view is already bookmarked
                ImageView bookmarkimg = (ImageView) SELECTEDVIEW.findViewById(R.id.bookmarkimg);
                if(bookmarkimg.getVisibility() == View.VISIBLE){
                   //here if view is visible we can remove bookmark
                    removeBookmark();
                }else {
                    addBookmark();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_bible_text, menu);
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

    //custom methods here

    public void onHome(View v){
        Intent intent = new Intent(this, HbLanding.class);
        startActivity(intent);
    }

    public void onVersion(View v){
        HbBibleVersion hbBibleVersion = new HbBibleVersion();
        hbBibleVersion.show(getSupportFragmentManager(), "Version Dialog");

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
    public void addBookmark() {
        int[] verses = bibleHelper.getStartEndVerses(getIntent());//gets the start - end verses

        Bookmark bookmark = new Bookmark();
        bookmark.setStartverse(verses[0]);
        bookmark.setEndverse(verses[1]);
        bookmark.setVerse(SELECTEDITEM.getInt(1));
        bookmark.setVersetext(SELECTEDITEM.getString(5));
        String fullverse = SELECTEDITEM.getString(8) + " " + SELECTEDITEM.getInt(3) + " : " + SELECTEDPOSITION;
        bookmark.setFullverse(fullverse);
        bookmark.setTimestamp(System.currentTimeMillis() / 1000);
        bookmark.setColortag("NULL");
        bookmark.setUserid(23432);

        BookmarksHelper bookmarksHelper = new BookmarksHelper(this);
        bookmarksHelper.addOne(bookmark);
        //finally we set the visibility of the view
        ImageView bookmarkimg = (ImageView) SELECTEDVIEW.findViewById(R.id.bookmarkimg);
        bookmarkimg.setVisibility(View.VISIBLE);
        }

    public void removeBookmark(){
        BookmarksHelper bookmarksHelper = new BookmarksHelper(this);
        bookmarksHelper.deleteBookmark(SELECTEDITEM.getInt(1));
        //finally we set the visibility of the view
        ImageView bookmarkimg = (ImageView) SELECTEDVIEW.findViewById(R.id.bookmarkimg);
        bookmarkimg.setVisibility(View.GONE);
    }

    //overload method
    private void showBibleText(int startverse,int endverse){
        String dbName = new UtilityHelper(this).getCurrentDBName();
        Cursor cursor = versionHelper.getVerseText(startverse,endverse,dbName);
        if(cAdapter == null){
            //we are dealing with an intent service
            //custom cursor adaptor
            cAdapter = new BibleTextAdapter(this, cursor, 0);
            final ListView listView1 = (ListView) findViewById(android.R.id.list);
            listView1.setAdapter(cAdapter);
            listView1.setSelection(verses[2]);
        }

       cAdapter.changeCursor(cursor);
       cAdapter.notifyDataSetChanged();

        setTitle();
    }

    public void onNextChapter(View v){
            int rowid =  bibleHelper.getRowId(cAdapter,"next"); //now this gets us the next row id
            String dbName = new UtilityHelper(this).getCurrentDBName();
            int startverse = versionHelper.getVerseId(rowid,dbName);
            int[] verses = bibleHelper.prevNextChapter("next",startverse);
            showBibleText(verses[0],verses[1]);

    }
    public void onPreviousChapter(View v){
            int rowid =  bibleHelper.getRowId(cAdapter,"prev"); //now this gets us the prev row id
            String dbName = new UtilityHelper(this).getCurrentDBName();
            int endverse = versionHelper.getVerseId(rowid,dbName);
            int[] verses = bibleHelper.prevNextChapter("prev",endverse);
            showBibleText(verses[0],verses[1]);
    }

    private void setTitle(){
        Cursor cs = (Cursor)cAdapter.getItem(0);
        //lets set the new title
        String bibleversion = versionHelper.getCurrentVersion().getAbbreviation();
        AppTextViewDosis bookname = (AppTextViewDosis) findViewById(R.id.hb_bookname);
        bookname.setText(cs.getString(8));

        AppTextViewDosis bookchapter = (AppTextViewDosis) findViewById(R.id.hb_bookchapter);
        bookchapter.setText("Chapter " + cs.getString(3)+ " ( " + bibleversion +" )");
//        getSupportActionBar().setTitle(cs.getString(8)+ " "+ cs.getInt(3)+ " ( " + bibleversion +" )");
    }

    private void saveVerseToHistory(String fullverse,String txt,String version) {
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.lastverse), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lastversetext", txt);
        editor.putString("lastfullverse", fullverse);
        editor.putString("lastversion", version);
        editor.commit();
    }

    //we implement dialog listeners/ callback event

    @Override
    public void onColorClick(DialogFragment dialog,String ColorCode) {
        TextView versetext = (TextView) SELECTEDVIEW.findViewById(R.id.versetext);
        versetext.setBackgroundColor(Color.parseColor(ColorCode));
        dialog.dismiss();
        //Lets add entry to colortag table or update if it exists
       // addColortag(SELECTEDITEM.getInt(1),ColorCode);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    //Implementing bible version callbacks
    @Override
    public void onVersionClick(DialogFragment dialog) {
        bibleHelper.getStartEndVerses(getIntent()); //this is called just in case
        showBibleText(bibleHelper.getStartv(), bibleHelper.getEndv());
    }

    @Override
    public void onGetMoreVersionsClick(DialogFragment dialog) {
//        Intent intent = new Intent(this, HbMoreVersions.class);
//        startActivity(intent);
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
//        String color = getColorTheme();
//        LinearLayout hbmenubar = (LinearLayout)findViewById(R.id.hb_menubar);
//        RelativeLayout hbfooter = (RelativeLayout)findViewById(R.id.hb_footer);
//        // ImageButton searchbtn = (ImageButton)findViewById(R.id.hb_searchbtn);
//
//        hbmenubar.setBackgroundColor(Color.parseColor(color));
//        hbfooter.setBackgroundColor(Color.parseColor(color));
//        // searchbtn.setBackgroundColor(Color.parseColor(color));
//
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }
}


//    private void addColortag(int verse,String colorCode) {
//        Colortag colortag = new Colortag();
//        colortag.setStartverse(STARTVERSE);
//        colortag.setEndverse(ENDVERSE);
//        colortag.setVerse(SELECTEDITEM.getInt(1));
//        colortag.setVersetext(SELECTEDITEM.getString(5));
//        String fullverse = SELECTEDITEM.getString(7) + " " + SELECTEDITEM.getInt(3) + " : " + SELECTEDPOSITION;
//        colortag.setFullverse(fullverse);
//        colortag.setTimestamp(System.currentTimeMillis() / 1000);
//        colortag.setColortag(colorCode);
//        colortag.setUserid(23432);
//
//        ColortagsHelper colortagsHelper = new ColortagsHelper(this);
//        Colortag ctag = colortagsHelper.findOne(verse);
//        if(ctag == null){
//            colortagsHelper.addOne(colortag);
//        }else{
//            ctag.setColortag(colorCode);
//            colortagsHelper.update(ctag);
//        }
//    }