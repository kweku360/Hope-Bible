package com.kfive.hopebook.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.models.MoreBibleVersions;

import java.util.ArrayList;

/**
 * Created by apple on 11/15/14.
 */

// Custom adapter Class extends BaseAdapter
public class MoreVersionsAdapter extends BaseAdapter {
    //Lets declare our data array (mdata)
    private ArrayList<MoreBibleVersions> mData;
    private Activity appcontext;

    //class constructor initializing data
    public MoreVersionsAdapter(ArrayList<MoreBibleVersions> data, Activity context) {
        mData = data;
        appcontext = context;
    }

    //Methods
    public void changeData(ArrayList<MoreBibleVersions> data) {
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

        TextView tv = (TextView) convertView.findViewById(R.id.textView);
        TextView tv1 = (TextView) convertView.findViewById(R.id.textView2);

        tv.setText(vData.getVersion());
        tv1.setText(vData.getLanguage());

        return convertView;
    }

}

