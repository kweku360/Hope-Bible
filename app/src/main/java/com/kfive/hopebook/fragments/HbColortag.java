package com.kfive.hopebook.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.data.BibleVersionKeyHelper;

/**
 * Created by Kweku Kankam on 11/12/14.
 */

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HbColortag extends DialogFragment {


    public HbColortag() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.hb_color_tag, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        builder.setTitle("Choose Color Tag")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        setDialogEvents(view);
        return builder.create();
    }

    private void setDialogEvents(View cusdia) {
        ImageView imageView2 = (ImageView) cusdia.findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                colortagListener.onColorClick(HbColortag.this,"#a3e496");
            }
        });
        ImageView imageView3 = (ImageView) cusdia.findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                colortagListener.onColorClick(HbColortag.this,"#008AB8");
            }
        });
        ImageView imageView4 = (ImageView) cusdia.findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                colortagListener.onColorClick(HbColortag.this,"#CC3333");
            }
        });
    }

    //Lets implement an interface
    public interface HbColortagListener {
        public void onColorClick(DialogFragment dialog,String colorcode);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    //create an instance of our interface
    HbColortagListener colortagListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            colortagListener = (HbColortagListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
