package com.kfive.hopebook.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebook.R;
import com.kfive.hopebook.models.BibleVersionKey;
import com.kfive.hopebook.models.CrossReference;
import com.kfive.hopebook.models.Version;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class VersionHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HopeBook";

    // Books table name
    private static final String TABLE_VERSION = "t_kjv";

    private static final String TABLE_KEY_VERSION = "key_english";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_B = "b";
    private static final String KEY_C = "c";
    private static final String KEY_V = "v";
    private static final String KEY_T = "t";

    private final Context APPCONTEXT ;


    private static final String[] COLUMNS = {KEY_ID,KEY_B,KEY_C,KEY_V,KEY_T};


    public VersionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        APPCONTEXT = context;
    }
    //getters and setters
    public static String getTableVersion() {
        return TABLE_VERSION;
    }


    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        //table will already be created

        //Execute Query to create table
        //db.execSQL(CREATE_BOOK_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_VERSION);

        // create fresh books table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud

    public void dbQueryExec(String query){
        if(query != ""){
            // 1. get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
        }
    }
    public Version findOne(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_VERSION, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
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
        Log.d("getCrossReference", version.toString());

        // 5. return bible key version
        return version;
    }

    public int getVerseCount(int startverse, int endverse){
        // 1. build the query SELECT * FROM bible.t_asv WHERE id BETWEEN 01001001 AND 02001005
        String query = "SELECT  count(*) FROM " + TABLE_VERSION  + " WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        db.close();
        return count;
    }
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


    public Cursor getVerseText(int startverse, int endverse) {
        //this method returns a cursor to be used by the cursor adapter class

        String TABLE_VERSION = getCurrentVersion().getTable();

        // 1. build the query
       // String query = "SELECT  rowid _id,* FROM " + TABLE_VERSION  + " WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";
       String query = "SELECT "+TABLE_VERSION+".rowid _id,* FROM " + TABLE_VERSION  + " INNER JOIN "+ TABLE_KEY_VERSION +" ON "+ TABLE_VERSION +".b = "+ TABLE_KEY_VERSION + ".b WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }


    public int getVerseId(int rowid) {
        //this method returns a cursor to be used by the cursor adapter class

        String TABLE_VERSION = getCurrentVersion().getTable();

        // 1. build the query
        String query = "SELECT  rowid _id,* FROM " + TABLE_VERSION  + " WHERE rowid = '" + rowid +"'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        int count= cursor.getInt(1);
        db.close();
        return count;
    }
}
