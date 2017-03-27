package com.kfive.hopebible.adapters;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kfive.hopebible.R;
import com.kfive.hopebible.activities.HbMore;
import com.kfive.hopebible.data.BibleVersionKeyHelper;
import com.kfive.hopebible.data.VersionHelper;
import com.kfive.hopebible.helpers.UtilityHelper;
import com.kfive.hopebible.models.BibleVersionKey;
import com.kfive.hopebible.models.MoreVersions;
import com.kfive.hopebible.uihelpers.AppTextViewRoboto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by apple on 11/15/14.
 */

// Custom adapter Class extends BaseAdapter
public class MoreVersionsAdapter extends BaseAdapter {

    //Lets declare our data array (mdata)
    private List<MoreVersions.Data> mData;
    private Activity appcontext;
    BibleVersionKeyHelper bibleVersionKeyHelper;
    BibleVersionKey newBibleVersion;
    FancyButton installversion;
    LinearLayout versionProgress;
    AppTextViewRoboto progresspercent;
    AppTextViewRoboto versioninstalled;

    DownloadManager downloadManager;
    DownloadManager.Request request;
    long enqueue;

    UtilityHelper utilityHelper;

    //class constructor initializing data
    public MoreVersionsAdapter(List<MoreVersions.Data> data, Activity context) {
        mData = data;
        appcontext = context;
        //lets register download broadcaster on init
        broadcastSetup();
        utilityHelper = new UtilityHelper(appcontext);
    }

    //Methods
    public void changeData(List<MoreVersions.Data> data) {
        mData = data;
        notifyDataSetChanged(); // method from BaseAdapter class
    }

    //overrides
    @Override
    public int getCount() {

        return mData.size();
    }

    @Override
    public MoreVersions.Data getItem(int position) {

        return mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    //the getView method
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //manually set the layout inflater

            convertView = appcontext.getLayoutInflater().inflate(R.layout.hb_moreversions_list, parent, false);
        }

        TextView versionname = (TextView) convertView.findViewById(R.id.versionname);
        TextView versioninfo = (TextView) convertView.findViewById(R.id.versioninfo);
        ImageView versionimg = (ImageView) convertView.findViewById((R.id.versionimg));

        //Other layouts
         versionProgress = (LinearLayout)  convertView.findViewById(R.id.versionprogress);
        installversion = (FancyButton)  convertView.findViewById(R.id.hb_installversion);
         versioninstalled = (AppTextViewRoboto)  convertView.findViewById(R.id.versioninstalled);
         progresspercent = (AppTextViewRoboto)  convertView.findViewById(R.id.progresspercent);

        //we set all their visibility to gone
//        versionProgress.setVisibility(View.GONE);
//        installversion.setVisibility(View.GONE);
//        versioninstalled.setVisibility(View.GONE);

        MoreVersions.Data vData = mData.get(position);

        versionname.setText(vData.attributes.versionname);
        versioninfo.setText(vData.attributes.bookcode + " ( "+ vData.attributes.downloadcount +" Downloads )");

        Picasso.with(appcontext).load(vData.attributes.imagelink).resize(100,100).into(versionimg);

        //we create a BibleVersionkey Model object and populate the new version values into it
        newBibleVersion = new BibleVersionKey();
        newBibleVersion.setTable("t_"+ vData.attributes.bookcode.toLowerCase());
        newBibleVersion.setAbbreviation(vData.attributes.bookcode);
        newBibleVersion.setLanguage("English"); //leave at english for now
        newBibleVersion.setVersion(vData.attributes.versionname); //leave at english for now
        newBibleVersion.setInfo_text(" ");
        newBibleVersion.setInfo_url(" ");
        newBibleVersion.setPublisher(" ");
        newBibleVersion.setCopyright("Free");
        newBibleVersion.setCopyright_info("Free Public Domain");


//        check if installed or not
          installChecker("t_"+vData.attributes.bookcode.toLowerCase());

        //handle button click here
        onInstallClick(vData.attributes.downloadlink);
        return convertView;
    }

    private void installChecker(String versioncode) {
        bibleVersionKeyHelper = new BibleVersionKeyHelper(appcontext);
        if(bibleVersionKeyHelper.findOneByTableName(versioncode) == true){
            //means bookexists
            //set installed visibility to visible // hide others
            //we set all their visibility to gone
            versionProgress.setVisibility(View.GONE);
            installversion.setVisibility(View.GONE);
            versioninstalled.setVisibility(View.VISIBLE);
        }else{
            versionProgress.setVisibility(View.GONE);
            installversion.setVisibility(View.VISIBLE);
            versioninstalled.setVisibility(View.GONE);
        }
    }

    public void onInstallClick(final String url){
        installversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Dialog
                //start download service
                downloadManager = (DownloadManager) appcontext.getSystemService(appcontext.DOWNLOAD_SERVICE);
                request = new DownloadManager.Request(Uri.parse(url));

                //Get download file name
                String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
                String name = URLUtil.guessFileName(url, null, fileExtenstion);

                //Save file to destination folder
                request.setDestinationInExternalPublicDir("/HopeBible", name);

                enqueue = downloadManager.enqueue(request);
                //Once the download starts we need to start querying it to get progress informaton and other stuff
                //we set a flag for our while loop
                // showDownloadProgress();
            }
        });
    }

    private void showDownloadProgress() {

        versionProgress.setVisibility(View.VISIBLE);
        installversion.setVisibility(View.GONE);
        versioninstalled.setVisibility(View.GONE);

//        boolean isDownloading = true;
//
//        while (isDownloading){
//            DownloadManager.Query query = new DownloadManager.Query();
//            query.setFilterById(enqueue);
//
//            Cursor c = downloadManager.query(query);
//            if (c.moveToFirst()) {
//                int sizeIndex = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
//                int downloadedIndex = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
//                int size = c.getInt(sizeIndex);
//                Log.v("trapsize",String.valueOf(size));
//                int downloaded = c.getInt(downloadedIndex);
//                Log.v("trapdown",String.valueOf(downloaded));
//                double progress = 0.0;
//                if (size != -1){
//                    // At this point you have the progress as a percentage.
//                    progress = downloaded*100/size;
//                    Log.v("trapprog",String.valueOf(progress));
//                    progresspercent.setText("kankm");
//                }else{
//                    isDownloading = false;
//                }
//
//            }
//        }
    }

    private void broadcastSetup() {

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                            String fname = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                            //we do a file copy
                            if(utilityHelper.DbFileCopy(Environment.getExternalStoragePublicDirectory("/HopeBible").getAbsolutePath(),fname) == true)
                            {
                                //lets check to see if the file was copied by listing
                                utilityHelper.ListFilesInDir("/data/data/com.kfive.hopebible/databases/");
                                //next we insert version info into coredb
                                bibleVersionKeyHelper.addOne(newBibleVersion);

                                //next we test our install

                                //finally we update our ui
                                installChecker(newBibleVersion.getTable());

                            };
                            Toast.makeText(appcontext, "Download completed! ", Toast.LENGTH_SHORT).show();
                            //we can perform sql actions hre
                        }
                    }
                }
            }
        };

        appcontext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }





}

