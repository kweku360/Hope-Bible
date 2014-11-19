package com.kfive.hopebook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebook.helpers.DbversionHelper;
import com.kfive.hopebook.models.Bookmark;
import com.kfive.hopebook.models.Colortag;

/**
 * Created by apple on 11/12/14.
 */
public class ColortagsHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = DbversionHelper.DATABASE_VERSION;
    public static final String DATABASE_NAME = DbversionHelper.DATABASE_NAME;

    // Bookmarks table name
    private static final String TABLE_COLORTAGS = "colortag";
    //Bookmarks table column names
    private static final String KEY_ID = "id";
    private static final String KEY_STARTVERSE = "startverse";
    private static final String KEY_ENDVERSE = "endverse";
    private static final String KEY_VERSE = "verse";
    private static final String KEY_VERSETEXT = "versetext";
    private static final String KEY_FULLVERSE = "fullverse";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_COLORTAG = "colortag";
    private static final String KEY_USERID = "userid";

    private static final String[] COLUMNS = {KEY_ID,KEY_STARTVERSE,KEY_ENDVERSE,KEY_VERSE,KEY_VERSETEXT,KEY_FULLVERSE,KEY_TIMESTAMP,KEY_COLORTAG,KEY_USERID};

    //Constructor
    public ColortagsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_COLORTAGS_TABLE = "CREATE TABLE colortag ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "startverse INTEGER NOT NULL, "+
                "endverse INTEGER NOT NULL, "+
                "verse INTEGER NOT NULL, "+
                "versetext TEXT NOT NULL, "+
                "fullverse TEXT NOT NULL, "+
                "timestamp INTEGER NOT NULL, "+
                "colortag TEXT NOT NULL, "+
                "userid INTEGER NOT NULL )";
        //Execute Query to create table
        db.execSQL(CREATE_COLORTAGS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COLORTAGS);

        // create fresh table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud
    public void addOne(Colortag colortag){
        //for logging
        Log.d("addOne", colortag.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_STARTVERSE, colortag.getStartverse());
        values.put(KEY_ENDVERSE, colortag.getEndverse());
        values.put(KEY_VERSE, colortag.getVerse());
        values.put(KEY_VERSETEXT, colortag.getVersetext());
        values.put(KEY_FULLVERSE, colortag.getFullverse());
        values.put(KEY_TIMESTAMP, colortag.getTimestamp());
        values.put(KEY_COLORTAG, colortag.getColortag());
        values.put(KEY_USERID, colortag.getUserid());

        // 3. insert
        db.insert(TABLE_COLORTAGS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

//    finds one based on the colortag or color code
    public Colortag findOne(int verse){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_COLORTAGS, // a. table
                        COLUMNS, // b. column names
                        " verse = ?", // c. selections
                        new String[] { String.valueOf(verse) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();

            // 4. build bible_version_key object
            Colortag colortag = new Colortag();
            colortag.setId(Integer.parseInt(cursor.getString(0)));
            colortag.setStartverse(cursor.getInt(1));
            colortag.setEndverse(cursor.getInt(2));
            colortag.setVerse(cursor.getInt(3));
            colortag.setVersetext(cursor.getString(4));
            colortag.setFullverse(cursor.getString(5));
            colortag.setTimestamp(cursor.getInt(6));
            colortag.setColortag(cursor.getString(7));
            colortag.setUserid(cursor.getInt(8));

            return colortag ;
        }else{
            return null;
        }
   }

    public String[] findChapterBookmark(int verse){
        // 1. build the query
//        String query = "SELECT  rowid _id,* FROM " + TABLE_BOOKMARKS  + " WHERE startverse = '" + startverse + "' AND endverse = '" + endverse+"'";
        String query = "SELECT  rowid _id,* FROM " + TABLE_COLORTAGS  + " WHERE verse = '" + verse + "'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] colortag = new String[cursor.getCount()];


        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                colortag[i] = cursor.getString(4);
                i++;
            } while (cursor.moveToNext());
        }

        return colortag;
    }

    public Cursor findAll() {
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
        String query = "SELECT  rowid _id,* FROM " + TABLE_COLORTAGS;
        //this qury converts the unit time stamp into into date readable string
        //String query = "SELECT  rowid _id,strftime(\"%m-%d-%Y\", timestamp, 'unixepoch') AS tstamp,* FROM " + TABLE_BOOKMARKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

    public int update(Colortag colortag) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, colortag.getId());
        values.put(KEY_STARTVERSE, colortag.getStartverse());
        values.put(KEY_ENDVERSE, colortag.getEndverse());
        values.put(KEY_VERSE, colortag.getVerse());
        values.put(KEY_VERSETEXT, colortag.getVersetext());
        values.put(KEY_FULLVERSE, colortag.getFullverse());
        values.put(KEY_TIMESTAMP, colortag.getTimestamp());
        values.put(KEY_COLORTAG, colortag.getColortag());
        values.put(KEY_USERID, colortag.getUserid());

        // 3. updating row
        int i = db.update(TABLE_COLORTAGS, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(colortag.getId())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteColortag(int verse) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_COLORTAGS, //table name
                KEY_VERSE+" = ?",  // selections
                new String[] { String.valueOf(verse) }); //selections args

        // 3. close
        db.close();
    }

}
