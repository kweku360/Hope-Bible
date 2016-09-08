package com.kfive.hopebible.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kfive.hopebible.R;
import com.kfive.hopebible.fragments.HbBibleVersion;
import com.kfive.hopebible.helpers.SearchHelper;

import java.util.ArrayList;

public class HbSearchPage extends AppCompatActivity implements HbBibleVersion.HbBibleVersionListener {

    //Extra intent message
    public static final String EXTRA_MESSAGE = "com.kfive.hopebible.MESSAGE";

    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_search_page);
        setResourcesColor();
        getSupportActionBar().setElevation(0);
        setSearchFonts();
        setSelectCriteria();
        setTitle("Search Bible");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void setSearchFonts() {
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), CUSTOM_FONT);

        TextView txtsearch = (TextView) findViewById(R.id.txtsearch);
        txtsearch.setTypeface(myTypeface);

        TextView txtsearchin = (TextView) findViewById(R.id.txtsearchin);
        txtsearchin.setTypeface(myTypeface);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hb_search_page, menu);

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

    //method shows spinner with filter values
    private void setSelectCriteria() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.searchfilter, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    //Events
    //events

    public void onHome(View v){
        Intent intent = new Intent(this, HbHome.class);
        startActivity(intent);
    }

    public void onBookSelect(View v){
        Intent intent = new Intent(this, HbBooksAll.class);
        startActivity(intent);
    }
    public void onVersion(View v){
        HbBibleVersion hbBibleVersion = new HbBibleVersion();
        hbBibleVersion.show(getSupportFragmentManager(), "Version Dialog");

    }


    public void onSearchClick(View v) {
        EditText searchtext = (EditText) findViewById(R.id.searchbox);
        if(searchtext.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter Search Text", Toast.LENGTH_SHORT).show();
        }else {
            SearchHelper searchHelper = new SearchHelper(this);
            //first we build another array list to be sent as intent
            ArrayList<String> itemlist = new ArrayList<String>();

            itemlist.add(searchtext.getText().toString());

            CheckBox chkbx1 = (CheckBox) findViewById(R.id.chkbxexact);
            if(chkbx1.isChecked()){
                itemlist.add("true");
            }else{itemlist.add("false"); }

            Spinner spinner = (Spinner)findViewById(R.id.spinner);
            String searchcriteria = spinner.getSelectedItem().toString();
            itemlist.add(searchcriteria);


            Intent intent = new Intent(this, HbSearchResult.class);

            intent.putStringArrayListExtra(EXTRA_MESSAGE, itemlist);
            startActivity(intent);
        }

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
        ImageButton searchbtn = (ImageButton)findViewById(R.id.hb_searchbtn);

        hbmenubar.setBackgroundColor(Color.parseColor(color));
        searchbtn.setBackgroundColor(Color.parseColor(color));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

    @Override
    public void onVersionClick(DialogFragment dialog) {

    }

    @Override
    public void onGetMoreVersionsClick(DialogFragment dialog) {
        Intent intent = new Intent(this, HbMoreVersions.class);
        startActivity(intent);
    }
}
