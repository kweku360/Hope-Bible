package com.kfive.hopebook.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.kfive.hopebook.data.VersionHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by apple on 11/22/14.
 */


public class DownloaderAsync extends AsyncTask<String,Void,String> {
    public AsyncTaskResponse responsedelegate;
    public void setAsyncTaskResponseListener(AsyncTaskResponse responsedelegate) {
        this.responsedelegate = responsedelegate;
    }

    @Override
    protected void onPostExecute(String result) {
        responsedelegate.processFinish(result);
    }

    @Override
    protected String doInBackground(String... params) {
        int count;
        try{
            String myurl = "https://dl.dropboxusercontent.com/s/ulghb7lcxiqy15f/yltnew.sql";
            URL url = new URL(myurl);
            URLConnection conn = url.openConnection();
            conn.connect();
            int lenghtOfFile = conn.getContentLength();
            Log.v("file size", String.valueOf(lenghtOfFile));
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream("/sdcard/yltnew.sql");
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;

                //  tv.setText("Downloading"+(int)((total*100)/4756654)+" %");
                // onPostExecute(String.valueOf(total));
                output.write(data, 0, count);
            }

            // tv.setText("Downloading Complete");

            output.flush();
            output.close();
            input.close();
            Log.v("complete", "Download Completed");
            Log.v("Installation","Starting Installation");

         //   installScript();


        }catch (IOException e){

        }
        return "";
    }

    private void installScript() {
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("/sdcard/yltnew.sql"));
            String line;
            while ((line = br.readLine()) != null) {
               // VersionHelper versionhelper = new VersionHelper(con);
               // versionhelper.dbQueryExec(line);
                Log.v("Installation","Installed "+count);
                count++;
            }
            br.close();
        }catch (IOException e){

        }
    }

    //this interface helps us get response back to our app
    public  interface AsyncTaskResponse{
        public void processFinish(String Output);
    }
}

