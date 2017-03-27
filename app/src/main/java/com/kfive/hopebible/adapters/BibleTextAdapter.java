package com.kfive.hopebible.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfive.hopebible.R;
import com.kfive.hopebible.data.BookmarksHelper;
import com.kfive.hopebible.data.ColortagsHelper;
import com.kfive.hopebible.models.Colortag;

/**
 * Created by apple on 11/11/14.
 */
public class BibleTextAdapter extends CursorAdapter {
    //Font
    private static final String CUSTOM_FONT = "fonts/LibreBaskerville-Regular.ttf";
    private Context appcontext;

    public BibleTextAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        appcontext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater infl = LayoutInflater.from(context);
        View v = infl.inflate(R.layout.hb_bible_textlist, viewGroup, false);

        Log.v("newview", "new view called");
        return v;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Typeface myTypeface = Typeface.createFromAsset(appcontext.getAssets(), CUSTOM_FONT);

        TextView versetext = (TextView) view.findViewById(R.id.versetext);
        TextView versenumber = (TextView) view.findViewById(R.id.versenumber);
        ImageView bookmarkimg = (ImageView) view.findViewById(R.id.hb_bookmarkbar);

        versetext.setTypeface(myTypeface);
        versetext.setBackgroundColor(Color.WHITE);

        versenumber.setText(cursor.getString(5));
        versetext.setText(" "+cursor.getString(4)+".  "+cursor.getString(5));

        checkBookmark(context,cursor,bookmarkimg);
        //checkColortag(context,cursor,versetext);

    }

    private void checkColortag(Context context, Cursor cursor, TextView versetext) {
        int verseno = Integer.parseInt(cursor.getString(1));

        ColortagsHelper colortagsHelper = new ColortagsHelper(context);
        Colortag ctag = colortagsHelper.findOne(verseno);
        if(ctag == null){
            versetext.setBackgroundColor(Color.TRANSPARENT);
        }else{
           versetext.setBackgroundColor(Color.parseColor(ctag.getColortag()));
        }

    }

    private void checkBookmark(Context context,Cursor cursor, View bookmarkimg) {

        //lets do some interesting stuff here
        int verseno = Integer.parseInt(cursor.getString(1));

        BookmarksHelper bookmarksHelper = new BookmarksHelper(context);

        String[] versebookmark = bookmarksHelper.findChapterBookmark(verseno);

        if(versebookmark.length >= 1){

            if(versebookmark[0].equals(String.valueOf(verseno))){
                bookmarkimg.setVisibility(View.VISIBLE);
            }
            else{
                bookmarkimg.setVisibility(View.GONE);
            }
        } else{
            bookmarkimg.setVisibility(View.GONE);
        }

    }
}
