package com.kfive.hopebook.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfive.hopebook.R;

/**
 * Created by apple on 11/13/14.
 */
public class ColortagAdapter extends CursorAdapter {

    public ColortagAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater infl = LayoutInflater.from(context);
        View v = infl.inflate(R.layout.hb_colortag_list, viewGroup, false);

        Log.v("newview", "new view called");
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String chapter = cursor.getString(6);
        String colorcode = cursor.getString(8);

        TextView colortagchapter = (TextView) view.findViewById(R.id.colortagverse);
        ImageView colortagcode = (ImageView) view.findViewById(R.id.colortagcode);

        colortagchapter.setText(chapter);

        colortagcode.setBackgroundColor(Color.parseColor(colorcode));
        }
}


