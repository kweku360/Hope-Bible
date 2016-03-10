package com.kfive.hopebible.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kfive.hopebible.R;
import com.kfive.hopebible.async.DownloaderAsync;

/**
 * Created by apple on 11/15/14.
 */
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HbDownloader extends DialogFragment {
    //Instantiate our async class
    DownloaderAsync downloaderAsync = new DownloaderAsync();

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




    public void startDownload(View view) {
     //   final Context that = this.getAppC;
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //new DownloaderAsync(that.getActivity(),view).execute("");


        }else {
            Log.v("error","network error");
        }


    }


}

