package com.kfive.hopebook.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.kfive.hopebook.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HbHomeLanding.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HbHomeLanding#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HbHomeLanding extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HbHomeLanding.
     */
    // TODO: Rename and change types and number of parameters
    public static HbHomeLanding newInstance(String param1, String param2) {
        HbHomeLanding fragment = new HbHomeLanding();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public HbHomeLanding() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          // Inflate the layout for this fragment
        View myFrag =  inflater.inflate(R.layout.fragment_hb_home_landing, container, false);
        //Start View Flipper
        final ViewFlipper viewFlipper = (ViewFlipper) myFrag.findViewById(R.id.viewFlipper);
//create an animation object from our animation resource
        Animation myanim = AnimationUtils.loadAnimation(getActivity(),R.anim.transition_slide_up);
//binf this animation to our viewflipper
        viewFlipper.setInAnimation(myanim);
        //viewFlipper.setOutAnimation(getActivity(), R.anim.transition_slide_down);

        //lets inflate some views into viewFlipper
        View verseoftheday = inflater.inflate(R.layout.verse_of_the_day, container, false);
        View lastverse = inflater.inflate(R.layout.last_bible_verse, container, false);

        viewFlipper.addView(verseoftheday);
        viewFlipper.addView(lastverse);

//lets bind listeners to our animation object
        myanim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
//                here we can do some stuff
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.lastverse), Context.MODE_PRIVATE);
                String lastversetext = sharedPref.getString("lastversetext", "");
                String lastfullverse = sharedPref.getString("lastfullverse", "");
                String lastversion = sharedPref.getString("lastversion", "");

                View v = viewFlipper.getChildAt(1);

                TextView versetext = (TextView) v.findViewById(R.id.lastversetext);
                TextView fullverse = (TextView) v.findViewById(R.id.lastfullverse);

                versetext.setText(lastversetext);
                fullverse.setText(lastfullverse + " ( "+ lastversion +" )");

            }
        });

        viewFlipper.startFlipping();



        return myFrag;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        try {
           // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
