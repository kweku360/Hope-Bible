package com.kfive.hopebook.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.kfive.hopebook.helpers.SearchHelper;

/**
 * Created by apple on 11/18/14.
 */

public class SearchResultAsync extends AsyncTask<String,Void,Cursor> {
    public AsyncTaskResponse responsedelegate;
    public Context con;


    public SearchResultAsync(Context con) {
        this.con = con;
    }
    public SearchResultAsync() {

    }

    public void setAsyncTaskResponseListener(AsyncTaskResponse responsedelegate) {
        this.responsedelegate = responsedelegate;
    }

    @Override
    protected Cursor doInBackground(String... searchtext) {

        if(searchtext[2].equals("Entire Bible")){
            return searchEntireBible(searchtext[0],searchtext[1]);
        }
        if(searchtext[2].equals("New Testament")){
            return searchNewTestament(searchtext[0],searchtext[1]);
        }
        if(searchtext[2].equals("Old testament")){
            return searchOldTestament(searchtext[0],searchtext[1]);
        }

           // return searchitem(searchtext[0]);
        return null;
    }

    private Cursor searchEntireBible(String text, String criteria) {

        if(criteria.equals("true")){
            SearchHelper searchHelper = new SearchHelper(con);
            Cursor cursor = searchHelper.searchEntireBible(text);
            return cursor;
        }
        if(criteria.equals("false")){
            SearchHelper searchHelper = new SearchHelper(con);
             Cursor cursor = searchHelper.searchEntireBible(text);
            return cursor;
        }
        return null;
    }
    private Cursor searchNewTestament(String text, String criteria) {

        SearchHelper searchHelper = new SearchHelper(con);
        Cursor cursor = searchHelper.searchTestament(text,40001001,66022021);
        return cursor;
    }
    private Cursor searchOldTestament(String text, String criteria) {

        SearchHelper searchHelper = new SearchHelper(con);
        Cursor cursor = searchHelper.searchTestament(text,1001001,39004006);
        return cursor;
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Cursor result) {
        responsedelegate.processFinish(result);

    }


    //this interface helps us get response back to our app
    public  interface AsyncTaskResponse{
        public void processFinish(Cursor Output);
    }
}
