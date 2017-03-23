package com.kfive.hopebible.fragments;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kfive.hopebible.R;
//import com.kfive.hopebible.activities.HbMoreVersions;
import com.kfive.hopebible.data.BibleVersionKeyHelper;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HbBibleVersion extends DialogFragment {

    //Font
    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";


    public HbBibleVersion() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Context con = this.getContext();

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.hb_versiondialog, null,true);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        bibleVersionListener.onVersionClick(HbBibleVersion.this);
                    }
                })
                .setNeutralButton("Get More Versions", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(con, HbMoreVersions.class);
//                        startActivity(intent);
                     // bibleVersionListener.onGetMoreVersionsClick(HbBibleVersion.this);
                     // Toast.makeText(con, "More Bible Versions Coming Soon to Hope Bible", Toast.LENGTH_LONG).show();
                    }
                })

        .setSingleChoiceItems(getInstalledBibleversions(), getCurrentBibleVersion(), "version",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //lets get the checked item
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Cursor checkedItem = (Cursor)lw.getAdapter().getItem(lw.getCheckedItemPosition());

//                        lets save in shared preff
                        Context context = getActivity();
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                getString(R.string.bibleversionpref), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("currentversion", checkedItem.getInt(1)); //this gets the value on the db table id
                        editor.putInt("currentversioncount", checkedItem.getInt(0)); //this gets the count value
                        editor.commit();

                    }
                });


        //lets set some fonts
        //Lets do some fonts here
//        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), CUSTOM_FONT);
//        TextView tv = (TextView) view.findViewById(R.id.txttitle);
//        tv.setTypeface(myTypeface);
        return builder.create();
    }

  public Cursor getInstalledBibleversions(){
      //letsget all the bible versions
      BibleVersionKeyHelper bibleVersionKeyHelper = new BibleVersionKeyHelper(getActivity().getApplicationContext());
      Cursor cursor = bibleVersionKeyHelper.findAll();

      // For the cursor adapter, specify which columns go into which views

      String[] fromColumns = {"version"};
      int[] toViews = {R.id.versenumber}; // The TextView in simple_list_item_1

      // Create an empty adapter we will use to display the loaded data.
      // We pass null for the cursor, then update it in onLoadFinished()
      SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
              R.layout.hb_bible_textlist, cursor,
              fromColumns, toViews, 0);

      return cursor;

  }
    public int getCurrentBibleVersion(){
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.bibleversionpref), Context.MODE_PRIVATE);
        int defaultValue = 1;
        int version = sharedPref.getInt("currentversioncount", defaultValue);
        return version -1;
    }

    //Lets implement an interface
    public interface HbBibleVersionListener {
        public void onVersionClick(DialogFragment dialog);
        public void onGetMoreVersionsClick(DialogFragment dialog);
    }

    //create an instance of our interface
    HbBibleVersionListener bibleVersionListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            bibleVersionListener = (HbBibleVersionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
