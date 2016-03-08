package com.kfive.hopebook.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.models.MoreBibleVersions;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 11/15/14.
 */

// Custom adapter Class extends BaseAdapter
public class MoreVersionsAdapter extends BaseAdapter {
    //Font
    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";
    //Lets declare our data array (mdata)
    private List<ParseObject> mData;
    private Activity appcontext;

    //class constructor initializing data
    public MoreVersionsAdapter(List<ParseObject> data, Activity context) {
        mData = data;
        appcontext = context;
    }

    //Methods
    public void changeData(List<ParseObject> data) {
        mData = data;
        notifyDataSetChanged(); // method from BaseAdapter class
    }

    //overrides
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ParseObject getItem(int position) {
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

        ParseObject vData = mData.get(position);

        Typeface myTypeface = Typeface.createFromAsset(appcontext.getAssets(), CUSTOM_FONT);

        TextView txtabbrev = (TextView) convertView.findViewById(R.id.txtabbreviation);
        TextView txtversion = (TextView) convertView.findViewById(R.id.txtversion);
        TextView txtlicense = (TextView) convertView.findViewById(R.id.txtlicence);

        txtabbrev.setText(vData.getString("abbreviation"));
        txtabbrev.setTypeface(myTypeface);

        txtversion.setText(vData.getString("version")+" ("+vData.getString("language")+")");
        txtabbrev.setTypeface(myTypeface);

        txtlicense.setText("Licence : "+vData.getString("license"));
        txtlicense.setTypeface(myTypeface);

        return convertView;
    }

}

