package com.kfive.hopebible.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebible.models.KeyEnglish;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class KeyEnglishHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BibleDb";

    // Books table name
    private static final String TABLE_KEY_ENGLISH = "key_english";

    // Books Table Columns names
    private static final String KEY_B = "b";
    private static final String KEY_N = "n";

    private static final String[] COLUMNS = {KEY_B,KEY_N};


    public KeyEnglishHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        //table will already be created

        //Execute Query to create table
        //db.execSQL(CREATE_BOOK_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_KEY_ENGLISH);

        // create fresh books table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud
    public void addOne(KeyEnglish keyEnglish){
        //for logging
        Log.d("addOne", keyEnglish.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_B, keyEnglish.getB());
        values.put(KEY_N, keyEnglish.getN());

        // 3. insert
        db.insert(TABLE_KEY_ENGLISH, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public KeyEnglish findOne(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_KEY_ENGLISH, // a. table
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
        KeyEnglish keyEnglish = new KeyEnglish();
        keyEnglish.setB(Integer.parseInt(cursor.getString(0)));
        keyEnglish.setN(cursor.getString(1));

        //log
        Log.d("getKeyEnglish", keyEnglish.toString());

        // 5. return bible key version
        return keyEnglish;
    }

    public Cursor findAll() {
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_KEY_ENGLISH;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

    public int update(KeyEnglish keyEnglish) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_B, keyEnglish.getB());
        values.put(KEY_N, keyEnglish.getN());

        // 3. updating row
        int i = db.update(TABLE_KEY_ENGLISH, //table
                values, // column/value
                KEY_B + " = ?", // selections
                new String[]{String.valueOf(keyEnglish.getB())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteBook(KeyEnglish keyEnglish) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_KEY_ENGLISH, //table name
                KEY_B+" = ?",  // selections
                new String[] { String.valueOf(keyEnglish.getB()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteCrossReference", keyEnglish.toString());

    }
}
