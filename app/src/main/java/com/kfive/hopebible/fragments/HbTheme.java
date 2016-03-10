package com.kfive.hopebible.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kfive.hopebible.R;

/**
 * Created by Kweku Kankam on 11/12/14.
 */

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HbTheme extends DialogFragment {

    //Font
    private static final String CUSTOM_FONT = "fonts/Dosis-Medium.ttf";


    public HbTheme() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.hb_theme, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        setDialogEvents(view);
        setResourcesColor(view);
        return builder.create();
    }

    private void setDialogEvents(View cusdia) {
        final ImageView imageView2 = (ImageView) cusdia.findViewById(R.id.imageView2);
        Button cancel = (Button)cusdia.findViewById(R.id.hb_diacancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String colorcode = (String)imageView2.getTag();
                themeListener.onThemeClick(HbTheme.this, colorcode);
            }
        });
        final ImageView imageView3 = (ImageView) cusdia.findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String colorcode = (String)imageView3.getTag();
                themeListener.onThemeClick(HbTheme.this, colorcode);
            }
        });
        final ImageView imageView4 = (ImageView) cusdia.findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String colorcode = (String) imageView4.getTag();
                themeListener.onThemeClick(HbTheme.this, colorcode);
            }
        });
        final ImageView imageView5 = (ImageView) cusdia.findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String colorcode = (String) imageView5.getTag();
                themeListener.onThemeClick(HbTheme.this, colorcode);
            }
        });
    }

    private String getColorTheme(){
        SharedPreferences appprefs = getActivity().getSharedPreferences("com.kfive.hopebible.bible", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor ed;
        String themecolor = appprefs.getString("color", "");
        if (themecolor.equals("")) {
            //means no value for theme so we use default redoctober
            ed = appprefs.edit();
            ed.putString("color", "#C44244");
            ed.commit(); //finally we commit
            themecolor = "#C44244";
        }
        return themecolor;
    }

    private void setResourcesColor(View cusdia){
        String color = getColorTheme();
        ImageButton dialogicon = (ImageButton)cusdia.findViewById(R.id.hb_themeddialogicon);
        TextView txttle = (TextView)cusdia.findViewById(R.id.txttitle);
        LinearLayout Hb_diagbg = (LinearLayout)cusdia.findViewById(R.id.hb_lineardialog);

        Hb_diagbg.setBackgroundColor(Color.parseColor(color));
        dialogicon.setBackgroundColor(Color.parseColor(color));
        txttle.setTextColor(Color.parseColor(color));
        }

    //Lets implement an interface
    public interface HbThemeListener {
        public void onThemeClick(DialogFragment dialog, String colorcode);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    //create an instance of our interface
    HbThemeListener themeListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            themeListener = (HbThemeListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
