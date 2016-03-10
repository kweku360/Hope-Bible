package com.kfive.hopebible.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebible.models.CrossReference;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class CrossReferenceHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BibleDb";

    // Books table name
    private static final String TABLE_CROSS_REFERENCE = "cross_reference";

    // Books Table Columns names
    private static final String KEY_VID = "vid";
    private static final String KEY_R = "r";
    private static final String KEY_SV = "sv";
    private static final String KEY_EV = "ev";
   
    private static final String[] COLUMNS = {KEY_VID,KEY_R,KEY_SV,KEY_EV};


    public CrossReferenceHelper(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CROSS_REFERENCE);

        // create fresh books table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud
    public void addOne(CrossReference crossReference){
        //for logging
        Log.d("addOne", crossReference.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_VID, crossReference.getVid());
        values.put(KEY_R, crossReference.getR());
        values.put(KEY_SV, crossReference.getSv());
        values.put(KEY_EV, crossReference.getEv());

        // 3. insert
        db.insert(TABLE_CROSS_REFERENCE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public CrossReference findOne(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_CROSS_REFERENCE, // a. table
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
        CrossReference crossReference = new CrossReference();
        crossReference.setVid(Integer.parseInt(cursor.getString(0)));
        crossReference.setR(cursor.getString(1));
        crossReference.setSv(cursor.getString(2));
        crossReference.setEv(cursor.getString(3));



        //log
        Log.d("getCrossReference", crossReference.toString());

        // 5. return bible key version
        return crossReference;
    }

    public Cursor findAll() {
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CROSS_REFERENCE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

    public int update(CrossReference crossReference) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_R, crossReference.getR());
        values.put(KEY_SV, crossReference.getSv());
        values.put(KEY_EV, crossReference.getEv());

        // 3. updating row
        int i = db.update(TABLE_CROSS_REFERENCE, //table
                values, // column/value
                KEY_VID + " = ?", // selections
                new String[]{String.valueOf(crossReference.getVid())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteBook(CrossReference crossReference) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_CROSS_REFERENCE, //table name
                KEY_VID+" = ?",  // selections
                new String[] { String.valueOf(crossReference.getVid()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteCrossReference", crossReference.toString());

    }
}
