package com.kfive.hopebible.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kfive.hopebible.helpers.DbInitHelper;

import java.io.IOException;

/**
 * Created by kweku on 3/23/2017.
 */

public class HbSplash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lets create our db if it doesnt exist
        //TODO MOVE to SPLASH SCREEN.
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

        Intent intent = new Intent(this, HbLanding.class);
        startActivity(intent);
        finish();
    }
}
