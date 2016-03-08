package com.kfive.hopebook.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kfive.hopebook.R;
import com.kfive.hopebook.activities.HbBookmarks;
import com.kfive.hopebook.activities.HbSearchPage;
import com.kfive.hopebook.adapters.AppShelveAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HBHomeAppShelve.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HBHomeAppShelve#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HBHomeAppShelve extends Fragment {

    //Font
    private static final String CUSTOM_FONT = "fonts/Signika-Semibold.ttf";

    //list variables
    private String[] listItems;
    private ListView sListView;

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
     * @return A new instance of fragment HBHomeAppShelve.
     */
    // TODO: Rename and change types and number of parameters
    public static HBHomeAppShelve newInstance(String param1, String param2) {
        HBHomeAppShelve fragment = new HBHomeAppShelve();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public HBHomeAppShelve() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_hbhome_app_shelve, container, false);

       //show our Listview
        listItems =new String[]{"Search", "History","Bible Version","Bookmarks","Color Tags","Notes","Daily Verse","settings","About"};
        AppShelveAdapter appShelveAdapter = new AppShelveAdapter(listItems,getActivity());
        sListView = (ListView) fragmentView.findViewById(R.id.listView);
        sListView.setAdapter(appShelveAdapter);
        //click event for each item clicked in the listview
        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.v("Position",String.valueOf(position));
                switch (position){
                    //Search clicked
                    case 0:{
                        Intent intent = new Intent(getActivity(), HbSearchPage.class);
                        startActivity(intent);
                        break;

                    }
                    //History clicked
                    case 1:{
                        Intent intent = new Intent(getActivity(), HbBookmarks.class);
                        startActivity(intent);
                        break;
                    }
                    //bible version clicked
                    case 2:{

                        HbBibleVersion hbBibleVersion = new HbBibleVersion();

                        hbBibleVersion.show(getActivity().getSupportFragmentManager(), "Version Dialog");
                        break;
                    }
                    //Bookmarks clicked
                    case 3:{
                        Intent intent = new Intent(getActivity(), HbBookmarks.class);
                        startActivity(intent);
                        break;
                    }

                }

            }
        });
        return fragmentView;
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
