package com.kfive.hopebook.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kfive.hopebook.data.VersionHelper;

/**
 * Created by apple on 10/27/14.
 */
public class SearchHelper extends VersionHelper {
    private static final String TABLE_KEY_VERSION = "key_english";
    public SearchHelper(Context context) {
        super(context);

    }

    public Cursor searchEntireBible(String searchtext){
        //this method returns a cursor to be used by the cursor adapter class
        //select from t_kjv inner join key_english on t_kjv.b = key_english.b where t like '%moses%'
        // 1. build the query
        String query = "SELECT  id _id,* FROM " + getTableVersion()  + " INNER JOIN "+ TABLE_KEY_VERSION +" ON "+ getTableVersion() +".b = "+ TABLE_KEY_VERSION + ".b WHERE t like '%" +searchtext+ "%'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }

   public String getBook(int id){
       // 1. build the query
       String query = "SELECT * FROM key_version WHERE b = '" +id+ "'";

       // 2. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(query, null);

       cursor.moveToFirst();
       String bookname = cursor.getString(1);

       // return cursor
       return String.valueOf(bookname);

   }


    public Cursor searchTestament(String searchtext,int startverse, int endverse){
        //this method returns a cursor to be used by the cursor adapter class

        // 1. build the query
        String query = "SELECT  id _id,* FROM " + getTableVersion()  + " INNER JOIN "+ TABLE_KEY_VERSION +" ON "+ getTableVersion() +".b = "+ TABLE_KEY_VERSION + ".b WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"' AND t like '%" +searchtext+"%'";


       // String query = "SELECT  rowid _id,* FROM " + VersionHelper.getTableVersion()  + " WHERE id BETWEEN '" + startverse + "' AND '" + endverse+"' AND t like '%" +searchtext+"%'";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return cursor
        return cursor;
    }
}
