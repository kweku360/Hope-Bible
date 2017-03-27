package com.kfive.hopebible.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.kfive.hopebible.R;
import com.kfive.hopebible.adapters.MoreVersionsAdapter;
import com.kfive.hopebible.data.BibleVersionKeyHelper;
import com.kfive.hopebible.data.VersionHelper;
import com.kfive.hopebible.helpers.UtilityHelper;
import com.kfive.hopebible.models.BibleVersionKey;
import com.kfive.hopebible.models.MoreVersions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HbMore extends AppCompatActivity {

    ListView listView;
    MoreVersionsAdapter moreVersionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_more);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("More Versions");

        //Steps to Glory
        //1.Gets Version data from server
        try {
            getVersionData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new UtilityHelper(this).ListFilesInDir("/data/data/com.kfive.hopebible/databases/");

    }


    private void getVersionData() throws IOException {
        String url = "http://104.131.190.2:3030/hopebible/getbible";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url(url)
                    .build();

            //async response
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string(); //note response.body().string() can be called only once
                final MoreVersions moreVersions= LoganSquare.parse(s,MoreVersions.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadData(moreVersions.data);
                    }
                });
            }
        });
    }

    private void loadData(List<MoreVersions.Data> mVersions){
        //setup our dummy data
        final ArrayList<String[]> bibleArr = new ArrayList<String[]>();

        String[] g1 = {"1","King James version","KJV","32203"};
        String[] g2 = {"2","Message Bible","MBV","188"};
        String[] g3 = {"3","Good News Bible","GNB","9822"};
        String[] g4 = {"4","Twere NkronKron","Asante Twi","32293"};

        bibleArr.add(g1);
        bibleArr.add(g2);
        bibleArr.add(g3);
        bibleArr.add(g4);

        //Our Adapter
        moreVersionsAdapter = new MoreVersionsAdapter(mVersions,this);
        listView = (ListView)findViewById(R.id.mlist);
        listView.setAdapter(moreVersionsAdapter);

    }
}
