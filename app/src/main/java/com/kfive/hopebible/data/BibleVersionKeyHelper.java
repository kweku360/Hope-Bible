package com.kfive.hopebible.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kfive.hopebible.models.BibleVersionKey;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class BibleVersionKeyHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HopeBook";

    // Books table name
    private static final String TABLE_BIBLE_VERSION_KEY = "bible_version_key";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TABLE = "tableref";
    private static final String KEY_ABBREVIATION = "abbreviation";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_VERSION = "version";
    private static final String KEY_INFO_TEXT = "info_text";
    private static final String KEY_INFO_URL = "info_url";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_COPYRIGHT = "copyright";
    private static final String KEY_COPYRIGHT_INFO = "copyright_info";

    private static final String[] COLUMNS = {KEY_ID,KEY_TABLE,KEY_ABBREVIATION,KEY_LANGUAGE,KEY_VERSION,KEY_INFO_TEXT,KEY_INFO_URL,KEY_PUBLISHER,KEY_COPYRIGHT,KEY_COPYRIGHT_INFO};


    public BibleVersionKeyHelper(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BIBLE_VERSION_KEY);

        // create fresh books table
        this.onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //other helper methods for crud
    public void addOne(BibleVersionKey bibleVersionKey){
        //for logging
        Log.d("addOne", bibleVersionKey.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, bibleVersionKey.getId());
        values.put(KEY_TABLE, bibleVersionKey.getTable());
        values.put(KEY_ABBREVIATION, bibleVersionKey.getAbbreviation());
        values.put(KEY_LANGUAGE, bibleVersionKey.getLanguage());
        values.put(KEY_VERSION, bibleVersionKey.getVersion());
        values.put(KEY_INFO_TEXT, bibleVersionKey.getInfo_text());
        values.put(KEY_INFO_URL, bibleVersionKey.getInfo_url());
        values.put(KEY_PUBLISHER, bibleVersionKey.getPublisher());
        values.put(KEY_COPYRIGHT, bibleVersionKey.getCopyright());
        values.put(KEY_COPYRIGHT_INFO, bibleVersionKey.getCopyright_info());


        // 3. insert
        db.insert(TABLE_BIBLE_VERSION_KEY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public BibleVersionKey findOne(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BIBLE_VERSION_KEY, // a. table
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
        BibleVersionKey bibleVersionKey = new BibleVersionKey();
        bibleVersionKey.setId(Integer.parseInt(cursor.getString(0)));
        bibleVersionKey.setTable(cursor.getString(1));
        bibleVersionKey.setAbbreviation(cursor.getString(2));
        bibleVersionKey.setLanguage(cursor.getString(3));
        bibleVersionKey.setVersion(cursor.getString(4));
        bibleVersionKey.setInfo_text(cursor.getString(5));
        bibleVersionKey.setInfo_url(cursor.getString(6));
        bibleVersionKey.setPublisher(cursor.getString(7));
        bibleVersionKey.setCopyright(cursor.getString(8));
        bibleVersionKey.setCopyright_info(cursor.getString(9));


        //log
        Log.d("getBibleKeyVersion", bibleVersionKey.toString());

        // 5. return bible key version
        return bibleVersionKey;
    }

    public Cursor findAll() {
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
        String query = "SELECT  rowid _id,* FROM " + TABLE_BIBLE_VERSION_KEY;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

    public int update(BibleVersionKey bibleVersionKey) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TABLE, bibleVersionKey.getTable());
        values.put(KEY_ABBREVIATION, bibleVersionKey.getAbbreviation());
        values.put(KEY_LANGUAGE, bibleVersionKey.getLanguage());
        values.put(KEY_VERSION, bibleVersionKey.getVersion());
        values.put(KEY_INFO_TEXT, bibleVersionKey.getInfo_text());
        values.put(KEY_INFO_URL, bibleVersionKey.getInfo_url());
        values.put(KEY_PUBLISHER, bibleVersionKey.getPublisher());
        values.put(KEY_COPYRIGHT, bibleVersionKey.getCopyright());
        values.put(KEY_COPYRIGHT_INFO, bibleVersionKey.getCopyright_info());

        // 3. updating row
        int i = db.update(TABLE_BIBLE_VERSION_KEY, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(bibleVersionKey.getId())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteBook(BibleVersionKey bibleVersionKey) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BIBLE_VERSION_KEY, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(bibleVersionKey.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBibleVersionKey", bibleVersionKey.toString());

    }
}
