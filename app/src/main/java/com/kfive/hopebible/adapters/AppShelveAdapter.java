package com.kfive.hopebible.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfive.hopebible.R;

/**
 * Created by apple on 3/30/15.
 */
public class AppShelveAdapter extends BaseAdapter {

    //Lets declare our data array (mdata)
    private String[] mData;
    private Activity appcontext;
    //Font
    private static final String CUSTOM_FONT = "fonts/Signika-Semibold.ttf";


    //class constructor initializing data
    public AppShelveAdapter(String[] data, Activity context) {
        mData = data;
        appcontext = context;
    }
    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public String getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //manually set the layout inflater
            convertView = appcontext.getLayoutInflater().inflate(R.layout.hb_simple_list, parent, false);
        }
        ///lets add custom font assets
        Typeface myTypeface = Typeface.createFromAsset(appcontext.getAssets(), CUSTOM_FONT);

        TextView listtext = (TextView) convertView.findViewById(R.id.textView);

        listtext.setTypeface(myTypeface);
        listtext.setText(getItem(position));

        return convertView;
    }
}
