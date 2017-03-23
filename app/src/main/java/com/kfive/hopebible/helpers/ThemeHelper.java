package com.kfive.hopebible.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.kfive.hopebible.R;

/**
 * Created by kweku on 3/23/2017.
 */

public class ThemeHelper {
    Context con;

    public ThemeHelper(Context con) {
        this.con = con;
    }

    public void saveTheme(String name){
        //first we save to shared prefs
        SharedPreferences appprefs = con.getSharedPreferences("com.kfive.hopebible.bible", con.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = appprefs.edit();
        ed.putString("theme", name);
        ed.commit();
    }

    public int getTheme(){
        int themeid = 0;
        SharedPreferences appprefs = con.getSharedPreferences("com.kfive.hopebible.bible", con.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        String theme = appprefs.getString("theme", "");
        switch (theme){
            case "" : themeid = R.style.HopeBibleTheme; //default implementation - red
                break;
            case "HopeBibleThemeBlue" : themeid = R.style.HopeBibleTheme_Blue; //blue
                break;
            case "HopeBibleTheme" : themeid = R.style.HopeBibleTheme; //red
                break;
        }
        return themeid;
    }
}
