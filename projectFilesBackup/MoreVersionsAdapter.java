package com.kfive.hopebible.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.models.MoreBibleVersions;

import io.realm.RealmResults;

/**
 * Created by apple on 11/15/14.
 */

// Custom adapter Class extends BaseAdapter
public class MoreVersionsAdapter extends BaseAdapter {
    //Font
    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";
    //Lets declare our data array (mdata)
    private RealmResults<MoreBibleVersions> mData;
    private Activity appcontext;

    //class constructor initializing data
    public MoreVersionsAdapter(RealmResults<MoreBibleVersions> data, Activity context) {
        mData = data;
        appcontext = context;
    }

    //Methods
    public void changeData(RealmResults<MoreBibleVersions> data) {
        mData = data;
        notifyDataSetChanged(); // method from BaseAdapter class
    }

    //overrides
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MoreBibleVersions getItem(int position) {
        return mData.get(0);
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

        MoreBibleVersions vData = mData.get(position);

        Typeface myTypeface = Typeface.createFromAsset(appcontext.getAssets(), CUSTOM_FONT);

        TextView txtversion = (TextView) convertView.findViewById(R.id.hb_versionname);
        TextView txtDownload = (TextView) convertView.findViewById(R.id.hb_downloadcount);

        txtversion.setText(vData.getVersionname());
        txtversion.setTypeface(myTypeface);

        txtDownload.setText(vData.getDownloadcount() + " Downloads");
        txtDownload.setTypeface(myTypeface);

        return convertView;
    }

}

