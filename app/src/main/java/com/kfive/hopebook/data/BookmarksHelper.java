package com.kfive.hopebook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebook.models.Bookmark;

/**
 * Created by apple on 11/5/14.
 */
public class BookmarksHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HopeBook";
    // Bookmarks table name
    private static final String TABLE_BOOKMARKS = "bookmark";
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
    public BookmarksHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
//        String CREATE_BOOKMARKS_TABLE = "CREATE TABLE bookmark ( " +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "startverse INTEGER NOT NULL, "+
//                "endverse INTEGER NOT NULL, "+
//                "verse INTEGER NOT NULL, "+
//                "versetext TEXT NOT NULL, "+
//                "fullverse TEXT NOT NULL, "+
//                "timestamp INTEGER NOT NULL, "+
//                "colortag TEXT NOT NULL, "+
//                "userid INTEGER NOT NULL )";
//        //Execute Query to create table
//        db.execSQL(CREATE_BOOKMARKS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOKMARKS);

        // create fresh books table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud
    public void addOne(Bookmark bookmark){
        //for logging
        Log.d("addOne", bookmark.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_STARTVERSE, bookmark.getStartverse());
        values.put(KEY_ENDVERSE, bookmark.getEndverse());
        values.put(KEY_VERSE, bookmark.getVerse());
        values.put(KEY_VERSETEXT, bookmark.getVersetext());
        values.put(KEY_FULLVERSE, bookmark.getFullverse());
        values.put(KEY_TIMESTAMP, bookmark.getTimestamp());
        values.put(KEY_COLORTAG, bookmark.getColortag());
        values.put(KEY_USERID, bookmark.getUserid());

        // 3. insert
        db.insert(TABLE_BOOKMARKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    public Bookmark findOne(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOOKMARKS, // a. table
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
        Bookmark bookmark = new Bookmark();
        bookmark.setId(Integer.parseInt(cursor.getString(0)));
        bookmark.setStartverse(cursor.getInt(1));
        bookmark.setEndverse(cursor.getInt(2));
        bookmark.setVerse(cursor.getInt(3));
        bookmark.setVersetext(cursor.getString(4));
        bookmark.setFullverse(cursor.getString(5));
        bookmark.setTimestamp(cursor.getInt(6));
        bookmark.setColortag(cursor.getString(7));
        bookmark.setUserid(cursor.getInt(8));

        //log
        Log.d("getBookmark", bookmark.toString());

        // 5. return bible key version
        return bookmark;
    }

    public String[] findChapterBookmark(int verse){
         // 1. build the query
//        String query = "SELECT  rowid _id,* FROM " + TABLE_BOOKMARKS  + " WHERE startverse = '" + startverse + "' AND endverse = '" + endverse+"'";
        String query = "SELECT  rowid _id,* FROM " + TABLE_BOOKMARKS  + " WHERE verse = '" + verse + "'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] bookmarks = new String[cursor.getCount()];


        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                bookmarks[i] = cursor.getString(4);
                i++;
            } while (cursor.moveToNext());
        }

        return bookmarks;
    }

    public Cursor findAll() {
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
       //String query = "SELECT  rowid _id,* FROM " + TABLE_BOOKMARKS;
        //this qury converts the unit time stamp into into date readable string
        //String query = "SELECT  rowid _id,strftime(\"%m-%d-%Y\", timestamp, 'unixepoch') AS tstamp,* FROM " + TABLE_BOOKMARKS;

        String query = "select rowid _id,case strftime('%m',  timestamp, 'unixepoch') when '01' then 'January' when '02' then 'Febuary' when '03' then 'March' when '04' then 'April' when '05' then 'May' when '06' then 'June' when '07' then 'July' when '08' then 'August' when '09' then 'September' when '10' then 'October' when '11' then 'November' when '12' then 'December' else '' end as month,strftime(\"%d, %Y\", timestamp, 'unixepoch') AS tstamp,* FROM " +  TABLE_BOOKMARKS;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

    public int update(Bookmark bookmark) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, bookmark.getId());
        values.put(KEY_STARTVERSE, bookmark.getStartverse());
        values.put(KEY_ENDVERSE, bookmark.getEndverse());
        values.put(KEY_VERSE, bookmark.getVerse());
        values.put(KEY_VERSETEXT, bookmark.getVersetext());
        values.put(KEY_FULLVERSE, bookmark.getFullverse());
        values.put(KEY_TIMESTAMP, bookmark.getTimestamp());
        values.put(KEY_COLORTAG, bookmark.getColortag());
        values.put(KEY_USERID, bookmark.getUserid());

        // 3. updating row
        int i = db.update(TABLE_BOOKMARKS, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(bookmark.getId())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteBookmark(int verse) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKMARKS, //table name
                KEY_VERSE+" = ?",  // selections
                new String[] { String.valueOf(verse) }); //selections args

        // 3. close
        db.close();
        }
}




