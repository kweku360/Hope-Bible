package com.kfive.hopebible.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebible.R;
import com.kfive.hopebible.helpers.DbInitHelper;
import com.kfive.hopebible.models.BibleVersionKey;
import com.kfive.hopebible.models.Version;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * Revised 03/27/17 by kweku kankam
 * copyright
 */
public class VersionHelper  {

    //Core database name
    public static final String DATABASE_NAME = "HopeBook";

    //Represents the Current Database being used
    public SQLiteDatabase SqlDb;

    //DBInit Helper
    public DbInitHelper myDbHelper;

    //Very useful to access the name of the current database
//    String dbName;


    private static final String TABLE_VERSION = "t_kjv";// table name from DB
    private static final String TABLE_KEY_VERSION = "key_english";// table name from Core DB

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_B = "b";
    private static final String KEY_C = "c";
    private static final String KEY_V = "v";
    private static final String KEY_T = "t";

    private final Context APPCONTEXT ;

    private static final String[] COLUMNS = {KEY_ID,KEY_B,KEY_C,KEY_V,KEY_T};

    //constructor
    public VersionHelper(Context context) {
        APPCONTEXT = context;
        myDbHelper = new DbInitHelper(context);
    }

    //Methods Here

    /**
     * Runs a SQL query Using the db.execSQL functions
     * Normally used for quick queries
     *
     * @param  query  String query
     * @param  dbName the active database (or the one in use)
     * @return void   no return value
     */
    public void dbQueryExec(String query,String dbName){
        if(query != ""){

            SqlDb = myDbHelper.openActiveDataBase(dbName);
            SqlDb.execSQL(query);
            // SqlDb.close();
        }
    }

    /**
     * Finds One Verse  from the
     * HopeBook Bible Version based in id (scripture ID)
     *
     * @param  id  bible_version_key ID
     * @param  dbName the active database (or the one in use)(not yet implemented
     * @return version  bible-version
     */

    public Version findOne(int id,String dbName){

        // 1. get reference to current DB
        SqlDb = myDbHelper.openActiveDataBase(dbName);

        // 2. build query
        Cursor cursor =
                SqlDb.query(TABLE_VERSION, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        //3. Close db after query
        // SqlDb.close();

        // 4. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build bible_version_key object
        Version version = new Version();
        version.setId(Integer.parseInt(cursor.getString(0)));
        version.setB(Integer.parseInt(cursor.getString(1)));
        version.setC(Integer.parseInt(cursor.getString(2)));
        version.setV(Integer.parseInt(cursor.getString(3)));
        version.setT(cursor.getString(4));

        //log
        //Log.d("getCrossReference", version.toString());

        return version;
    }

    /**
     * Gets Count of Verses
     * Normally Used to Show verses of a Chapter
     *But can be used for other stuff
     *
     * @param  startverse  Start Verse Code
     * @param  endverse  end Verse Code
     * @param  dbName the active database (or the one in use)(not yet implemented
     * @return count  Int Value
     */
    public int getVerseCount(int startverse, int endverse,String dbName){

        // 1. build the query SELECT * FROM bible.t_asv WHERE id BETWEEN 01001001 AND 02001005
        String query = "SELECT  count(*) FROM t_asv WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";
        int count = 0;
        try
        {
            SqlDb = myDbHelper.openActiveDataBase(dbName);
            Cursor cursor = SqlDb.rawQuery(query, null);
            cursor.moveToFirst();
            count= cursor.getInt(0);
            // SqlDb.close();
        }
        catch (SQLException mSQLException)
        {
            Log.e("Exception", "open >>"+ mSQLException.toString());
            throw mSQLException;
        }

        return count;
    }

    /**
     * Retrieves the Bible version Information from Shared Prefs
     *Normally used to get Version Info
     *
     *
     * @return Bible Version Key  model
     */
    public BibleVersionKey getCurrentVersion(){
        //we get shared pref
        Context context = APPCONTEXT;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.bibleversionpref), Context.MODE_PRIVATE);
        int defaultValue = 1;
        int version = sharedPref.getInt("currentversion", defaultValue);

        BibleVersionKeyHelper bibleVersionKeyHelper = new BibleVersionKeyHelper(APPCONTEXT);
        BibleVersionKey bibleVersionKey = bibleVersionKeyHelper.findOne(version);

        return bibleVersionKey;
    }

    /**
     * Retrieves verse text
     *Joins two tables from its current db
     *
     *
     * @param  startverse  Start Verse Code
     * @param  endverse  end Verse Code
     * @param  dbName the active database (or the one in use)(not yet implemented
     * @return cursor  DB Cursor
     */
    public Cursor getVerseText(int startverse, int endverse,String dbName) {

        //this method returns a cursor to be used by the cursor adapter class
//        String TABLE_VERSION = getCurrentVersion().getTable();
        String TABLE_VERSION = dbName;

        // 1. build the query
       // String query = "SELECT  rowid _id,* FROM " + TABLE_VERSION  + " WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";
       String query = "SELECT "+TABLE_VERSION+".rowid _id,* FROM " + TABLE_VERSION  + " INNER JOIN "+ TABLE_KEY_VERSION +" ON "+ TABLE_VERSION +".b = "+ TABLE_KEY_VERSION + ".b WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";

        // 2. get reference to writable DB
        SqlDb = myDbHelper.openActiveDataBase(dbName);
        Cursor cursor = SqlDb.rawQuery(query, null);
        // SqlDb.close();

        // return cursor
        return cursor;
    }

    public int getVerseId(int rowid,String dbName) {
        //this method returns a cursor to be used by the cursor adapter class
        // 1. build the query
        String query = "SELECT  rowid _id,* FROM " + dbName  + " WHERE rowid = '" + rowid +"'";

        // 2. get reference to writable DB
        SqlDb = myDbHelper.openActiveDataBase(dbName);
        Cursor cursor = SqlDb.rawQuery(query, null);
        // SqlDb.close();

        cursor.moveToFirst();
        int count= cursor.getInt(1);
        return count;
    }
}
