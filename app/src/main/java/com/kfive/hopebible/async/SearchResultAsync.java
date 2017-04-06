package com.kfive.hopebible.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.kfive.hopebible.helpers.SearchHelper;
import com.kfive.hopebible.helpers.UtilityHelper;

/**
 * Created by apple on 11/18/14.
 */

public class SearchResultAsync extends AsyncTask<String,Void,Cursor[]> {
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
    protected Cursor[] doInBackground(String... searchtext) {

        if(searchtext[2].equals("Entire Bible")){
            return searchEntireBible(searchtext[0]);
        }
        return null;
    }

    private Cursor[] searchEntireBible(String text) {

        Cursor[] arrcursor = new Cursor[3];

        //we get the new testament
        arrcursor[2] = searchNewTestament(text);
        //we get the old testament
        arrcursor[1] = searchOldTestament(text);

            SearchHelper searchHelper = new SearchHelper(con);
            String dbName = new UtilityHelper(con).getCurrentDBName();
            arrcursor[0] = searchHelper.searchEntireBible(text,dbName);

        return arrcursor;
    }
    private Cursor searchNewTestament(String text) {

        SearchHelper searchHelper = new SearchHelper(con);
        String dbName = new UtilityHelper(con).getCurrentDBName();
        Cursor cursor = searchHelper.searchTestament(text,40001001,66022021,dbName);
        return cursor;
    }
    private Cursor searchOldTestament(String text) {

        SearchHelper searchHelper = new SearchHelper(con);
        String dbName = new UtilityHelper(con).getCurrentDBName();
        Cursor cursor = searchHelper.searchTestament(text,1001001,39004006,dbName);
        return cursor;
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Cursor[] result) {
        responsedelegate.processFinish(result);

    }


    //this interface helps us get response back to our app
    public  interface AsyncTaskResponse{
        public void processFinish(Cursor[] Output);
    }
}
