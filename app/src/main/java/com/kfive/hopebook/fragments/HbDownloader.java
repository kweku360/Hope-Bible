package com.kfive.hopebook.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kfive.hopebook.R;
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
 * Created by apple on 11/15/14.
 */
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HbDownloader extends DialogFragment {

    //TextView tv;
    public HbDownloader() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.hb_simple_list,null);
        //tv = (TextView) view.findViewById(R.id.textView);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        builder.setTitle("Downloads")

                .setNegativeButton("Cancel Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        startDownload(view);
        return builder.create();
    }

    public class StartDownloadAsync extends AsyncTask<String,Void,String>{
        Context con;
        View rootview;
        public StartDownloadAsync(Context context,View rootview) {
             this.con = context;
            this.rootview = rootview;
        }

        @Override
        protected void onPostExecute(String total) {

           // TextView tv = (TextView) rootview.findViewById(R.id.textView);
          //  long t = Long.parseLong(total);
           // tv.setText("Downloading"+(int)((t*100)/4756654)+" %");
            //Log.v("progress", "Downloading"+(int)((t*100)/4756654)+" %");
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
                Log.v("file size",String.valueOf(lenghtOfFile));
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

                installScript();


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
                    VersionHelper versionhelper = new VersionHelper(con);
                    versionhelper.dbQueryExec(line);
                    Log.v("Installation","Installed "+count);
                    count++;
                }
                br.close();
            }catch (IOException e){

            }
        }
    }


    public void startDownload(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            new StartDownloadAsync(this.getActivity(),view).execute("");


        }else {
            Log.v("error","network error");
        }


    }


}

