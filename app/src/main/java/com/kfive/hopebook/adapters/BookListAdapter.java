package com.kfive.hopebook.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfive.hopebook.R;

import java.util.ArrayList;

/**
 * Created by apple on 10/26/14.
 */

// Custom adapter Class extends BaseAdapter
public class BookListAdapter extends BaseAdapter {
    //Font
    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";

    //Lets declare our data array (mdata)
    private String[][] mData;
    private Activity appcontext;

    //class constructor initializing data
    public BookListAdapter(String[][] data, Activity context) {
        mData = data;
        appcontext = context;
    }

    //Methods
    public void changeData(String[][] data) {
        mData = data;
        notifyDataSetChanged(); // method from BaseAdapter class
    }

    //overrides
    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public String getItem(int position) {
        return mData[position][0];
    }

    //the method returns an array list of the selected item ie all the values at that position
    public ArrayList<String> getItemList(int position){
        ArrayList<String> itemlist = new ArrayList<String>();
        for(int i =0;i < mData[position].length;i++){
            itemlist.add(i,mData[position][i]);
        }
        return itemlist;

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

            convertView = appcontext.getLayoutInflater().inflate(R.layout.hb_booksall_list, parent, false);
        }


        Typeface myTypeface = Typeface.createFromAsset(appcontext.getAssets(), CUSTOM_FONT);

        TextView listtext = (TextView) convertView.findViewById(R.id.textView);
        listtext.setTypeface(myTypeface);
        listtext.setText(getItem(position));

        return convertView;
    }

}

